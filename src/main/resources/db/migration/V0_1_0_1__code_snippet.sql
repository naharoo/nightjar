create table code_snippet
(
    id                varchar(36)  not null,
    name              varchar(255) not null,
    value             text,
    creation_date     timestamp    not null,
    modification_date timestamp    not null,

    constraint code_snippet_id_pkey primary key (id),
    constraint code_snippet_name_uk unique (name)
);
