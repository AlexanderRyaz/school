package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface SchoolRepository<T> extends JpaRepository<T, Long> {
    Optional<T> findById(Long id);

    List<T> findAll();

    void deleteById(Long id);
}
