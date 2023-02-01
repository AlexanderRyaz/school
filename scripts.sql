select *
from public.students
where age > 43
  and students.age < 45;
select student_name
from students;
select *
from students
where student_name like '%W%';
select *
from students
where age < students.id;
select *from students order by age;