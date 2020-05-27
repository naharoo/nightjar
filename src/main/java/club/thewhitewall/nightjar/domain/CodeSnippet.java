package club.thewhitewall.nightjar.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static club.thewhitewall.nightjar.util.Assertion.notBlank;
import static club.thewhitewall.nightjar.util.Assertion.notNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

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

    protected CodeSnippet() {
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
        if (isBlank(value)) {
            this.value = null;
        } else {
            this.value = value.replace("\n", "\r\n").replace("\r\r\n", "\r\n");
        }
        return this;
    }

    private void setCreationDate(final LocalDateTime creationDate) {
        this.creationDate = notNull("creationDate", creationDate);
    }

    private void setModificationDate(final LocalDateTime modificationDate) {
        this.modificationDate = notNull("modificationDate", modificationDate);
    }

    @Transient
    public List<String> getReadyForRenderingValueLines() {
        if (isBlank(getValue())) {
            return Collections.emptyList();
        }

        return Arrays
                .stream(getValue().split("\r\n"))
                .map(line -> line.replace("'", "\""))
                .map(line -> line.replace("\\n", ""))
                .collect(Collectors.toList());
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