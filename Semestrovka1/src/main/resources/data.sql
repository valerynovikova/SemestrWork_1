create table if not exists users
(
    id         serial
        constraint users_pkey
            primary key,
    email      varchar(31),
    login      varchar(31),
    nickname   varchar(31),
    password   varchar(255),
    avatar_url varchar
);

create table if not exists reports
(
    id        serial
        constraint reports_pkey
            primary key,
    user_id   integer
        constraint reports_user_id_fkey
            references users,
    title     varchar,
    text      varchar,
    photo_url varchar,
    data      varchar
);

create table if not exists comment_report
(
    id       serial
        constraint comment_report_pkey
            primary key,
    user_id  integer
        constraint comment_report_user_id_fkey
            references users,
    report_id integer
        constraint comment_report_report_id_fkey
            references reports,
    text     varchar
);

create table if not exists articles
(
    id        serial
        constraint articles_pkey
            primary key,
    user_id   integer
        constraint articles_user_id_fkey
            references users,
    title     varchar,
    text      varchar,
    photo_url varchar,
    data      varchar
);

create table if not exists comment_article
(
    id       serial
        constraint comment_article_pkey
            primary key,
    user_id  integer
        constraint comment_article_user_id_fkey
            references users,
    article_id integer
        constraint comment_article_article_id_fkey
            references articles,
    text     varchar
);