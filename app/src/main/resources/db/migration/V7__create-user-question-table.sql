CREATE TABLE user_question
(
    user_id bigint NOT NULL,
    question_id bigint NOT NULL,
    CONSTRAINT user_question_pkey PRIMARY KEY (user_id, question_id),
    CONSTRAINT fk7vadekpqp7k8tr32jxphidpea FOREIGN KEY (question_id)
        REFERENCES public.question (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkw1hfnl55qw5osuvxq2kfoyvx FOREIGN KEY (user_id)
        REFERENCES app_user (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)