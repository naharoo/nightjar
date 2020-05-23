package eu.navads.nightjar.domain;

import lombok.Data;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Data
public class CodeSnippetSearchRequest {

    private final String name;
    private final int page;
    private final int size;

    private CodeSnippetSearchRequest(final String name, final int page, final int size) {
        this.name = name;
        this.page = page;
        this.size = size;
    }

    public static CodeSnippetSearchRequest createInstance(final String name, final int page, final int size) {
        return new CodeSnippetSearchRequest(isBlank(name) ? null : name, page, size);
    }
}
