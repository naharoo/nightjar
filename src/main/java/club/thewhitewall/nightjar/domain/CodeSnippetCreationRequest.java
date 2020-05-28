package club.thewhitewall.nightjar.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.Map;
import java.util.Set;

import static club.thewhitewall.nightjar.util.Assertion.notBlank;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class CodeSnippetCreationRequest extends CodeSnippetModificationRequest {

    @NotBlank
    private final String name;

    private CodeSnippetCreationRequest(
            final String name,
            final String value,
            final String description,
            final Set<CodeSnippetQualifier> qualifiers,
            final Map<CodeSnippetExtraAttribute, String> extraAttributes
    ) {
        super(value, description, qualifiers, extraAttributes);
        this.name = name;
    }

    public static CodeSnippetCreationRequest createInstance(
            final String name,
            final String value,
            final String description,
            final Set<CodeSnippetQualifier> qualifiers,
            final Map<CodeSnippetExtraAttribute, String> extraAttributes
    ) {
        return new CodeSnippetCreationRequest(notBlank("name", name), value, description, qualifiers, extraAttributes);
    }
}
