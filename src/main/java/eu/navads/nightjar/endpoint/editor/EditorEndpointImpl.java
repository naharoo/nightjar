package eu.navads.nightjar.endpoint.editor;

import eu.navads.nightjar.domain.CodeSnippet;
import eu.navads.nightjar.infra.logging.Loggable;
import eu.navads.nightjar.service.codesnippet.CodeSnippetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
public class EditorEndpointImpl implements EditorEndpoint {

    private final CodeSnippetService codeSnippetService;

    @Autowired
    public EditorEndpointImpl(final CodeSnippetService codeSnippetService) {
        this.codeSnippetService = codeSnippetService;
    }

    @Override
    @Loggable(before = LogLevel.DEBUG, after = LogLevel.INFO)
    public ModelAndView getEditor(final ModelAndView modelAndView, final String snippetId) {
        modelAndView.setViewName("editor");

        addCodeSnippetData(snippetId, modelAndView);

        return modelAndView;
    }

    private void addCodeSnippetData(final String snippetId, final ModelAndView modelAndView) {
        final String snippetIdToPass;
        final String snippetName;
        final List<String> codeLines;
        final boolean isNewSnippet;

        if (isBlank(snippetId)) {
            snippetIdToPass = "";
            snippetName = "New Snippet";
            codeLines = Collections.emptyList();
            isNewSnippet = true;
        } else {
            snippetIdToPass = snippetId;
            final CodeSnippet codeSnippet = codeSnippetService.getById(snippetId);
            snippetName = codeSnippet.getName();
            codeLines = codeSnippet.getReadyForRenderingValueLines();
            isNewSnippet = false;
        }

        modelAndView.addObject("snippetId", snippetIdToPass);
        modelAndView.addObject("codeLines", codeLines);
        modelAndView.addObject("snippetName", snippetName);
        modelAndView.addObject("isNewSnippet", String.valueOf(isNewSnippet));
    }
}
