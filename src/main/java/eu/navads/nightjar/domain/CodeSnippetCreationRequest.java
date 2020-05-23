package eu.navads.nightjar.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;

import static eu.navads.nightjar.util.Assertion.notBlank;

@Data
public class CodeSnippetCreationRequest {

    @NotBlank
    private final String name;
    private final String value;

    private CodeSnippetCreationRequest(final String name, final String value) {
        this.name = name;
        this.value = value;
    }

    public static CodeSnippetCreationRequest createInstance(final String name, final String value) {
        return new CodeSnippetCreationRequest(notBlank(name, name), value);
    }
}
