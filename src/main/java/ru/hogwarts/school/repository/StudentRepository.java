package ru.hogwarts.school.repository;

import org.apache.catalina.LifecycleState;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.List;

@Repository
public interface StudentRepository extends SchoolRepository<Student> {
    List<Student> findByAgeBetween(int min, int max);

    @Query(value = "SELECT COUNT (id) FROM Student")
    int getStudentCount();

    @Query(value = "select avg (age) from Student ")
    double getAverageStudentAge();

    @Query(value = "SELECT id, age, student_name, faculty_id FROM Student order by id" +
            " offset  (select count (id) from Student) - :cnt ")
    List<Student> lastStudents(@Param("cnt") int cnt);
}
