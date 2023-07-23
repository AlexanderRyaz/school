package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AbstractService;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("faculty")
public class FacultyController extends AbstractController<Faculty> {
    @Autowired
    public FacultyController(AbstractService<Faculty> service) {
        super(service);
    }

    @GetMapping("bycolor")
    public List<Faculty> facultiesByColor(@RequestParam(value = "color", required = false) String color) {
        return service.getAll().stream()
                .filter(faculty -> faculty.getColor() != null)
                .filter(faculty -> faculty.getColor().equals(color)).toList();
    }

    @GetMapping("bycolororname")
    public ResponseEntity<List<Faculty>> findByColorOrName(@RequestParam(value = "color", required = false) String color,
                                                           @RequestParam(value = "name", required = false) String name) {
        List<Faculty> faculties = ((FacultyService) service).findByColorOrName(color, name);
        return new ResponseEntity<>(faculties, HttpStatus.OK);
    }

    @GetMapping("{id}/students")
    public ResponseEntity<List<Student>> getStudents(@PathVariable Long id) {
        List<Student> students = ((FacultyService) service).getStudents(id);
        return new ResponseEntity<>(students,HttpStatus.OK);
    }
}
