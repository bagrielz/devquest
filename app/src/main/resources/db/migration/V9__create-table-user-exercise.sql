
CREATE TABLE public.user_exercise (
    user_id BIGINT NOT NULL,
    exercise_id BIGINT NOT NULL,
    CONSTRAINT user_exercise_pkey PRIMARY KEY (user_id, exercise_id),
    CONSTRAINT fk_exercise FOREIGN KEY (exercise_id) REFERENCES public.exercise (id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES public.users (id)
);