package club.thewhitewall.nightjar.endpoint.codesnippet;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import club.thewhitewall.nightjar.domain.CodeSnippetCreationRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CodeSnippetCreationRequestDto {

    @NotBlank
    private final String name;
    private final String value;

    @JsonCreator
    public CodeSnippetCreationRequestDto(
            @JsonProperty("name") final String name,
            @JsonProperty("value") final String value
    ) {
        this.name = name;
        this.value = value;
    }

    public CodeSnippetCreationRequest toDomain() {
        return CodeSnippetCreationRequest.createInstance(getName(), getValue());
    }
}
