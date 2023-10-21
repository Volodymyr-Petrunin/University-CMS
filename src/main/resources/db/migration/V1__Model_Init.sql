CREATE SEQUENCE IF NOT EXISTS lesson_seq START WITH 1 INCREMENT BY 100;

CREATE SEQUENCE IF NOT EXISTS course_seq START WITH 1 INCREMENT BY 100;

CREATE SEQUENCE IF NOT EXISTS group_seq START WITH 1 INCREMENT BY 100;

CREATE SEQUENCE IF NOT EXISTS user_seq START WITH 1 INCREMENT BY 100;

CREATE SEQUENCE IF NOT EXISTS dep_seq START WITH 1 INCREMENT BY 10;

CREATE TABLE lessons
(
    lesson_id         BIGINT NOT NULL PRIMARY KEY,
    lesson_name       VARCHAR(100),
    lesson_audience   VARCHAR(50)
);

CREATE TABLE courses
(
    course_id   BIGINT NOT NULL PRIMARY KEY,
    course_name VARCHAR(100)
);

CREATE TABLE groups
(
    group_id   BIGINT NOT NULL PRIMARY KEY,
    group_name VARCHAR(100)
);

CREATE TABLE users
(
    user_id        BIGINT NOT NULL PRIMARY KEY,
    user_name      VARCHAR(255),
    user_surname   VARCHAR(255),
    user_password  VARCHAR(255),
    user_type      VARCHAR(100)
);

CREATE TABLE departments
(
    department_id   BIGINT NOT NULL PRIMARY KEY,
    department_name VARCHAR(255)
);