package club.thewhitewall.nightjar.endpoint.codesnippet;

import club.thewhitewall.nightjar.domain.CodeSnippetQualifier;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import club.thewhitewall.nightjar.domain.CodeSnippetCreationRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Data
public class CodeSnippetCreationRequestDto {

    @NotBlank
    private final String name;
    private final String value;
    private final String description;
    private final Set<CodeSnippetQualifier> qualifiers;

    @JsonCreator
    public CodeSnippetCreationRequestDto(
            @JsonProperty("name") final String name,
            @JsonProperty("value") final String value,
            @JsonProperty("description") final String description,
            @JsonProperty("qualifiers") final Set<CodeSnippetQualifier> qualifiers
    ) {
        this.name = name;
        this.value = value;
        this.description = description;
        this.qualifiers = Optional.ofNullable(qualifiers).orElseGet(HashSet::new);
    }

    public CodeSnippetCreationRequest toDomain() {
        return CodeSnippetCreationRequest.createInstance(getName(), getValue(), getDescription(), getQualifiers());
    }
}
