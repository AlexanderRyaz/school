package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AbstractService;

import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController extends AbstractController<Student> {
    @Autowired
    public StudentController(AbstractService<Student> service) {
        super(service);
    }

    @GetMapping("byage")
    public List<Student> studentsByAge(@RequestParam(value = "age", required = false) int age) {
        return getAll().stream().filter(student -> student.getAge() == age).toList();
    }

}
