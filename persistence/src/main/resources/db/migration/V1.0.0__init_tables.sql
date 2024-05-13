/*
    User table
*/
create table users
(
    id       bigserial    not null
        primary key,
    username varchar(256) not null unique,
    password varchar(256) not null
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
 Permission table
 */
create table permissions
(
    id   serial      not null
        primary key,
    name varchar(64) not null
);

/*
 Many-to-many roles-permissions table
 */
create table roles_permissions
(
    role_id       integer not null
        references roles on delete cascade,
    permission_id integer not null
        references permissions on delete cascade
);

/*
    User-Role many-to-many table
*/
create table users_roles
(
    user_id bigint  not null
        references users on delete cascade,
    role_id integer not null
        references roles on delete cascade,

    CONSTRAINT user_pkey PRIMARY KEY (user_id, role_id)
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
