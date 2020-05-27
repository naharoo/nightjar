package club.thewhitewall.nightjar.endpoint.codesnippet;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import club.thewhitewall.nightjar.domain.CodeSnippetModificationRequest;
import lombok.Data;

@Data
public class CodeSnippetModificationRequestDto {

    private final String value;

    @JsonCreator
    public CodeSnippetModificationRequestDto(@JsonProperty("value") final String value) {
        this.value = value;
    }

    public CodeSnippetModificationRequest toDomain() {
        return CodeSnippetModificationRequest.createInstance(getValue());
    }
}
