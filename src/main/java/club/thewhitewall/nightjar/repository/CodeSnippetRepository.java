package club.thewhitewall.nightjar.repository;

import club.thewhitewall.nightjar.domain.CodeSnippet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CodeSnippetRepository extends JpaRepository<CodeSnippet, String> {

    Optional<CodeSnippet> findByName(String name);

    @Query("select cs " +
            "from CodeSnippet cs " +
            "where :name is null or lower(cs.name) like lower(concat('%', '', :name, '%')) ")
    Page<CodeSnippet> search(@Param("name") String name, Pageable pageable);
}
