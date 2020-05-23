package eu.navads.nightjar.endpoint.codesnippet;

import eu.navads.nightjar.endpoint.PageDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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

    @GetMapping("/search")
    PageDto<CodeSnippetDto> search(
            @RequestParam(value = "name", required = false) String name,
            @Min(0) @RequestParam(value = "page", defaultValue = "0") int page,
            @Min(0) @Max(100) @RequestParam(value = "size", defaultValue = "20") int size
    );
}
