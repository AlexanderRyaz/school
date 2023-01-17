package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.AbstractService;

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
        return getAll().stream()
                .filter(faculty -> faculty.getColor() != null)
                .filter(faculty -> faculty.getColor().equals(color)).toList();
    }
}
