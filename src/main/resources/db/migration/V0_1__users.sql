create table if not exists users
(
    id int auto_increment primary key,
    username varchar(256) not null,
    email varchar(256) not null,
    constraint users_email_uindex
        unique (email),
    constraint users_id_uindex
        unique (id),
    constraint users_username_uindex
        unique (username)
);

INSERT INTO users (username, email) VALUES ('superadmin', 'superadmin@email.com');
INSERT INTO users (username, email) VALUES ('admin', 'admin@email.com');
INSERT INTO users (username, email) VALUES ('enduser', 'enduser@email.com');