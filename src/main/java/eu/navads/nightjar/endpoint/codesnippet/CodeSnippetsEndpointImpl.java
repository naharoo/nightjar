package eu.navads.nightjar.endpoint.codesnippet;

import eu.navads.nightjar.domain.CodeSnippet;
import eu.navads.nightjar.domain.CodeSnippetCreationRequest;
import eu.navads.nightjar.domain.CodeSnippetModificationRequest;
import eu.navads.nightjar.infra.logging.Loggable;
import eu.navads.nightjar.service.codesnippet.CodeSnippetService;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
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
}
