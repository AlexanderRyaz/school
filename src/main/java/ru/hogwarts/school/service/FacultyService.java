package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.SchoolRepository;

import java.util.Comparator;
import java.util.List;

@Service
public class FacultyService extends AbstractService<Faculty> {
    Logger logger = LoggerFactory.getLogger(FacultyService.class);
    private FacultyRepository repository;

    @Autowired
    public FacultyService(FacultyRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Faculty updateEntity(Faculty entity, Long id) {
        logger.info("Was invoked method for updateEntity");
        entity.setId(id);
        return entity;
    }

    public List<Faculty> findByColorOrName(String color, String name) {
        logger.warn("Цвет или имя может быть null");
        return repository.findByColorOrNameIgnoreCase(color, name);
    }

    public List<Student> getStudents(Long id) {
        logger.info("Was invoked method for getStudents");
        Faculty byId = getById(id);
        return byId.getStudents();

    }

    public List<Faculty> facultiesByColor(String color) {
        logger.info("Was invoked method for facultiesByColor");
        return repository.findAll().stream()
                .filter(faculty -> faculty.getColor() != null)
                .filter(faculty -> faculty.getColor().equals(color)).toList();
    }

    public String longestFacultyName() {
        return repository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length)).orElse(null);
    }
}
