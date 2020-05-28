package club.thewhitewall.nightjar.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Data
public class CodeSnippetModificationRequest {

    private final String value;
    private final String description;

    @NotNull
    private final Set<CodeSnippetQualifier> qualifiers;

    public CodeSnippetModificationRequest(
            final String value,
            final String description,
            final Set<CodeSnippetQualifier> qualifiers
    ) {
        this.value = value;
        this.description = description;
        this.qualifiers = Optional.ofNullable(qualifiers).orElseGet(HashSet::new);
    }

    public static CodeSnippetModificationRequest createInstance(
            final String value,
            final String description,
            final Set<CodeSnippetQualifier> qualifiers
    ) {
        return new CodeSnippetModificationRequest(value, description, qualifiers);
    }
}
