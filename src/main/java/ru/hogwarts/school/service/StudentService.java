package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

@Service
public class StudentService extends AbstractService<Student> {
    private static Long counter = 1L;


    @Override
    public Student addEntityToStorage(Student entity) {
        entity.setId(counter++);
        storage.put(entity.getId(), entity);
        return entity;
    }
    public void clearStorage(){
        storage.clear();
    }
}









