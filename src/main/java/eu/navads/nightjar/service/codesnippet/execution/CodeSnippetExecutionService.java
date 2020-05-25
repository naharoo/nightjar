package eu.navads.nightjar.service.codesnippet.execution;

import com.fasterxml.jackson.databind.JsonNode;
import eu.navads.nightjar.domain.CodeSnippetExecutionRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface CodeSnippetExecutionService {

    JsonNode execute(@Valid @NotNull CodeSnippetExecutionRequest executionRequest);
}
