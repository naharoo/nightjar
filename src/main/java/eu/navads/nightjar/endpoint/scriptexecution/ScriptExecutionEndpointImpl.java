package eu.navads.nightjar.endpoint.scriptexecution;

import eu.navads.nightjar.infra.logging.Loggable;
import eu.navads.nightjar.service.scriptexecution.ScriptExecutionService;
import eu.navads.nightjar.service.snippet.CodeSnippetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

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

        final String codeSnippet = isNotBlank(snippetId) ? codeSnippetService.getById(snippetId).getValue() : "";
        modelAndView.addObject("codeSnippet", codeSnippet);

        return modelAndView;
    }
}
