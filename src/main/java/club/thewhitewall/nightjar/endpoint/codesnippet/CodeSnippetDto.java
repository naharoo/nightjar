package club.thewhitewall.nightjar.endpoint.codesnippet;

import club.thewhitewall.nightjar.domain.CodeSnippet;
import club.thewhitewall.nightjar.domain.CodeSnippetExtraAttribute;
import club.thewhitewall.nightjar.domain.CodeSnippetQualifier;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.*;

@Data
public class CodeSnippetDto {

    private final String id;
    private final String name;
    private final String description;
    private final String value;
    private final LocalDateTime creationDate;
    private final LocalDateTime modificationDate;
    private final Set<CodeSnippetQualifier> qualifiers;
    private final Map<CodeSnippetExtraAttribute, String> extraAttributes;

    @JsonCreator
    public CodeSnippetDto(
            @JsonProperty("id") final String id,
            @JsonProperty("name") final String name,
            @JsonProperty("description") final String description,
            @JsonProperty("value") final String value,
            @JsonProperty("creationDate") final LocalDateTime creationDate,
            @JsonProperty("modificationDate") final LocalDateTime modificationDate,
            @JsonProperty("qualifiers") final Set<CodeSnippetQualifier> qualifiers,
            @JsonProperty("extraAttributes") final Map<CodeSnippetExtraAttribute, String> extraAttributes
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.value = value;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.qualifiers = Optional.ofNullable(qualifiers).orElseGet(HashSet::new);
        this.extraAttributes = Optional.ofNullable(extraAttributes).orElseGet(HashMap::new);
    }

    public static CodeSnippetDto from(final CodeSnippet snippet) {
        return new CodeSnippetDto(
                snippet.getId(),
                snippet.getName(),
                snippet.getDescription(),
                snippet.getValue(),
                snippet.getCreationDate(),
                snippet.getModificationDate(),
                snippet.getQualifiers(),
                snippet.getExtraAttributes()
        );
    }
}
