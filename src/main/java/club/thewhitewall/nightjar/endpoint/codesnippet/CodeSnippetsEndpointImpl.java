package club.thewhitewall.nightjar.endpoint.codesnippet;

import club.thewhitewall.nightjar.domain.*;
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
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CodeSnippetsEndpointImpl implements CodeSnippetsEndpoint {

    private final CodeSnippetService service;

    public CodeSnippetsEndpointImpl(final CodeSnippetService service) {
        this.service = service;
    }

    @Override
    @Loggable(before = LogLevel.DEBUG, after = LogLevel.INFO)
    public CodeSnippetDto create(@Valid @NotNull final CodeSnippetCreationRequestDto creationRequestDto) {
        final CodeSnippetCreationRequest creationRequest = creationRequestDto.toCreationDomain();

        final CodeSnippet createdSnippet = service.create(creationRequest);

        return CodeSnippetDto.from(createdSnippet);
    }

    @Override
    @Loggable(before = LogLevel.DEBUG, after = LogLevel.INFO)
    public CodeSnippetDto update(
            @NotBlank final String id,
            @Valid @NotNull final CodeSnippetModificationRequestDto modificationRequestDto
    ) {
        final CodeSnippetModificationRequest modificationRequest = modificationRequestDto.toModificationDomain();

        final CodeSnippet updatedSnippet = service.update(id, modificationRequest);

        return CodeSnippetDto.from(updatedSnippet);
    }

    @Override
    @Loggable(before = LogLevel.DEBUG, after = LogLevel.INFO)
    public CodeSnippetDto getById(@NotBlank final String id) {
        final CodeSnippet codeSnippet = service.getById(id);
        return CodeSnippetDto.from(codeSnippet);
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

    @Override
    @Loggable(before = LogLevel.DEBUG, after = LogLevel.INFO)
    public ModelAndView getIndexPage(final ModelAndView modelAndView) {
        modelAndView.setViewName("code-snippets");

        return modelAndView;
    }

    @Override
    @Loggable(before = LogLevel.DEBUG, after = LogLevel.INFO)
    public Set<CodeSnippetQualifier> getAllQualifiers() {
        return Arrays.stream(CodeSnippetQualifier.values()).collect(Collectors.toSet());
    }

    @Override
    @Loggable(before = LogLevel.DEBUG, after = LogLevel.INFO)
    public Set<CodeSnippetExtraAttribute> getAllExtraAttributes() {
        return Arrays.stream(CodeSnippetExtraAttribute.values()).collect(Collectors.toSet());
    }
}
