CREATE TABLE public.user_exercise
(
    user_id bigint NOT NULL,
    exercise_id bigint NOT NULL,
    CONSTRAINT user_exercise_pkey PRIMARY KEY (user_id, exercise_id),
    CONSTRAINT fk4dsfvd3ee924pwq4078equ1tu FOREIGN KEY (exercise_id)
        REFERENCES public.exercise (id),
    CONSTRAINT fke4uuabfgr057oiauag0cysh32 FOREIGN KEY (user_id)
        REFERENCES app_user (id)
);