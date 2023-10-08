CREATE TABLE users
(
    id              BIGINT       NOT NULL AUTO_INCREMENT,
    username        VARCHAR(255) NOT NULL UNIQUE,
    email           VARCHAR(255) NOT NULL UNIQUE,
    password        VARCHAR(255) NOT NULL,
    profile_picture VARCHAR(255) NULL,
    last_login_time DATETIME NULL,
    role varchar(16) NOT NULL DEFAULT 'ROLE_USER',
    PRIMARY KEY (id)
);

CREATE TABLE chat_rooms
(
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    name        VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(255) NULL,
    creator_id  BIGINT          NOT NULL,
    FOREIGN KEY (creator_id) REFERENCES users (id),
    PRIMARY KEY (id)
);

CREATE TABLE messages
(
    id           BIGINT       NOT NULL AUTO_INCREMENT,
    sender_id    BIGINT          NOT NULL,
    recipient_id BIGINT          NOT NULL,
    chat_room_id BIGINT          NOT NULL,
    message_text TEXT         NOT NULL,
    timestamp    DATETIME     NOT NULL,
    status       VARCHAR(255) NOT NULL DEFAULT 'sent',
    FOREIGN KEY (sender_id) REFERENCES users (id),
    FOREIGN KEY (recipient_id) REFERENCES users (id),
    FOREIGN KEY (chat_room_id) REFERENCES chat_rooms (id),
    PRIMARY KEY (id)
);

CREATE TABLE chat_room_participants
(
    chat_room_id BIGINT NOT NULL,
    user_id      BIGINT NOT NULL,
    FOREIGN KEY (chat_room_id) REFERENCES chat_rooms (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);