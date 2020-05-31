package club.thewhitewall.nightjar.endpoint.editor;

import club.thewhitewall.nightjar.service.codesnippet.CodeSnippetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EditorEndpointImpl implements EditorEndpoint {

    private final CodeSnippetService codeSnippetService;

    @Autowired
    public EditorEndpointImpl(final CodeSnippetService codeSnippetService) {
        this.codeSnippetService = codeSnippetService;
    }
}
