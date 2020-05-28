package club.thewhitewall.nightjar.endpoint.codesnippet;

import club.thewhitewall.nightjar.domain.CodeSnippetExtraAttribute;
import club.thewhitewall.nightjar.domain.CodeSnippetQualifier;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import club.thewhitewall.nightjar.domain.CodeSnippetModificationRequest;
import lombok.Data;

import java.util.*;

@Data
public class CodeSnippetModificationRequestDto {

    private final String value;
    private final String description;
    private final Set<CodeSnippetQualifier> qualifiers;
    private final Map<CodeSnippetExtraAttribute, String> extraAttributes;

    @JsonCreator
    public CodeSnippetModificationRequestDto(
            @JsonProperty("value") final String value,
            @JsonProperty("description") final String description,
            @JsonProperty("qualifiers") final Set<CodeSnippetQualifier> qualifiers,
            @JsonProperty("extraAttributes") final Map<CodeSnippetExtraAttribute, String> extraAttributes
    ) {
        this.value = value;
        this.description = description;
        this.qualifiers = Optional.ofNullable(qualifiers).orElseGet(HashSet::new);
        this.extraAttributes = Optional.ofNullable(extraAttributes).orElseGet(HashMap::new);
    }

    public CodeSnippetModificationRequest toModificationDomain() {
        return CodeSnippetModificationRequest.createInstance(
                getValue(),
                getDescription(),
                getQualifiers(),
                getExtraAttributes()
        );
    }
}
