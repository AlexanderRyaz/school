package ru.hogwarts.school.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Faculty {
    private Long id;
    private String name;
    private String color;

    public Faculty(Long id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }
}
