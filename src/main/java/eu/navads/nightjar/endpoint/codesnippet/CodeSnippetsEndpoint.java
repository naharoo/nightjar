package eu.navads.nightjar.endpoint.codesnippet;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Validated
@RestController
@RequestMapping("/rest/code-snippets")
public interface CodeSnippetsEndpoint {

    @PostMapping
    CodeSnippetDto create(@Valid @NotNull @RequestBody CodeSnippetCreationRequestDto creationRequestDto);

    @PutMapping("/{id}")
    CodeSnippetDto update(
            @NotBlank @PathVariable("id") String id,
            @Valid @NotNull @RequestBody CodeSnippetModificationRequestDto modificationRequestDto
    );
}
