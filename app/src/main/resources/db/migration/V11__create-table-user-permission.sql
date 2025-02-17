CREATE TABLE IF NOT EXISTS user_permission (
    id_user BIGINT NOT NULL,
    id_permission BIGINT NOT NULL,
    CONSTRAINT fk_user_permission_user FOREIGN KEY (id_user)
        REFERENCES users (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_user_permission_permission FOREIGN KEY (id_permission)
        REFERENCES permission (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
