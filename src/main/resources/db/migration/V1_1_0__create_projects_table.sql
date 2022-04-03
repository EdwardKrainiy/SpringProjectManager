CREATE TABLE projects
(
    id                 BIGINT       NOT NULL GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_id            BIGINT       NULL DEFAULT NULL,
    title              VARCHAR(60)  NULL DEFAULT NULL,
    description        VARCHAR(320) NULL DEFAULT NULL,
    issued_at          TIMESTAMP,
    deleted            BOOLEAN      DEFAULT FALSE
);
