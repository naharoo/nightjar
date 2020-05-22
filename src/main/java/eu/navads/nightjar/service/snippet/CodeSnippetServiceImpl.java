package eu.navads.nightjar.service.snippet;

import eu.navads.nightjar.domain.CodeSnippet;
import eu.navads.nightjar.exception.ResourceNotFoundException;
import eu.navads.nightjar.infra.logging.Loggable;
import eu.navads.nightjar.infra.validation.params.ValidParams;
import eu.navads.nightjar.repository.CodeSnippetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;

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
        return repository.findById(id).orElseThrow(() -> ResourceNotFoundException.createInstance(
                CodeSnippet.class,
                "id: " + id
        ));
    }
}
