create table search_place_history
(
    keyword    varchar(255) not null primary key,
    count      int          not null,
    created_at datetime(6)  null,
    updated_at datetime(6)  null,
    version    bigint       not null
);

create index search_place_history_count_index
    on search_place_history (count);
