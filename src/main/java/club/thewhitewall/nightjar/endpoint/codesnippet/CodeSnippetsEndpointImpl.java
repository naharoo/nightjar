package club.thewhitewall.nightjar.endpoint.codesnippet;

import club.thewhitewall.nightjar.domain.CodeSnippet;
import club.thewhitewall.nightjar.domain.CodeSnippetCreationRequest;
import club.thewhitewall.nightjar.domain.CodeSnippetModificationRequest;
import club.thewhitewall.nightjar.domain.CodeSnippetSearchRequest;
import club.thewhitewall.nightjar.endpoint.PageDto;
import club.thewhitewall.nightjar.infra.logging.Loggable;
import club.thewhitewall.nightjar.service.codesnippet.CodeSnippetService;
import org.springframework.boot.logging.LogLevel;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Component
public class CodeSnippetsEndpointImpl implements CodeSnippetsEndpoint {

    private final CodeSnippetService service;

    public CodeSnippetsEndpointImpl(final CodeSnippetService service) {
        this.service = service;
    }

    @Override
    @Loggable(before = LogLevel.DEBUG, after = LogLevel.INFO)
    public CodeSnippetDto create(@Valid @NotNull final CodeSnippetCreationRequestDto creationRequestDto) {
        final CodeSnippetCreationRequest creationRequest = creationRequestDto.toDomain();

        final CodeSnippet createdSnippet = service.create(creationRequest);

        return CodeSnippetDto.from(createdSnippet);
    }

    @Override
    @Loggable(before = LogLevel.DEBUG, after = LogLevel.INFO)
    public CodeSnippetDto update(
            @NotBlank final String id,
            @Valid @NotNull final CodeSnippetModificationRequestDto modificationRequestDto
    ) {
        final CodeSnippetModificationRequest modificationRequest = modificationRequestDto.toDomain();

        final CodeSnippet updatedSnippet = service.update(id, modificationRequest);

        return CodeSnippetDto.from(updatedSnippet);
    }

    @Override
    @Loggable(before = LogLevel.DEBUG, after = LogLevel.INFO)
    public PageDto<CodeSnippetDto> search(final String name, @Min(0) final int page, @Min(0) @Max(100) final int size) {
        final CodeSnippetSearchRequest searchRequest = CodeSnippetSearchRequest.createInstance(name, page, size);

        final Page<CodeSnippet> pageOfSnippets = service.search(searchRequest);

        return PageDto.from(pageOfSnippets, CodeSnippetDto::from);
    }

    @Override
    @Loggable(before = LogLevel.DEBUG, after = LogLevel.INFO)
    public ModelAndView getSnippetsPage(final ModelAndView modelAndView) {
        modelAndView.setViewName("code-snippets");

        return modelAndView;
    }
}
