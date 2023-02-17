package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    Logger logger = LoggerFactory.getLogger(AvatarService.class);

    @Autowired
    public AvatarService(AvatarRepository repository, StudentRepository studentRepository) {
        this.repository = repository;
        this.studentRepository = studentRepository;
    }

    public Avatar findById(Long id) {
        logger.info("Was invoked method for findById");
        return repository.findById(id).orElseThrow();
    }

    public Page<Avatar> findAll(Pageable pageable) {
        logger.info("Was invoked method for findAll");
        return repository.findAll(pageable);
    }
}
