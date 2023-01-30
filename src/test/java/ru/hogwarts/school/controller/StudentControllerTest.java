package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class StudentControllerTest {
    private static final String STUDENT_MAPPING = "/student";
    @Autowired
    private TestRestTemplate testRestTemplate;
    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private StudentService studentService;
    @Autowired
    private StudentController studentController;
    @MockBean
    private AvatarRepository avatarRepository;
    @MockBean
    private AvatarService avatarService;

    @Test
    void studentsByAge() {
        when(studentService.studentsByAge(anyInt())).thenCallRealMethod();
        when(studentRepository.findAll()).thenReturn(studentList());
        ResponseEntity<List> forEntity = testRestTemplate
                .getForEntity(STUDENT_MAPPING + "/byage?age=15", List.class);
        assertEquals(HttpStatus.OK, forEntity.getStatusCode());
        assertEquals(2, forEntity.getBody().size());
    }

    @Test
    void findStudentByAgeBetween() {
        when(studentService.findStudentByAgeBetween(anyInt(), anyInt())).thenCallRealMethod();
        when(studentRepository.findByAgeBetween(anyInt(), anyInt())).thenReturn(studentList());
        ResponseEntity<List> forEntity = testRestTemplate
                .getForEntity(STUDENT_MAPPING + "/byagebetween?min=14&max=18", List.class);
        assertEquals(HttpStatus.OK, forEntity.getStatusCode());
        assertEquals(4, forEntity.getBody().size());
    }

    @Test
    void getFaculty() {
        when(studentService.getFaculty(anyLong())).thenCallRealMethod();
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(studentById()));
        ResponseEntity<Faculty> forEntity = testRestTemplate
                .getForEntity(STUDENT_MAPPING + "/3/faculty", Faculty.class);
        assertEquals(HttpStatus.OK, forEntity.getStatusCode());
        Faculty body = forEntity.getBody();
        assertNotNull(body);
        assertEquals(2L, body.getId());
        assertEquals("f1", body.getName());
        assertEquals("green", body.getColor());
    }

    @Test
    void uploadAvatar() throws IOException {
       doNothing().when(studentService).uploadAvatar(anyLong(),any());
        ResponseEntity<String> forEntity = testRestTemplate
                .getForEntity(STUDENT_MAPPING + "/3/avatar", String.class);
        assertEquals(HttpStatus.OK, forEntity.getStatusCode());
    }

    @Test
    void downloadAvatar() {
        when(avatarService.findById(anyLong())).thenCallRealMethod();
        when(avatarRepository.findByStudentId(anyLong())).thenReturn(Optional.of(createAvatar()));
//        testRestTemplate.getForEntity(STUDENT_MAPPING+"3/avatar/preview");

    }

    @Test
    void testDownloadAvatar() {
        when(avatarService.findById(anyLong())).thenCallRealMethod();
        when(avatarRepository.findByStudentId(anyLong())).thenReturn(Optional.of(createAvatar()));
        ResponseEntity<Void> entity = testRestTemplate.getForEntity(STUDENT_MAPPING + "3/avatar", Void.class);
        assertEquals(HttpStatus.OK,entity.getStatusCode());
        assertEquals("png",entity.getHeaders().getContentType().toString());
        assertEquals(5,entity.getHeaders().getContentLength());
    }

    private List<Student> studentList() {
        List<Student> students = new ArrayList<>();
        Student student = new Student("s1", 15);
        Student student2 = new Student("s2", 16);
        Student student3 = new Student("s3", 17);
        Student student4 = new Student("s4", 15);
        students.add(student);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        return students;
    }

    private Student studentById() {
        Student s1 = new Student("s1", 15);
        s1.setId(3L);
        Faculty faculty = new Faculty("f1", "green");
        s1.setId(2L);
        s1.setFaculty(faculty);
        return s1;
    }
    private Avatar createAvatar(){
        Avatar avatar = new Avatar();
        avatar.setFilePath("path/to/avatar");
        avatar.setFileSize(600);
        avatar.setMediaType("png");
        avatar.setData(new byte[]{1,2,3,4,5});
        return avatar;
    }

}