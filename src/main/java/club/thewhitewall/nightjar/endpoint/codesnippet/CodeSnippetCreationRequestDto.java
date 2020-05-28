package club.thewhitewall.nightjar.endpoint.codesnippet;

import club.thewhitewall.nightjar.domain.CodeSnippetExtraAttribute;
import club.thewhitewall.nightjar.domain.CodeSnippetQualifier;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import club.thewhitewall.nightjar.domain.CodeSnippetCreationRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.Map;
import java.util.Set;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class CodeSnippetCreationRequestDto extends CodeSnippetModificationRequestDto {

    @NotBlank
    private final String name;

    @JsonCreator
    public CodeSnippetCreationRequestDto(
            @JsonProperty("name") final String name,
            @JsonProperty("value") final String value,
            @JsonProperty("description") final String description,
            @JsonProperty("qualifiers") final Set<CodeSnippetQualifier> qualifiers,
            @JsonProperty("extraAttributes") final Map<CodeSnippetExtraAttribute, String> extraAttributes
    ) {
        super(value, description, qualifiers, extraAttributes);
        this.name = name;
    }

    public CodeSnippetCreationRequest toCreationDomain() {
        return CodeSnippetCreationRequest.createInstance(
                getName(),
                getValue(),
                getDescription(),
                getQualifiers(),
                getExtraAttributes()
        );
    }
}
