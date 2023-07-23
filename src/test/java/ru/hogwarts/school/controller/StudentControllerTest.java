package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class StudentControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @MockBean
    private StudentService studentService;
    @MockBean
    private AvatarService avatarService;
    @LocalServerPort
    private int port;
    private final String STUDENT_MAPPING = "/student";

    @Test
    void studentsByAge() {
        when(studentService.studentsByAge(anyInt())).thenReturn(studentList());
        ResponseEntity<List> forEntity = testRestTemplate
                .getForEntity("http://localhost:" + port + STUDENT_MAPPING + "/byage?age=15", List.class);
        assertEquals(HttpStatus.OK, forEntity.getStatusCode());
        assertEquals(2, forEntity.getBody().size());
    }

    @Test
    void findStudentByAgeBetween() {
        when(studentService.findStudentByAgeBetween(anyInt(), anyInt())).thenReturn(studentList());
        ResponseEntity<List> forEntity = testRestTemplate
                .getForEntity(STUDENT_MAPPING + "/byagebetween?min=14&max=18", List.class);
        assertEquals(HttpStatus.OK, forEntity.getStatusCode());
        assertEquals(2, forEntity.getBody().size());
    }

    @Test
    void getFaculty() {
        when(studentService.getFaculty(anyLong())).thenReturn(studentById());
        ResponseEntity<Faculty> forEntity = testRestTemplate
                .getForEntity("http://localhost:" + port + STUDENT_MAPPING + "/3/faculty", Faculty.class);
        assertEquals(HttpStatus.OK, forEntity.getStatusCode());
        Faculty body = forEntity.getBody();
        assertNotNull(body);
        assertEquals(2L, body.getId());
        assertEquals("f1", body.getName());
        assertEquals("green", body.getColor());
    }


    void uploadAvatar() throws IOException {
        doNothing().when(studentService).uploadAvatar(anyLong(), any());
        MockMultipartFile file
                = new MockMultipartFile(
                "avatar",
                "avatar.png",
                MediaType.IMAGE_PNG_VALUE,
                "Hello, World!".getBytes()
        );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MockMultipartFile> request = new HttpEntity<>(file, headers);
        ResponseEntity<String> forEntity = testRestTemplate
                .postForEntity("http://localhost:" + port + STUDENT_MAPPING + "/3/avatar", request, String.class);
        assertEquals(HttpStatus.OK, forEntity.getStatusCode());

    }

    @Test
    void downloadAvatar() {
        when(avatarService.findById(anyLong())).thenReturn(createAvatar());
        ResponseEntity<String> entity = testRestTemplate.getForEntity("http://localhost:" + port + STUDENT_MAPPING
                + "/3/avatar/preview", String.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertEquals("image/png", entity.getHeaders().getContentType().toString());
        assertEquals(5, entity.getHeaders().getContentLength());

    }


    void testDownloadAvatar() {
        when(studentService.findAvatar(anyLong())).thenReturn(createAvatar());
        ResponseEntity<Void> entity = testRestTemplate.getForEntity("http://localhost:" + port + STUDENT_MAPPING
                + "/3/avatar", Void.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertEquals("image/png", entity.getHeaders().getContentType().toString());
        assertEquals(5, entity.getHeaders().getContentLength());
    }

    private List<Student> studentList() {
        List<Student> students = new ArrayList<>();

        Student student = new Student("s1", 15);
        Student student4 = new Student("s4", 15);
        students.add(student);
        students.add(student4);
        return students;
    }

    private Faculty studentById() {
        Faculty faculty = new Faculty("f1", "green");
        faculty.setId(2L);
        return faculty;
    }

    private Avatar createAvatar() {
        Avatar avatar = new Avatar();
        avatar.setFilePath("path/to/avatar");
        avatar.setFileSize(600);
        avatar.setMediaType("image/png");
        avatar.setData(new byte[]{1, 2, 3, 4, 5});
        return avatar;
    }

}