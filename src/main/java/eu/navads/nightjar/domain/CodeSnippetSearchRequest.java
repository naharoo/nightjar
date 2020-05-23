package eu.navads.nightjar.domain;

import lombok.Data;

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
        return new CodeSnippetSearchRequest(name, page, size);
    }
}
