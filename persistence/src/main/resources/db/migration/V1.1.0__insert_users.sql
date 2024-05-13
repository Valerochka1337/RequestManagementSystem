insert into users(id, username, password)
VALUES (1, 'valerochka1337',
        '$2a$12$MuYlvRKPt9gytCYlJvxho.IHHXquzYYzRWq7CLrbVpy8gryix4bnq'), -- bcrypt 12 for 'password'
       (2, 'anton228', '$2a$12$MuYlvRKPt9gytCYlJvxho.IHHXquzYYzRWq7CLrbVpy8gryix4bnq'),
       (3, 'tema3223', '$2a$12$MuYlvRKPt9gytCYlJvxho.IHHXquzYYzRWq7CLrbVpy8gryix4bnq'),
       (4, 'superuser', '$2a$12$MuYlvRKPt9gytCYlJvxho.IHHXquzYYzRWq7CLrbVpy8gryix4bnq');

insert into roles(id, name)
values (1, 'USER'),
       (2, 'OPERATOR'),
       (3, 'ADMIN');

insert into users_roles(user_id, role_id)
values (1, 1),
       (2, 2),
       (3, 3),
       (4, 1),
       (4, 2),
       (4, 3);

insert into permissions(id, name)
values (1, 'users:read:all'),
       (2, 'users:give_role:operator'),
       (3, 'requests:create'),
       (4, 'requests:read:sent:all'),
       (5, 'requests:accept'),
       (6, 'requests:reject:sent'),
       (7, 'requests:accept:sent'),
       (8, 'requests:read:owned'),
       (9, 'requests:edit:owned:draft'),
       (10, 'requests:send:draft'),
       (11, 'requests:remove:owned');

insert into roles_permissions(role_id, permission_id)
values (1, 3),
       (1, 8),
       (1, 9),
       (1, 10),
       (1, 11),
       (2, 4),
       (2, 6),
       (2, 7),
       (3, 1),
       (3, 2);

