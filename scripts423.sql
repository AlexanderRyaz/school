select student_name, age, faculty_name from students left join faculties on faculty_id = faculties.id;
select student_name, age from students where avatar is not null;