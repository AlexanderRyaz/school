package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
