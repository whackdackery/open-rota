create table if not exists users
(
    id int auto_increment primary key,
    username varchar(256) not null,
    email varchar(256) not null,
    password varchar(256) not null,
    created timestamp not null,
    updated timestamp not null,
    constraint users_email_uindex
        unique (email),
    constraint users_id_uindex
        unique (id),
    constraint users_username_uindex
        unique (username)
);

INSERT INTO users (username, email, password, created, updated) VALUES ('superadmin', 'superadmin@email.com', 'password', now(), now());
INSERT INTO users (username, email, password, created, updated) VALUES ('admin', 'admin@email.com', 'password', now(), now());
INSERT INTO users (username, email, password, created, updated) VALUES ('enduser', 'enduser@email.com', 'password', now(), now());

create table if not exists roles
(
    id int auto_increment primary key,
    code varchar(128) not null,
    constraint code
        unique (code),
    constraint roles_id_uindex
        unique (id)
);

INSERT INTO roles (id, code) VALUES (1, 'SUPER_ADMIN');
INSERT INTO roles (id, code) VALUES (2, 'ADMIN');
INSERT INTO roles (id, code) VALUES (3, 'EDITOR');
INSERT INTO roles (id, code) VALUES (4, 'SCHEDULER');
INSERT INTO roles (id, code) VALUES (5, 'END_USER');

create table if not exists user_roles
(
    user_id int not null,
    role_id int null,
    constraint user_roles_pk
        unique (user_id, role_id),
    constraint user_roles_roles_id_fk
        foreign key (role_id) references roles (id)
            on delete cascade,
    constraint user_roles_users_id_fk
        foreign key (user_id) references users (id)
            on delete cascade
);

INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (1, 3);
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (2, 5);
INSERT INTO user_roles (user_id, role_id) VALUES (3, 5);