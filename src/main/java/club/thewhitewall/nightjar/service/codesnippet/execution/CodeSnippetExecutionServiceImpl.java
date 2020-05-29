package club.thewhitewall.nightjar.service.codesnippet.execution;

import club.thewhitewall.nightjar.domain.CodeSnippet;
import club.thewhitewall.nightjar.domain.CodeSnippetQualifier;
import club.thewhitewall.nightjar.exception.PreconditionFailedException;
import com.fasterxml.jackson.databind.JsonNode;
import club.thewhitewall.nightjar.domain.CodeSnippetExecutionRequest;
import club.thewhitewall.nightjar.infra.logging.Loggable;
import club.thewhitewall.nightjar.infra.validation.params.ValidParams;
import club.thewhitewall.nightjar.service.codesnippet.CodeSnippetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Service
public class CodeSnippetExecutionServiceImpl implements CodeSnippetExecutionService {

    private final CodeSnippetService codeSnippetService;
    private final CodeSnippetExecutionStrategy codeSnippetExecutionStrategy;

    @Autowired
    public CodeSnippetExecutionServiceImpl(
            final CodeSnippetService codeSnippetService,
            final CodeSnippetExecutionStrategy codeSnippetExecutionStrategy
    ) {
        this.codeSnippetService = codeSnippetService;
        this.codeSnippetExecutionStrategy = codeSnippetExecutionStrategy;
    }

    @Override
    @Loggable
    @ValidParams
    @Transactional(readOnly = true)
    public JsonNode execute(@Valid @NotNull final CodeSnippetExecutionRequest executionRequest) {
        final CodeSnippet snippet = codeSnippetService.getByName(executionRequest.getSnippetName());

        if (!snippet.containsQualifier(CodeSnippetQualifier.INVOCABLE)) {
            throw PreconditionFailedException.createInstance("Only INVOCABLE CodeSnippets can be executed.");
        }

        return codeSnippetExecutionStrategy.execute(snippet, executionRequest.getData());
    }
}
