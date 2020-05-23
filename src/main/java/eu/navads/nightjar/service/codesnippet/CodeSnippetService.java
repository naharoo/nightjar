package eu.navads.nightjar.service.codesnippet;

import eu.navads.nightjar.domain.CodeSnippet;
import eu.navads.nightjar.domain.CodeSnippetCreationRequest;
import eu.navads.nightjar.domain.CodeSnippetModificationRequest;
import eu.navads.nightjar.domain.CodeSnippetSearchRequest;
import org.springframework.data.domain.Page;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface CodeSnippetService {

    CodeSnippet getById(@NotBlank String id);

    CodeSnippet create(@Valid @NotNull CodeSnippetCreationRequest creationRequest);

    CodeSnippet update(@NotBlank String id, @Valid @NotNull CodeSnippetModificationRequest modificationRequest);

    Page<CodeSnippet> search(@NotNull CodeSnippetSearchRequest searchRequest);
}
