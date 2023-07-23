package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class FacultyControllerTest {
    @Autowired
    MockMvc        mockMvc;
    @MockBean
    FacultyService facultyService;
    private static final String FACULTY_MAPPING = "/faculty";

    @Test
    void facultiesByColor() throws Exception {
        when(facultyService.facultiesByColor(any())).thenReturn(facultyList());
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get(FACULTY_MAPPING + "/bycolor")
                                .param("color", "red")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("f1", "f4")))
                .andExpect(jsonPath("$[*].color", containsInAnyOrder("red", "red")));
    }

    @Test
    void findByColorOrName() throws Exception {
        when(facultyService.findByColorOrName(anyString(), anyString())).thenReturn(facultyListByColorOrName());
        mockMvc.perform(
                        MockMvcRequestBuilders.get(FACULTY_MAPPING + "/bycolororname")
                                .param("color", "black")
                                .param("name", "f3")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("f2", "f3")))
                .andExpect(jsonPath("$[*].color", containsInAnyOrder("green", "black")));
    }

    @Test
    void getStudents() throws Exception {
        when(facultyService.getStudents(anyLong())).thenReturn(studentsByFacultyId());
        mockMvc.perform(
                        MockMvcRequestBuilders.get(FACULTY_MAPPING + "/" + 2 + "/students")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("s1", "s2")))
                .andExpect(jsonPath("$[*].age", containsInAnyOrder(20, 18)));
    }

    @Test
    void create() throws Exception {
        Faculty faculty = new Faculty("s1", "red");
        faculty.setId(3L);
        when(facultyService.create(any())).thenReturn(faculty);
        mockMvc.perform(MockMvcRequestBuilders
                        .post(FACULTY_MAPPING)
                        .content(new ObjectMapper().writeValueAsString(faculty))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.name").value("s1"))
                .andExpect(jsonPath("$.color").value("red"));
    }

    @Test
    void update() throws Exception {
        Faculty faculty = new Faculty("s1", "red");
        faculty.setId(3L);
        when(facultyService.update(any(), anyLong())).thenReturn(faculty);
        mockMvc.perform(MockMvcRequestBuilders
                        .put(FACULTY_MAPPING + "/" + 3)
                        .content(new ObjectMapper().writeValueAsString(faculty))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.name").value("s1"))
                .andExpect(jsonPath("$.color").value("red"));
    }

    @Test
    void getById() throws Exception {
        Faculty faculty = new Faculty("s1", "red");
        faculty.setId(3L);
        when(facultyService.getById(anyLong())).thenReturn(faculty);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(FACULTY_MAPPING + "/" + 3)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.name").value("s1"))
                .andExpect(jsonPath("$.color").value("red"));
    }

    @Test
    void getAll() throws Exception {
        when(facultyService.getAll()).thenReturn(facultyList());
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get(FACULTY_MAPPING)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("f1", "f4")))
                .andExpect(jsonPath("$[*].color", containsInAnyOrder("red", "red")));
    }

    @Test
    void delete() throws Exception {
        Faculty faculty = new Faculty("s1", "red");
        faculty.setId(3L);
        when(facultyService.delete(anyLong())).thenReturn(faculty);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(FACULTY_MAPPING + "/" + 3)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.name").value("s1"))
                .andExpect(jsonPath("$.color").value("red"));
    }

    private List<Faculty> facultyList() {
        List<Faculty> faculties = new ArrayList<>();
        Faculty       faculty1  = new Faculty("f1", "red");
        Faculty       faculty4  = new Faculty("f4", "red");
        faculties.add(faculty1);
        faculties.add(faculty4);
        return faculties;

    }

    private List<Faculty> facultyListByColorOrName() {
        List<Faculty> faculties = new ArrayList<>();
        Faculty       faculty2  = new Faculty("f2", "black");
        Faculty       faculty3  = new Faculty("f3", "green");
        faculties.add(faculty2);
        faculties.add(faculty3);
        return faculties;
    }

    private List<Student> studentsByFacultyId() {
        List<Student> students = new ArrayList<>();
        students.add(new Student("s1", 20));
        students.add(new Student("s2", 18));
        return students;
    }
}