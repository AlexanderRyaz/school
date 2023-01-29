package ru.hogwarts.school.controller;

import ch.qos.logback.core.boolex.EvaluationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AbstractService;
import ru.hogwarts.school.service.StudentService;

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
        return service.getAll().stream().filter(student -> student.getAge() == age).toList();
    }

    @GetMapping("byagebetween")
    public ResponseEntity<List<Student>> findStudentByAgeBetween(@RequestParam(value = "min") int min,
                                                                 @RequestParam(value = "max") int max) {
        List<Student> students = ((StudentService) service).findStudentByAgeBetween(min, max);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }
@GetMapping("{id}/faculty")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty faculty = ((StudentService) service).getFaculty(id);
        return new ResponseEntity<>(faculty,HttpStatus.OK);
    }
}
