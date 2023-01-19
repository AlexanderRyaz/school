package ru.hogwarts.school.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@WebMvcTest({FacultyService.class, FacultyRepository.class})
@SpringBootTest
class FacultyServiceTest {
    @Autowired
    private FacultyService service;
    private Long id;
    @Autowired
    private FacultyRepository facultyRepository;

    @BeforeEach
    void setUp() {
        Faculty f1 = new Faculty("Gryffindor", "red");
        Faculty f2 = new Faculty("Hufflepuff", "yellow");
        Faculty f3 = new Faculty("Ravenclaw", "blue");
        Faculty faculty = service.create(f1);
        id = faculty.getId();
        service.create(f2);
        service.create(f3);
    }

    @Test
    void create() {
        Faculty faculty = new Faculty("Slytherin", "green");
        Faculty faculty1 = service.create(faculty);
        assertNotNull(faculty1.getId());
    }

    @Test
    void getById() {
        Faculty actualFaculty = service.getById(id);
        assertNotNull(actualFaculty);
        assertEquals("Gryffindor", actualFaculty.getName());
        assertEquals("red", actualFaculty.getColor());
    }

    @Test
    void getAll() {
        List<Faculty> actualFaculties = service.getAll();
        assertTrue(actualFaculties.size() >= 3);
    }

    @Test
    void update() {
        Faculty faculty = new Faculty("Gryffindor", "gold");
        faculty.setId(id);
        Faculty updatedFaculty = service.update(faculty, id);
        assertEquals("gold", updatedFaculty.getColor());
    }

    @Test
    void delete() {
        Faculty deletedFaculty = service.delete(id);
        assertEquals("Gryffindor", deletedFaculty.getName());
    }

}