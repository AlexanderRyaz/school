package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Faculty;

import java.util.List;

@Repository
public interface FacultyRepository extends SchoolRepository<Faculty> {
    List<Faculty> findByColorOrNameIgnoreCase(String color, String name);
}
