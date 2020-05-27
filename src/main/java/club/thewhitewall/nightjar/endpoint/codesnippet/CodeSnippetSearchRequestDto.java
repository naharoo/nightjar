package club.thewhitewall.nightjar.endpoint.codesnippet;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CodeSnippetSearchRequestDto {

    private final String name;
    private final int page;
    private final int size;

    @JsonCreator
    public CodeSnippetSearchRequestDto(
            @JsonProperty("name") final String name,
            @JsonProperty("page") final int page,
            @JsonProperty("size") final int size
    ) {
        this.name = name;
        this.page = page;
        this.size = size;
    }
}
