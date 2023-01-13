package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

@Service
public class FacultyService extends AbstractService<Faculty> {
    private static Long counter = 1L;

    @Override
    public Faculty addEntityToStorage(Faculty entity) {
        entity.setId(counter++);
        return storage.put(entity.getId(), entity);
    }
}
