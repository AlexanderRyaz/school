package ru.hogwarts.school.repository;

import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;
@Repository
public interface StudentRepository extends SchoolRepository<Student> {
}
