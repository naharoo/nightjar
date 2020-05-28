alter table if exists code_snippet
    add column if not exists description varchar(2047);

create table code_snippet_qualifier
(
    snippet_id varchar(36)  not null,
    name       varchar(255) not null,

    constraint code_snippet_qualifier_snippet_id_name_pkey primary key (snippet_id, name),
    constraint code_snippet_qualifier_snippet_id_fk foreign key (snippet_id) references code_snippet (id)
);


