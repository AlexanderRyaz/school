package ru.hogwarts.school.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import ru.hogwarts.school.model.Faculty;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@WebMvcTest(FacultyService.class)
class FacultyServiceTest {
    @Autowired
    private FacultyService service;
    private Long id;

    @BeforeEach
    void setUp() {
        Faculty f1 = new Faculty("Gryffindor", "red");
        Faculty f2 = new Faculty("Hufflepuff", "yellow");
        Faculty f3 = new Faculty("Ravenclaw", "blue");
        Faculty faculty = service.addEntityToStorage(f1);
        id = faculty.getId();
        service.addEntityToStorage(f2);
        service.addEntityToStorage(f3);
    }

    @AfterEach
    void tearDown() {
        service.clearStorage();
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
        assertEquals(3, actualFaculties.size());
    }

    @Test
    void update() {
        Faculty faculty = new Faculty("Gryffindor", "gold");
        faculty.setId(id);
        Faculty updatedFaculty = service.update(faculty, id);
        assertEquals("gold",updatedFaculty.getColor());
    }

    @Test
    void delete() {
        Faculty deletedFaculty = service.delete(id);
        assertEquals("Gryffindor", deletedFaculty.getName());
    }

    @Test
    void addEntityToStorage() {
        Faculty faculty = new Faculty("Slytherin", "green");
        Faculty actualCreateFaculty = service.addEntityToStorage(faculty);
        assertNotNull(actualCreateFaculty.getId());
        service.storage.containsKey(actualCreateFaculty.getId());
    }

}