package ru.hogwarts.school.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractService<T> {
    protected Map<Long, T> storage = new HashMap<>();

    public T create(T entity) {
        return addEntityToStorage(entity);

    }

    public T getById(Long id) {
        return storage.get(id);
    }

    public List<T> getAll() {
        return storage.values().stream().toList();
    }

    public T update(T entity, Long id) {
        storage.put(id, entity);
        return storage.get(id);
    }

    public T delete(Long id) {
        return storage.remove(id);
    }

    public abstract T addEntityToStorage(T entity);
}
