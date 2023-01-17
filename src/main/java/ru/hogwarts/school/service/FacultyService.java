package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

@Service
public class FacultyService extends AbstractService<Faculty> {

    @Override
    public Faculty updateEntity(Faculty entity, Long id) {
        entity.setId(id);
        return entity;
    }

    public void clearStorage() {
        //  storage.clear();
    }
}
