package club.thewhitewall.nightjar.domain;

import lombok.Data;

@Data
public class CodeSnippetModificationRequest {

    private final String value;

    public CodeSnippetModificationRequest(final String value) {
        this.value = value;
    }

    public static CodeSnippetModificationRequest createInstance(final String value) {
        return new CodeSnippetModificationRequest(value);
    }
}
