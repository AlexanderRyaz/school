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
import java.util.stream.Stream;

@RestController
@RequestMapping("faculty")
public class FacultyController extends AbstractController<Faculty> {
    @Autowired
    public FacultyController(AbstractService<Faculty> service) {
        super(service);
    }

    @GetMapping("bycolor")
    public ResponseEntity<List<Faculty>> facultiesByColor(@RequestParam(value = "color", required = false) String color) {
        List<Faculty> faculties = ((FacultyService) service).facultiesByColor(color);
        return new ResponseEntity<>(faculties, HttpStatus.OK);
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
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("longestFacultyName")
    public ResponseEntity<String> longestFacultyName() {
        String longestFacultyName = ((FacultyService) service).longestFacultyName();
        return new ResponseEntity<>(longestFacultyName, HttpStatus.OK);
    }

    @GetMapping("calculateInt")
    public ResponseEntity<Long> calculateInt() {
        long startTime = System.nanoTime();
        int sum = Stream.iterate(1, a -> a + 1).parallel().limit(1_000_000).reduce(0, (a, b) -> a + b);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        return new ResponseEntity<>(duration, HttpStatus.OK);
    }
}
