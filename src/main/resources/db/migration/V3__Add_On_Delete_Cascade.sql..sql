ALTER TABLE lessons
DROP CONSTRAINT IF EXISTS lessons_lesson_course_id_fkey,
    ADD CONSTRAINT lessons_lesson_course_id_fkey
        FOREIGN KEY (lesson_course_id)
        REFERENCES courses(course_id)
        ON DELETE CASCADE;

ALTER TABLE lessons
DROP CONSTRAINT IF EXISTS lessons_lesson_group_id_fkey,
    ADD CONSTRAINT lessons_lesson_group_id_fkey
        FOREIGN KEY (lesson_group_id)
        REFERENCES groups(group_id)
        ON DELETE CASCADE;

ALTER TABLE users
DROP CONSTRAINT IF EXISTS users_student_group_id_fkey,
    ADD CONSTRAINT users_student_group_id_fkey
        FOREIGN KEY (student_group_id)
        REFERENCES groups(group_id)
        ON DELETE CASCADE;

ALTER TABLE teachers_course
DROP CONSTRAINT IF EXISTS teachers_course_teacher_id_fkey,
    ADD CONSTRAINT teachers_course_teacher_id_fkey
        FOREIGN KEY (teacher_id)
        REFERENCES users(user_id)
        ON DELETE CASCADE;

ALTER TABLE teachers_course
DROP CONSTRAINT IF EXISTS teachers_course_course_id_fkey,
    ADD CONSTRAINT teachers_course_course_id_fkey
        FOREIGN KEY (course_id)
        REFERENCES courses(course_id)
        ON DELETE CASCADE;

ALTER TABLE department_course
DROP CONSTRAINT IF EXISTS department_course_course_id_fkey,
    ADD CONSTRAINT department_course_course_id_fkey
        FOREIGN KEY (course_id)
        REFERENCES courses(course_id)
        ON DELETE CASCADE;

ALTER TABLE department_course
DROP CONSTRAINT IF EXISTS department_course_department_id_fkey,
    ADD CONSTRAINT department_course_department_id_fkey
        FOREIGN KEY (department_id)
        REFERENCES departments(department_id)
        ON DELETE CASCADE;