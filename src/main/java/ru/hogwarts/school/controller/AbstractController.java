package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.service.AbstractService;

import java.util.List;

public class AbstractController<T> {
    protected AbstractService<T> service;


    public AbstractController(AbstractService<T> service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<T> create(@RequestBody T entity) {
        T t = service.create(entity);
        return new ResponseEntity<>(t, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<T> update(@PathVariable Long id, T entity) {
        T update = service.update(entity, id);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<T> getById(@PathVariable Long id) {
        T byId = service.getById(id);
        return new ResponseEntity<>(byId, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<T>> getAll() {
        List<T> all = service.getAll();
        return new ResponseEntity<>(all,HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<T> delete(@PathVariable Long id) {
        T delete = service.delete(id);
        return new ResponseEntity<>(delete,HttpStatus.NO_CONTENT);
    }
}
