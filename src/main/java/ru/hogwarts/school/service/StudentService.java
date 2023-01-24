package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.SchoolRepository;
import ru.hogwarts.school.repository.StudentRepository;

@Service
public class StudentService extends AbstractService<Student> {
    @Autowired
    public StudentService(StudentRepository repository) {
        super(repository);
    }

    @Override
    public Student updateEntity(Student entity, Long id) {
        entity.setId(id);
        return entity;
    }

}









