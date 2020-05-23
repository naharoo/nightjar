package eu.navads.nightjar.endpoint.scriptexecution;

import eu.navads.nightjar.domain.CodeSnippet;
import eu.navads.nightjar.infra.logging.Loggable;
import eu.navads.nightjar.service.scriptexecution.ScriptExecutionService;
import eu.navads.nightjar.service.codesnippet.CodeSnippetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
public class ScriptExecutionEndpointImpl implements ScriptExecutionEndpoint {

    private final CodeSnippetService codeSnippetService;
    private final ScriptExecutionService scriptExecutionService;

    @Autowired
    public ScriptExecutionEndpointImpl(
            final CodeSnippetService codeSnippetService,
            final ScriptExecutionService scriptExecutionService
    ) {
        this.codeSnippetService = codeSnippetService;
        this.scriptExecutionService = scriptExecutionService;
    }

    @Override
    @Loggable(before = LogLevel.DEBUG, after = LogLevel.INFO)
    public ModelAndView getScriptExecutionPageHtml(final ModelAndView modelAndView, final String snippetId) {
        modelAndView.setViewName("editor");

        addCodeSnippetData(snippetId, modelAndView);

        return modelAndView;
    }

    private void addCodeSnippetData(final String snippetId, final ModelAndView modelAndView) {
        final String snippetName;
        final List<String> codeLines;
        final boolean isNewSnippet;

        if (isBlank(snippetId)) {
            snippetName = "New Snippet";
            codeLines = Collections.emptyList();
            isNewSnippet = true;
        } else {
            final CodeSnippet codeSnippet = codeSnippetService.getById(snippetId);
            snippetName = codeSnippet.getName();
            codeLines = codeSnippet.getReadyForRenderingValueLines();
            isNewSnippet = false;
        }

        modelAndView.addObject("snippetId", snippetId);
        modelAndView.addObject("codeLines", codeLines);
        modelAndView.addObject("snippetName", snippetName);
        modelAndView.addObject("isNewSnippet", String.valueOf(isNewSnippet));
    }
}
