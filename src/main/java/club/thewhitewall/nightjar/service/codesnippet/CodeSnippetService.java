package club.thewhitewall.nightjar.service.codesnippet;

import club.thewhitewall.nightjar.domain.CodeSnippet;
import club.thewhitewall.nightjar.domain.CodeSnippetCreationRequest;
import club.thewhitewall.nightjar.domain.CodeSnippetModificationRequest;
import club.thewhitewall.nightjar.domain.CodeSnippetSearchRequest;
import org.springframework.data.domain.Page;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface CodeSnippetService {

    CodeSnippet getById(@NotBlank String id);

    CodeSnippet create(@Valid @NotNull CodeSnippetCreationRequest creationRequest);

    CodeSnippet update(@NotBlank String id, @Valid @NotNull CodeSnippetModificationRequest modificationRequest);

    Page<CodeSnippet> search(@NotNull CodeSnippetSearchRequest searchRequest);

    CodeSnippet getByName(@NotBlank String name);
}
