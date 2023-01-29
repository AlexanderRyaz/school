package ru.hogwarts.school.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.SchoolRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentServiceTest {
    @Autowired
    private StudentService service;
    private Long id;
    @Autowired
    private SchoolRepository<Student> studentRepository;

    @BeforeEach
    void setUp() {
        Student s1 = new Student("Harry Potter", 43);
        Student s2 = new Student("Hermione Granger", 44);
        Student s3 = new Student("Ronald Weasley", 43);
        Student student = service.create(s1);
        id = student.getId();
        service.create(s2);
        service.create(s3);
    }

    @Test
    void create() {
        Student student = new Student("Neville Longbottom", 43);
        Student student1 = service.create(student);
        assertNotNull(student1.getId());
    }

    @Test
    void getById() {
        Student actualStudent = service.getById(id);
        assertNotNull(actualStudent);
        assertEquals("Harry Potter", actualStudent.getName());
        assertEquals(43, actualStudent.getAge());
    }

    @Test
    void getAll() {
        List<Student> actualStudents = service.getAll();
        assertTrue(actualStudents.size() >= 3);
    }

    @Test
    void update() {
        Student student = new Student("Harry Potter", 45);
        student.setId(id);
        Student updatedStudent = service.update(student, id);
        assertEquals(45, updatedStudent.getAge());
    }

    @Test
    void delete() {
        Student deletedStudent = service.delete(id);
        assertEquals("Harry Potter", deletedStudent.getName());
    }

}