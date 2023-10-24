CREATE SEQUENCE IF NOT EXISTS lesson_seq START WITH 1 INCREMENT BY 100;

CREATE SEQUENCE IF NOT EXISTS course_seq START WITH 1 INCREMENT BY 100;

CREATE SEQUENCE IF NOT EXISTS group_seq START WITH 1 INCREMENT BY 100;

CREATE SEQUENCE IF NOT EXISTS user_seq START WITH 1 INCREMENT BY 100;

CREATE SEQUENCE IF NOT EXISTS dep_seq START WITH 1 INCREMENT BY 10;

CREATE TABLE IF NOT EXISTS courses
(
    course_id   BIGINT NOT NULL PRIMARY KEY,
    course_name VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS groups
(
    group_id   BIGINT NOT NULL PRIMARY KEY,
    group_name VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS lessons
(
    lesson_id           BIGINT NOT NULL PRIMARY KEY,
    lesson_name         VARCHAR(100),
    lesson_audience     VARCHAR(50),
    lesson_day_of_week  VARCHAR(10),
    lesson_start_time   TIME,
    lesson_end_time     TIME,
    lesson_course_id    BIGINT REFERENCES courses(course_id),
    lesson_group_id     BIGINT REFERENCES groups(group_id)
);

CREATE TABLE IF NOT EXISTS users
(
    user_id           BIGINT NOT NULL PRIMARY KEY,
    user_name         VARCHAR(255),
    user_surname      VARCHAR(255),
    user_password     VARCHAR(255),
    user_type         VARCHAR(100),
    student_group_id  BIGINT,

    FOREIGN KEY (student_group_id) REFERENCES groups(group_id)
);

CREATE TABLE IF NOT EXISTS teachers_course(
    teacher_id BIGINT REFERENCES users(user_id),
    course_id  BIGINT REFERENCES courses(course_id),
    PRIMARY KEY (teacher_id, course_id)
);

CREATE TABLE IF NOT EXISTS departments
(
    department_id   BIGINT NOT NULL PRIMARY KEY,
    department_name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS department_course(
    course_id     BIGINT REFERENCES courses(course_id),
    department_id BIGINT REFERENCES departments(department_id)
);