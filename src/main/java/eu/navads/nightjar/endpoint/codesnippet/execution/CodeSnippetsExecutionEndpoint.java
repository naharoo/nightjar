package eu.navads.nightjar.endpoint.codesnippet.execution;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Validated
@RestController
@RequestMapping("/rest/code-snippets/execution")
public interface CodeSnippetsExecutionEndpoint {

    @PostMapping
    JsonNode execute(@Valid @NotNull @RequestBody CodeSnippetExecutionRequestDto executionRequestDto);
}
