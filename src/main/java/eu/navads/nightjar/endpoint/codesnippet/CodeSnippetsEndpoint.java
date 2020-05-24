package eu.navads.nightjar.endpoint.codesnippet;

import eu.navads.nightjar.endpoint.PageDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Validated
@RestController
@RequestMapping
public interface CodeSnippetsEndpoint {

    @PostMapping("/rest/code-snippets")
    CodeSnippetDto create(@Valid @NotNull @RequestBody CodeSnippetCreationRequestDto creationRequestDto);

    @PutMapping("/rest/code-snippets/{id}")
    CodeSnippetDto update(
            @NotBlank @PathVariable("id") String id,
            @Valid @NotNull @RequestBody CodeSnippetModificationRequestDto modificationRequestDto
    );

    @GetMapping("/rest/code-snippets/search")
    PageDto<CodeSnippetDto> search(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    );

    @GetMapping("/code-snippets")
    ModelAndView getSnippetsPage(ModelAndView modelAndView);
}
