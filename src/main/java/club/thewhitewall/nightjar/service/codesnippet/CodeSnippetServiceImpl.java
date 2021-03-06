package club.thewhitewall.nightjar.service.codesnippet;

import club.thewhitewall.nightjar.domain.CodeSnippet;
import club.thewhitewall.nightjar.domain.CodeSnippetCreationRequest;
import club.thewhitewall.nightjar.domain.CodeSnippetModificationRequest;
import club.thewhitewall.nightjar.domain.CodeSnippetSearchRequest;
import club.thewhitewall.nightjar.exception.ResourceAlreadyExistsException;
import club.thewhitewall.nightjar.exception.ResourceNotFoundException;
import club.thewhitewall.nightjar.infra.logging.Loggable;
import club.thewhitewall.nightjar.infra.validation.params.ValidParams;
import club.thewhitewall.nightjar.repository.CodeSnippetRepository;
import club.thewhitewall.nightjar.service.codesnippet.modification.CodeSnippetModificationCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
public class CodeSnippetServiceImpl implements CodeSnippetService {

    private final CodeSnippetRepository repository;
    private final List<CodeSnippetModificationCustomizer> modificationCustomizers;

    @Autowired
    public CodeSnippetServiceImpl(
            final CodeSnippetRepository repository,
            final List<CodeSnippetModificationCustomizer> modificationCustomizers
    ) {
        this.repository = repository;
        this.modificationCustomizers = modificationCustomizers;
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

        final Optional<CodeSnippet> existingCodeSnippetWithSameNameOpt = findByName(name);
        if (existingCodeSnippetWithSameNameOpt.isPresent()) {
            throw ResourceAlreadyExistsException.createInstance(CodeSnippet.class, "name", name);
        }

        final CodeSnippet newCodeSnippet = CodeSnippet.named(name);
        modificationCustomizers.forEach(customizer -> customizer.customize(newCodeSnippet, creationRequest));

        return repository.save(newCodeSnippet);
    }

    private Optional<CodeSnippet> findByName(final String name) {
        return repository.findByName(name);
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

        modificationCustomizers.forEach(customizer -> customizer.customize(snippet, modificationRequest));

        return repository.save(snippet);
    }

    @Override
    @Loggable
    @ValidParams
    @Transactional(readOnly = true)
    public Page<CodeSnippet> search(@NotNull final CodeSnippetSearchRequest searchRequest) {
        return repository.search(
                searchRequest.getName(),
                PageRequest.of(searchRequest.getPage(), searchRequest.getSize(), Sort.by(desc("modificationDate")))
        );
    }

    @Override
    @Loggable
    @ValidParams
    @Transactional(readOnly = true)
    public CodeSnippet getByName(@NotBlank final String name) {
        final Optional<CodeSnippet> codeSnippetOpt = findByName(name);
        return codeSnippetOpt.orElseThrow(() -> ResourceNotFoundException.createInstance(
                CodeSnippet.class,
                String.format("name: %s", name)
        ));
    }
}
