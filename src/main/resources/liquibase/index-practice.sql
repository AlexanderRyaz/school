-- liquibase formatted sql

-- changeset aryazanov:1
CREATE INDEX students_sh_idx ON students (student_name);
-- changeset aryazanov:2
CREATE INDEX faculties_sh_idx ON faculties (faculty_name, color);