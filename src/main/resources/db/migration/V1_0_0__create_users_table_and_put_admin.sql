CREATE TABLE users
(
    id                 BIGINT       NOT NULL GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    username           VARCHAR(60)  NULL DEFAULT NULL,
    password           VARCHAR(60)  NULL DEFAULT NULL,
    email              VARCHAR(320) NULL DEFAULT NULL,
    confirmation_token VARCHAR(256) NULL DEFAULT NULL,
    role               VARCHAR(20)  NULL DEFAULT 'USER',
    activated          BOOLEAN      DEFAULT FALSE,
    deleted            BOOLEAN      DEFAULT FALSE

);

ALTER TABLE users
    ADD CONSTRAINT unique_username UNIQUE (username);

INSERT INTO users (username, password, email, role, activated, deleted)
VALUEs ('EdvardKrainiy', '$2a$12$p.n8HSdP9DlN6FZBPNQi3OamZjQKUUlIvbpkZogTc1pgRenyu2UVq',
        'ekrayniy@inbox.ru', 'ADMIN', true, false );