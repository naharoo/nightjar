package eu.navads.nightjar.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

import static eu.navads.nightjar.util.Assertion.notBlank;
import static eu.navads.nightjar.util.Assertion.notNull;

@Entity
@ToString
@EqualsAndHashCode
@Access(AccessType.FIELD)
@Table(name = "code_snippet",
        uniqueConstraints = {
                @UniqueConstraint(name = "code_snippet_id_pkey", columnNames = "id"),
                @UniqueConstraint(name = "code_snippet_name_uk", columnNames = "name")
        },
        indexes = {
                @Index(name = "code_snippet_id_pkey", columnList = "id", unique = true),
                @Index(name = "code_snippet_name_uk", columnList = "name", unique = true)
        }
)
public class CodeSnippet {

    @Id
    @Getter
    @Column(name = "id", nullable = false, unique = true, updatable = false, length = 36)
    private String id;

    @Getter
    @Column(name = "name", nullable = false, unique = true, updatable = false)
    private String name;

    @Getter
    @Column(name = "value")
    private String value;

    @Getter
    @Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @Getter
    @Column(name = "modification_date", nullable = false)
    private LocalDateTime modificationDate;

    CodeSnippet() {
        // For Hibernate
    }

    public static CodeSnippet named(final String name) {
        final CodeSnippet codeSnippet = new CodeSnippet();
        codeSnippet.setName(name);
        return codeSnippet;
    }

    private void setId(final String id) {
        this.id = notBlank("id", id);
    }

    private void setName(final String name) {
        this.name = notBlank("name", name);
    }

    public CodeSnippet setValue(final String value) {
        this.value = value;
        return this;
    }

    private void setCreationDate(final LocalDateTime creationDate) {
        this.creationDate = notNull("creationDate", creationDate);
    }

    private void setModificationDate(final LocalDateTime modificationDate) {
        this.modificationDate = notNull("modificationDate", modificationDate);
    }

    @PrePersist
    private void prePersist() {
        setId(UUID.randomUUID().toString());
        final LocalDateTime currentDateTime = LocalDateTime.now();
        setCreationDate(currentDateTime);
        setModificationDate(currentDateTime);
    }

    @PreUpdate
    private void preUpdate() {
        setModificationDate(LocalDateTime.now());
    }
}
