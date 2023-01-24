package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.webjars.NotFoundException;
import ru.hogwarts.school.repository.SchoolRepository;

import java.util.List;
import java.util.Optional;

public abstract class AbstractService<T> {

    private SchoolRepository<T> repository;

    public AbstractService(SchoolRepository<T> repository) {
        this.repository = repository;
    }

    public T create(T entity) {
        return repository.saveAndFlush(entity);

    }

    public T getById(Long id) {
        Optional<T> optionalT = repository.findById(id);
        if (optionalT.isEmpty()) {
            throw new NotFoundException("entity not found");
        }
        return optionalT.get();
    }


    public List<T> getAll() {
        return repository.findAll();
    }

    public T update(T entity, Long id) {
        Optional<T> byId = repository.findById(id);
        if (byId.isEmpty()) {
            throw new NotFoundException("entity not found");
        }
        T updatedEntity = updateEntity(entity, id);
        return repository.saveAndFlush(updatedEntity);
    }

    public T delete(Long id) {
        Optional<T> byId = repository.findById(id);
        if (byId.isEmpty()) {
            throw new NotFoundException("entity not found");
        }
        repository.deleteById(id);
        return byId.get();
    }

    public abstract T updateEntity(T entity, Long id);
}
