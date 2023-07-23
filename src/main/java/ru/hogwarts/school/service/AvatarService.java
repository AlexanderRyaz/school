package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import javax.transaction.Transactional;


@Transactional
@Service
public class AvatarService {
    private AvatarRepository repository;
    private StudentRepository studentRepository;
    private String avatarsDir;

    @Autowired
    public AvatarService(AvatarRepository repository, StudentRepository studentRepository) {
        this.repository = repository;
        this.studentRepository = studentRepository;
    }

    public Avatar findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public Page<Avatar> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
