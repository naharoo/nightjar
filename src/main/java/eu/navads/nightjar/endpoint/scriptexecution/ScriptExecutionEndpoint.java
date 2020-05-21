package eu.navads.nightjar.endpoint.scriptexecution;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@Controller
@RequestMapping("/script-execution")
public interface ScriptExecutionEndpoint {

    @GetMapping
    String getScriptExecutionPageHtml();
}
