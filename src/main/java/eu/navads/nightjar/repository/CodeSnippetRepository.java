package eu.navads.nightjar.repository;

import eu.navads.nightjar.domain.CodeSnippet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CodeSnippetRepository extends JpaRepository<CodeSnippet, String> {

    Optional<CodeSnippet> findByName(String name);
}
