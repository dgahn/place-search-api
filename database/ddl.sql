create table search_place_history
(
    id         bigint       not null
        primary key,
    count      int          not null,
    created_at datetime(6)  null,
    keyword    varchar(255) null,
    updated_at datetime(6)  null,
    version    bigint       not null
);

create index search_place_history_count_index
    on search_place_history (count);

create index search_place_history_keyword_index
    on search_place_history (keyword);

