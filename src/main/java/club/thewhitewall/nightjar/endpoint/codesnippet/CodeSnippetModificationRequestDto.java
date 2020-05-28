package club.thewhitewall.nightjar.endpoint.codesnippet;

import club.thewhitewall.nightjar.domain.CodeSnippetQualifier;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import club.thewhitewall.nightjar.domain.CodeSnippetModificationRequest;
import lombok.Data;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Data
public class CodeSnippetModificationRequestDto {

    private final String value;
    private final String description;
    private final Set<CodeSnippetQualifier> qualifiers;

    @JsonCreator
    public CodeSnippetModificationRequestDto(
            @JsonProperty("value") final String value,
            @JsonProperty("description") final String description,
            @JsonProperty("qualifiers") final Set<CodeSnippetQualifier> qualifiers
    ) {
        this.value = value;
        this.description = description;
        this.qualifiers = Optional.ofNullable(qualifiers).orElseGet(HashSet::new);
    }

    public CodeSnippetModificationRequest toDomain() {
        return CodeSnippetModificationRequest.createInstance(getValue(), getDescription(), getQualifiers());
    }
}
