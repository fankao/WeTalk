CREATE TABLE IF NOT EXISTS user_tokens
(
    id            BINARY(16) NOT NULL,
    refresh_token VARCHAR(255) NOT NULL,
    user_id       BIGINT       NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);