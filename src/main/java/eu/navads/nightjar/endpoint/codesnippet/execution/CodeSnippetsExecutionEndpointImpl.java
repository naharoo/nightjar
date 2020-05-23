package eu.navads.nightjar.endpoint.codesnippet.execution;

import eu.navads.nightjar.service.codesnippet.execution.CodeSnippetExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CodeSnippetsExecutionEndpointImpl implements CodeSnippetsExecutionEndpoint {

    private final CodeSnippetExecutionService codeSnippetExecutionService;

    @Autowired
    public CodeSnippetsExecutionEndpointImpl(final CodeSnippetExecutionService codeSnippetExecutionService) {
        this.codeSnippetExecutionService = codeSnippetExecutionService;
    }
}
