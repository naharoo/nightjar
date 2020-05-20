package eu.navads.nightjar.endpoint.scriptexecution;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/script-execution")
public interface ScriptExecutionEndpoint {

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    String getScriptExecutionPageHtml();
}
