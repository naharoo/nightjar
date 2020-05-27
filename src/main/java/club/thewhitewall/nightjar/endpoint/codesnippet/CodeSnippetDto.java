package club.thewhitewall.nightjar.endpoint.codesnippet;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import club.thewhitewall.nightjar.domain.CodeSnippet;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CodeSnippetDto {

    private final String id;
    private final String name;
    private final String value;
    private final LocalDateTime creationDate;
    private final LocalDateTime modificationDate;

    @JsonCreator
    public CodeSnippetDto(
            @JsonProperty("id") final String id,
            @JsonProperty("name") final String name,
            @JsonProperty("value") final String value,
            @JsonProperty("creationDate") final LocalDateTime creationDate,
            @JsonProperty("modificationDate") final LocalDateTime modificationDate
    ) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
    }

    public static CodeSnippetDto from(final CodeSnippet snippet) {
        return new CodeSnippetDto(
                snippet.getId(),
                snippet.getName(),
                snippet.getValue(),
                snippet.getCreationDate(),
                snippet.getModificationDate()
        );
    }
}
