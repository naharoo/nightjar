package club.thewhitewall.nightjar.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.*;

@Data
public class CodeSnippetModificationRequest {

    private final String value;
    private final String description;

    @NotNull
    private final Set<CodeSnippetQualifier> qualifiers;

    @NotNull
    private final Map<CodeSnippetExtraAttribute, String> extraAttributes;

    public CodeSnippetModificationRequest(
            final String value,
            final String description,
            final Set<CodeSnippetQualifier> qualifiers,
            final Map<CodeSnippetExtraAttribute, String> extraAttributes
    ) {
        this.value = value;
        this.description = description;
        this.qualifiers = Optional.ofNullable(qualifiers).orElseGet(HashSet::new);
        this.extraAttributes = Optional.ofNullable(extraAttributes).orElseGet(HashMap::new);
    }

    public static CodeSnippetModificationRequest createInstance(
            final String value,
            final String description,
            final Set<CodeSnippetQualifier> qualifiers,
            final Map<CodeSnippetExtraAttribute, String> extraAttributes
    ) {
        return new CodeSnippetModificationRequest(value, description, qualifiers, extraAttributes);
    }
}
