package club.thewhitewall.nightjar.service.codesnippet.execution;

import club.thewhitewall.nightjar.domain.CodeSnippet;
import com.fasterxml.jackson.databind.JsonNode;

import javax.validation.constraints.NotNull;

public interface CodeSnippetExecutionStrategy {

    JsonNode execute(@NotNull CodeSnippet snippet, @NotNull JsonNode request);
}
