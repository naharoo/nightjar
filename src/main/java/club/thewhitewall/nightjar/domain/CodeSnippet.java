package club.thewhitewall.nightjar.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
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
    @Column(name = "description")
    private String description;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false)
    @ElementCollection(targetClass = CodeSnippetQualifier.class, fetch = FetchType.EAGER)
    @CollectionTable(
            name = "code_snippet_qualifier",
            joinColumns = @JoinColumn(
                    name = "snippet_id",
                    referencedColumnName = "id",
                    nullable = false,
                    foreignKey = @ForeignKey(name = "code_snippet_qualifier_snippet_id_fk")
            ),
            foreignKey = @ForeignKey(name = "code_snippet_qualifier_snippet_id_fk"),
            uniqueConstraints = {
                    @UniqueConstraint(name = "code_snippet_qualifier_snippet_id_name_pkey", columnNames = {
                            "snippet_id",
                            "name"
                    })
            },
            indexes = @Index(name = "code_snippet_qualifier_snippet_id_name_pkey", columnList = "snippet_id, name", unique = true)
    )
    private Set<CodeSnippetQualifier> qualifiers = new HashSet<>();

    @Getter
    @Column(name = "value")
    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "extra_attribute")
    @CollectionTable(
            name = "code_snippet_extra_attribute",
            joinColumns = @JoinColumn(
                    name = "snippet_id",
                    referencedColumnName = "id",
                    nullable = false,
                    foreignKey = @ForeignKey(name = "code_snippet_extra_attribute_snippet_id_fk")
            ),
            foreignKey = @ForeignKey(name = "code_snippet_extra_attribute_snippet_id_fk"),
            uniqueConstraints = @UniqueConstraint(
                    name = "code_snippet_extra_attribute_snippet_id_extra_attribute_pkey",
                    columnNames = {"snippet_id", "extra_attribute"}
            ),
            indexes = @Index(
                    name = "code_snippet_extra_attribute_snippet_id_extra_attribute_pkey",
                    columnList = "snippet_id, extra_attribute",
                    unique = true
            )
    )
    private Map<CodeSnippetExtraAttribute, String> extraAttributes = new EnumMap<>(CodeSnippetExtraAttribute.class);

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

    public CodeSnippet setQualifiers(final Set<CodeSnippetQualifier> qualifiers) {
        this.qualifiers = notNull("qualifiers", qualifiers);
        return this;
    }

    public boolean containsQualifier(final CodeSnippetQualifier qualifier) {
        return getQualifiers().contains(notNull("qualifier", qualifier));
    }

    public CodeSnippet addQualifier(final CodeSnippetQualifier qualifier) {
        getQualifiers().add(notNull("qualifier", qualifier));
        return this;
    }

    public CodeSnippet removeQualifier(final CodeSnippetQualifier qualifier) {
        getQualifiers().remove(notNull("qualifier", qualifier));
        return this;
    }

    public CodeSnippet setExtraAttributes(final Map<CodeSnippetExtraAttribute, String> extraAttributes) {
        this.extraAttributes = notNull("extraAttributes", extraAttributes);
        return this;
    }

    public CodeSnippet setExtraAttributeValue(final CodeSnippetExtraAttribute extraAttribute, final String value) {
        getExtraAttributes().put(notNull("extraAttribute", extraAttribute), value);
        return this;
    }

    public CodeSnippet removeExtraAttribute(final CodeSnippetExtraAttribute extraAttribute) {
        getExtraAttributes().remove(notNull("extraAttribute", extraAttribute));
        return this;
    }

    public String getExtraAttributeValue(final CodeSnippetExtraAttribute extraAttribute) {
        return getExtraAttributes().get(notNull("extraAttribute", extraAttribute));
    }

    public CodeSnippet setDescription(final String description) {
        this.description = description;
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
