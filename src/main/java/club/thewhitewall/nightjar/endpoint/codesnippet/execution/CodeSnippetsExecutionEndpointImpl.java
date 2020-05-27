package club.thewhitewall.nightjar.endpoint.codesnippet.execution;

import com.fasterxml.jackson.databind.JsonNode;
import club.thewhitewall.nightjar.domain.CodeSnippetExecutionRequest;
import club.thewhitewall.nightjar.infra.logging.Loggable;
import club.thewhitewall.nightjar.service.codesnippet.execution.CodeSnippetExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;

@Component
public class CodeSnippetsExecutionEndpointImpl implements CodeSnippetsExecutionEndpoint {

    private final CodeSnippetExecutionService service;

    @Autowired
    public CodeSnippetsExecutionEndpointImpl(final CodeSnippetExecutionService service) {
        this.service = service;
    }

    @Override
    @Loggable(before = LogLevel.DEBUG, after = LogLevel.INFO)
    public JsonNode execute(final CodeSnippetExecutionRequestDto executionRequestDto) {
        final CodeSnippetExecutionRequest executionRequest = executionRequestDto.toDomain();
        return service.execute(executionRequest);
    }
}
