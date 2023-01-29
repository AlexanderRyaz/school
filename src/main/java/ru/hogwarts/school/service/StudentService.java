package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.SchoolRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

@Service
public class StudentService extends AbstractService<Student> {
    private StudentRepository repository;

    @Autowired
    public StudentService(StudentRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Student updateEntity(Student entity, Long id) {
        entity.setId(id);
        return entity;
    }

    public List<Student> findStudentByAgeBetween(int min, int max) {
        return repository.findByAgeBetween(min, max);
    }

    public Faculty getFaculty(Long id) {
        Student byId = getById(id);
        return byId.getFaculty();
    }
}









