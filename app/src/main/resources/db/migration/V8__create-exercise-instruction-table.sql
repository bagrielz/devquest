CREATE TABLE exercise_instruction
(
  id bigint NOT NULL,
  indicator character varying(1),
  text text,
  exercise_id bigint,

  CONSTRAINT exercise_instruction_pkey PRIMARY KEY (id),
  CONSTRAINT fkqosrjdo6ix81csggrmlhqqlhs FOREIGN KEY (exercise_id)
      REFERENCES exercise (id)
)