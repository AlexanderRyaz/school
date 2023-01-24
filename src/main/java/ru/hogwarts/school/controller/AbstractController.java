package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.service.AbstractService;

import java.util.List;

public class AbstractController<T> {
    private AbstractService<T> service;


    public AbstractController(AbstractService<T> service) {
        this.service = service;
    }

    @PostMapping
    public T create(T entity) {
        return service.create(entity);
    }

    @PutMapping("{id}")
    public T update(@PathVariable Long id, T entity) {
        return service.update(entity, id);
    }

    @GetMapping("{id}")
    public T getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<T> getAll() {
        return service.getAll();
    }

    @DeleteMapping("{id}")
    public T delete(@PathVariable Long id) {
        return service.delete(id);
    }
}
