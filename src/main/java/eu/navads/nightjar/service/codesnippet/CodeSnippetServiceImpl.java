package eu.navads.nightjar.service.codesnippet;

import eu.navads.nightjar.domain.CodeSnippet;
import eu.navads.nightjar.domain.CodeSnippetCreationRequest;
import eu.navads.nightjar.domain.CodeSnippetModificationRequest;
import eu.navads.nightjar.exception.ResourceAlreadyExistsException;
import eu.navads.nightjar.exception.ResourceNotFoundException;
import eu.navads.nightjar.infra.logging.Loggable;
import eu.navads.nightjar.infra.validation.params.ValidParams;
import eu.navads.nightjar.repository.CodeSnippetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
public class CodeSnippetServiceImpl implements CodeSnippetService {

    private final CodeSnippetRepository repository;

    public CodeSnippetServiceImpl(final CodeSnippetRepository repository) {
        this.repository = repository;
    }

    @Override
    @Loggable
    @ValidParams
    @Transactional(readOnly = true)
    public CodeSnippet getById(@NotBlank final String id) {
        return findById(id).orElseThrow(() -> ResourceNotFoundException.createInstance(
                CodeSnippet.class,
                "id: " + id
        ));
    }

    private Optional<CodeSnippet> findById(@NotBlank final String id) {
        return repository.findById(id);
    }

    @Override
    @Loggable
    @ValidParams
    @Transactional
    public CodeSnippet create(@Valid @NotNull final CodeSnippetCreationRequest creationRequest) {
        final String name = creationRequest.getName();

        final Optional<CodeSnippet> existingCodeSnippetWithSameNameOpt = repository.findByName(name);
        if (existingCodeSnippetWithSameNameOpt.isPresent()) {
            throw ResourceAlreadyExistsException.createInstance(CodeSnippet.class, "name", name);
        }

        final CodeSnippet newCodeSnippet = CodeSnippet.named(name).setValue(creationRequest.getValue());

        return repository.save(newCodeSnippet);
    }

    @Override
    @Loggable
    @ValidParams
    @Transactional
    public CodeSnippet update(
            @NotBlank final String id,
            @Valid @NotNull final CodeSnippetModificationRequest modificationRequest
    ) {
        final CodeSnippet snippet = getById(id);

        final CodeSnippet codeSnippetToBeUpdated = snippet.setValue(modificationRequest.getValue());

        return repository.save(codeSnippetToBeUpdated);
    }
}
