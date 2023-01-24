package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.SchoolRepository;

import java.util.List;

@Service
public class FacultyService extends AbstractService<Faculty> {
    private FacultyRepository repository;

    @Autowired
    public FacultyService(FacultyRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Faculty updateEntity(Faculty entity, Long id) {
        entity.setId(id);
        return entity;
    }

    public List<Faculty> findByColorOrName(String color, String name) {
        return repository.findByColorOrNameIgnoreCase(color, name);
    }

    public List<Student> getStudents(Long id) {
        Faculty byId = getById(id);
        return byId.getStudents();

    }
}
