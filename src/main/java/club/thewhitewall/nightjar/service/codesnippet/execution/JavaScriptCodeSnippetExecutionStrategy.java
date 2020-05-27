package club.thewhitewall.nightjar.service.codesnippet.execution;

import club.thewhitewall.nightjar.domain.CodeSnippet;
import club.thewhitewall.nightjar.infra.logging.Loggable;
import club.thewhitewall.nightjar.infra.validation.params.ValidParams;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.io.IOException;

@Slf4j
@Component
public class JavaScriptCodeSnippetExecutionStrategy implements CodeSnippetExecutionStrategy {

    private static final String LANGUAGE = "js";
    private static final String SCRIPT_FILE_NAME = "src.js";
    private static final String SCRIPT_MAIN_METHOD_NAME = "invoke";
    private static final String SCRIPT_JSON_STRINGIFY_METHOD_NAME = "JSON.stringify";
    private static final String SCRIPT_JSON_PARSE_METHOD_NAME = "JSON.parse";

    private final ObjectMapper objectMapper;

    @Autowired
    public JavaScriptCodeSnippetExecutionStrategy(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    @Loggable
    @ValidParams
    public JsonNode execute(@NotNull final CodeSnippet snippet, @NotNull final JsonNode request) {
        final JsonNode response;
        try (final Context context = Context.create()) {
            final Source source = buildEvaluationSource(snippet);
            evaluateSource(source, context);
            final Value invocationTarget = getInvocationTarget(context);
            final Value argumentsAsValue = compressJsonNode(request, context);
            final Value returnedValue = invocationTarget.execute(argumentsAsValue);
            response = extractJsonNode(returnedValue, context);
        }

        return response;
    }

    private Value getInvocationTarget(final Context context) {
        return context.getBindings(LANGUAGE).getMember(SCRIPT_MAIN_METHOD_NAME);
    }

    private void evaluateSource(final Source source, final Context context) {
        context.eval(source);
    }

    private JsonNode extractJsonNode(final Value returnedValue, final Context context) {
        final String resultAsString = context
                .eval(LANGUAGE, SCRIPT_JSON_STRINGIFY_METHOD_NAME)
                .execute(returnedValue)
                .asString();
        try {
            return objectMapper.readTree(resultAsString);
        } catch (final IOException exception) {
            final String errorMsg = "Failed to convert execution result json to JsonNode.";
            log.warn(errorMsg, exception);
            throw new IllegalStateException(errorMsg, exception);
        }
    }

    private Value compressJsonNode(final JsonNode jsonNode, final Context context) {
        try {
            final String stringifiedJson = objectMapper.writeValueAsString(jsonNode);
            return context
                    .eval(LANGUAGE, SCRIPT_JSON_PARSE_METHOD_NAME)
                    .execute(stringifiedJson);
        } catch (final JsonProcessingException exception) {
            log.warn("Failed to convert JsonNode to String.", exception);
            throw new IllegalStateException(exception);
        }
    }

    private Source buildEvaluationSource(final CodeSnippet snippet) {
        try {
            return Source.newBuilder(LANGUAGE, snippet.getValue(), SCRIPT_FILE_NAME).build();
        } catch (final IOException exception) {
            log.warn("Failed to build Source for Snippet Script.", exception);
            throw new IllegalStateException(exception);
        }
    }
}
