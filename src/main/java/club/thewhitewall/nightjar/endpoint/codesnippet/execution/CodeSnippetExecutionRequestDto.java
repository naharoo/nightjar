package club.thewhitewall.nightjar.endpoint.codesnippet.execution;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import club.thewhitewall.nightjar.domain.CodeSnippetExecutionRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Data
public class CodeSnippetExecutionRequestDto {

    @NotBlank
    private final String snippetName;

    private final JsonNode data;

    @JsonCreator
    public CodeSnippetExecutionRequestDto(
            @JsonProperty("snippetName") final String snippetName,
            @JsonProperty("data") final JsonNode data
    ) {
        this.snippetName = snippetName;
        this.data = Optional.ofNullable(data).orElse(NullNode.getInstance());
    }

    public CodeSnippetExecutionRequest toDomain() {
        return CodeSnippetExecutionRequest.createInstance(getSnippetName(), getData());
    }
}
