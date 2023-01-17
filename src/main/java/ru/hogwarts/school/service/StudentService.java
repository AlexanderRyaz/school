package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

@Service
public class StudentService extends AbstractService<Student> {

    @Override
    public Student updateEntity(Student entity, Long id) {
        entity.setId(id);
        return entity;
    }
    public void clearStorage(){
       // storage.clear();
    }
}









