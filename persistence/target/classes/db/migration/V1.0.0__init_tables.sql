/*
    User table
*/
create table users
(
    id         bigserial    not null unique
        primary key,
    username   varchar(64)  not null,
    password   varchar(256) not null,
    first_name varchar(64)  not null,
    last_name  varchar(64)
);

/*
    Role table
*/
create table roles
(
    id   serial      not null
        primary key,
    name varchar(64) not null
);

/*
    User-Role many-to-many table
*/
create table users_roles
(
    user_id bigint  not null
        references users on delete cascade,
    role_id integer not null
        references roles on delete cascade
);


/*
    Requests table
*/
create table requests
(
    id            bigserial     not null,
    status        varchar(64)   not null,
    message       varchar(1024) not null,
    author_id     bigint        not null
        references users on delete set null,
    creation_date timestamp     not null,
    sent_date     timestamp
);
