package ru.hogwarts.school.repository;

import org.apache.catalina.LifecycleState;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.List;

@Repository
public interface StudentRepository extends SchoolRepository<Student> {
    List<Student> findByAgeBetween(int min, int max);
}
