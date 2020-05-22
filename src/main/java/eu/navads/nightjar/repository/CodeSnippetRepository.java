package eu.navads.nightjar.repository;

import eu.navads.nightjar.domain.CodeSnippet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeSnippetRepository extends JpaRepository<CodeSnippet, String> {
}
