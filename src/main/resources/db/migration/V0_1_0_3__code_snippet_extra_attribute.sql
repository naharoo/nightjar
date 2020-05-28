create table code_snippet_extra_attribute
(
    snippet_id      varchar(36)   not null,
    extra_attribute varchar(255)  not null,
    value           varchar(1023) not null,

    constraint code_snippet_extra_attribute_snippet_id_extra_attribute_pkey primary key (snippet_id, extra_attribute),
    constraint code_snippet_extra_attribute_snippet_id_fk foreign key (snippet_id) references code_snippet (id)
);
