package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.SchoolRepository;

@Service
public class FacultyService extends AbstractService<Faculty> {
    @Autowired
    public FacultyService(FacultyRepository repository) {
        super(repository);
    }

    @Override
    public Faculty updateEntity(Faculty entity, Long id) {
        entity.setId(id);
        return entity;
    }

}
