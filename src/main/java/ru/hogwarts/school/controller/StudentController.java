package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AbstractService;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController extends AbstractController<Student> {
    private AvatarService avatarService;

    @Autowired
    public StudentController(AbstractService<Student> service, AvatarService avatarService) {
        super(service);
        this.avatarService = avatarService;
    }

    @GetMapping("byage")
    public ResponseEntity<List<Student>> studentsByAge(@RequestParam(value = "age", required = false) int age) {
        List<Student> students = ((StudentService) service).studentsByAge(age);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("byagebetween")
    public ResponseEntity<List<Student>> findStudentByAgeBetween(@RequestParam(value = "min") int min,
                                                                 @RequestParam(value = "max") int max) {
        List<Student> students = ((StudentService) service).findStudentByAgeBetween(min, max);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("{id}/faculty")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty faculty = ((StudentService) service).getFaculty(id);
        return new ResponseEntity<>(faculty, HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long id,
                                               @RequestParam MultipartFile avatar) throws IOException {

        ((StudentService) service).uploadAvatar(id, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}/avatar/preview")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable Long id) {
        Avatar avatar = avatarService.findById(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    @GetMapping(value = "/{id}/avatar")
    public void downloadAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Avatar avatar = ((StudentService) service).findAvatar(id);

        Path path = Path.of(avatar.getFilePath());

        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream();) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }
    }

    @GetMapping(value = "count")
    public ResponseEntity<Integer> getStudentCount() {
        int studentCount = ((StudentService) service).getStudentCount();
        return new ResponseEntity<>(studentCount, HttpStatus.OK);
    }

    @GetMapping(value = "average/age")
    public ResponseEntity<Double> getAverageStudentAge() {
        double averageAgeStudent = ((StudentService) service).getAverageStudentAge();
        return new ResponseEntity<>(averageAgeStudent, HttpStatus.OK);
    }

    @GetMapping(value = "last")
    public ResponseEntity<List<Student>> lastStudents(@RequestParam(name = "count", required = false) int count) {
        List<Student> lastCountStudents = ((StudentService) service).lastStudents(count);
        return new ResponseEntity<>(lastCountStudents, HttpStatus.OK);
    }

    @GetMapping(value = "startsWith")
    public ResponseEntity<List<String>> startsWithA() {
        List<String> strings = ((StudentService) service).startsWithA();
        return new ResponseEntity<>(strings, HttpStatus.OK);
    }

    @GetMapping(value = "multiThreading")
    public void multiThreading() {
        List<Student> all = service.getAll();

        Thread thread2 = new Thread(() -> {
            System.out.println(all.get(2).getName());
            System.out.println(all.get(3).getName());
        });
        Thread thread3 = new Thread(() -> {
            System.out.println(all.get(4).getName());
            System.out.println(all.get(5).getName());
        });
        System.out.println(all.get(0).getName());
        System.out.println(all.get(1).getName());
        thread2.start();
        thread3.start();
    }

    @GetMapping(value = "multiThreadingSynchronized")
    public void multiThreadingSynchronized() {
        ((StudentService) service).multiThreadingSynchronized();
    }


}

