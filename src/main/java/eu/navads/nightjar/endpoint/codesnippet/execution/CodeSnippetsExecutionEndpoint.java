package eu.navads.nightjar.endpoint.codesnippet.execution;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@Controller
@RequestMapping("/rest/code-snippets/execution")
public interface CodeSnippetsExecutionEndpoint {
}
