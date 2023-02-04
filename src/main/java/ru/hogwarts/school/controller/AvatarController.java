package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping("avatar")
public class AvatarController {
    private AvatarService service;

    @Autowired
    public AvatarController(AvatarService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Avatar>> getAll(@PageableDefault(value = 10, page = 0) Pageable pageable) {
        Page<Avatar> avatars = service.findAll(pageable);
        return new ResponseEntity<>(avatars.getContent(), HttpStatus.OK);
    }
}
