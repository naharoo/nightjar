package eu.navads.nightjar.endpoint.scriptexecution;

import eu.navads.nightjar.infra.logging.Loggable;
import eu.navads.nightjar.service.scriptexecution.ScriptExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;

@Component
public class ScriptExecutionEndpointImpl implements ScriptExecutionEndpoint {

    private final ScriptExecutionService scriptExecutionService;

    @Autowired
    public ScriptExecutionEndpointImpl(final ScriptExecutionService scriptExecutionService) {
        this.scriptExecutionService = scriptExecutionService;
    }

    @Override
    @Loggable(before = LogLevel.DEBUG, after = LogLevel.INFO)
    public String getScriptExecutionPageHtml() {
        return "script-execution";
    }
}
