ALTER TABLE lessons
    ADD COLUMN lesson_teacher_id BIGINT REFERENCES users(user_id);