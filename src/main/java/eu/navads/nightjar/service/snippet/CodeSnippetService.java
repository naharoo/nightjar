package eu.navads.nightjar.service.snippet;

import eu.navads.nightjar.domain.CodeSnippet;

import javax.validation.constraints.NotBlank;

public interface CodeSnippetService {

    CodeSnippet getById(@NotBlank String id);
}
