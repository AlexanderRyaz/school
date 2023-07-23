ALTER TABLE students ADD CONSTRAINT age_constraint CHECK (age > 16);
ALTER TABLE students ALTER COLUMN name SET NOT NULL;
ALTER TABLE students ADD CONSTRAINT name_unique UNIQUE (student_name);
ALTER TABLE faculties ADD CONSTRAINT name_color_unique UNIQUE (faculty_name, color);
ALTER TABLE students ADD  DEFAULT 20 FOR age;