package club.thewhitewall.nightjar.domain;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CodeSnippetExecutionRequest {

    @NotBlank
    private final String snippetName;

    private final JsonNode data;

    private CodeSnippetExecutionRequest(final String snippetName, final JsonNode data) {
        this.snippetName = snippetName;
        this.data = data;
    }

    public static CodeSnippetExecutionRequest createInstance(final String snippetName, final JsonNode data) {
        return new CodeSnippetExecutionRequest(snippetName, data);
    }
}
