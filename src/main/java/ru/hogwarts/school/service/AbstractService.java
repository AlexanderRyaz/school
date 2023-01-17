package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.webjars.NotFoundException;
import ru.hogwarts.school.repository.SchoolRepository;

import java.util.List;

public abstract class AbstractService<T> {
  @Autowired
    private SchoolRepository<T> repository;

    public T create(T entity) {
        return repository.saveAndFlush(entity);

    }

    public T getById(Long id) {
        return repository.findById(id);
    }

    public List<T> getAll() {
        return repository.findAll();
    }

    public T update(T entity, Long id) {
        T byId = repository.findById(id);
        if (byId==null){
            throw new NotFoundException("entity not found");
        }
        T updatedEntity = updateEntity(entity, id);
        return repository.saveAndFlush(updatedEntity);
    }

    public T delete(Long id) {
        T byId = repository.findById(id);
        if (byId==null){
            throw new NotFoundException("entity not found");
        }
        return repository.deleteById(id);
    }

    public abstract T updateEntity(T entity, Long id);
}
