package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class StudentService extends AbstractService<Student> {
    private StudentRepository repository;
    private AvatarRepository avatarRepository;
    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    @Autowired
    public StudentService(StudentRepository repository, AvatarRepository avatarRepository) {
        super(repository);
        this.repository = repository;
        this.avatarRepository = avatarRepository;
    }

    @Override
    public Student updateEntity(Student entity, Long id) {
        entity.setId(id);
        return entity;
    }

    public List<Student> findStudentByAgeBetween(int min, int max) {
        return repository.findByAgeBetween(min, max);
    }

    public Faculty getFaculty(Long id) {
        Student byId = getById(id);
        return byId.getFaculty();
    }

    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        if (file.getSize() > 1024 * 300) {
            throw new RuntimeException("размер файла превышен");
        }
        Optional<Student> optionalStudent = repository.findById(studentId);
        if (optionalStudent.isPresent()) {
            Path filePath = Path.of(avatarsDir, optionalStudent.get().getId() +
                    "." + getExtension(file.getOriginalFilename()));
            Files.createDirectories(filePath.getParent());
            Files.deleteIfExists(filePath);
            try (
                    InputStream is = file.getInputStream();
                    OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                    BufferedInputStream bis = new BufferedInputStream(is, 1024);
                    BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
            ) {
                bis.transferTo(bos);
            }
            Avatar avatar = avatarRepository.findByStudentId(studentId).orElseGet(Avatar::new);
            avatar.setStudent(optionalStudent.get());
            avatar.setFilePath(filePath.toString());
            avatar.setFileSize(file.getSize());
            avatar.setMediaType(file.getContentType());
            avatar.setData(file.getBytes());

            avatarRepository.save(avatar);
        }
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public Avatar findAvatar(Long id) {
        return avatarRepository.findByStudentId(id).orElseThrow();
    }

    public List<Student> studentsByAge(int age) {
        return repository.findAll().stream().filter(student -> student.getAge() == age).toList();
    }

    public int getStudentCount() {
        return repository.getStudentCount();
    }

    public double getAverageStudentAge() {
        return repository.getAverageStudentAge();
    }

    public List<Student> lastStudents(int count) {
        return repository.lastStudents(count);
    }
}









