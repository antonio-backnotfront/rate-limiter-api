CREATE TABLE policy
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    capacity    INT NULL,
    window_size BIGINT NULL,
    user_id     BIGINT NOT NULL,
    CONSTRAINT pk_policy PRIMARY KEY (id)
);

CREATE TABLE `role`
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255) NULL,
    CONSTRAINT pk_role PRIMARY KEY (id)
);

CREATE TABLE user
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    login    VARCHAR(255) NULL,
    password VARCHAR(255) NULL,
    role_id  BIGINT NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE policy
    ADD CONSTRAINT FK_POLICY_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE user
    ADD CONSTRAINT FK_USER_ON_ROLEID FOREIGN KEY (role_id) REFERENCES `role` (id);