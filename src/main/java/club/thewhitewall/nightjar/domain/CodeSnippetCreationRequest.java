package club.thewhitewall.nightjar.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static club.thewhitewall.nightjar.util.Assertion.notBlank;

@Data
public class CodeSnippetCreationRequest {

    @NotBlank
    private final String name;
    private final String value;
    private final String description;

    @NotNull
    private final Set<CodeSnippetQualifier> qualifiers;

    private CodeSnippetCreationRequest(
            final String name,
            final String value,
            final String description,
            final Set<CodeSnippetQualifier> qualifiers
    ) {
        this.name = name;
        this.value = value;
        this.description = description;
        this.qualifiers = Optional.ofNullable(qualifiers).orElseGet(HashSet::new);
    }

    public static CodeSnippetCreationRequest createInstance(
            final String name,
            final String value,
            final String description,
            final Set<CodeSnippetQualifier> qualifiers
    ) {
        return new CodeSnippetCreationRequest(notBlank("name", name), value, description, qualifiers);
    }
}
