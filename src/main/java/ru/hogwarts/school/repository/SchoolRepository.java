package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SchoolRepository<T> extends JpaRepository<T, Long> {
    T findById(Long id);

    List<T> findAll();

    T deleteById(Long id);
}
