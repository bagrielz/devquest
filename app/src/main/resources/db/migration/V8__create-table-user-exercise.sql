-- V14__create_user_exercise_table.sql

CREATE TABLE user_exercise (
    user_id BIGINT NOT NULL,
    exercise_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, exercise_id),
    CONSTRAINT fk_user_exercise_user FOREIGN KEY (user_id)
        REFERENCES users (id) ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT fk_user_exercise_exercise FOREIGN KEY (exercise_id)
        REFERENCES exercise (id) ON UPDATE NO ACTION ON DELETE NO ACTION
);
