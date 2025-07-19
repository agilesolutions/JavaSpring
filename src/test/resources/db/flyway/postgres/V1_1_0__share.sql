CREATE TABLE if not exists share (
    id          BIGINT primary key auto_increment   not null,
    company     varchar                             not null,
    quantity    BIGINT                              not null,
    publishDate timestamp                           default current_timestamp
);