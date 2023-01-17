package ru.hogwarts.school.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="faculties")
public class Faculty {
    @Id
   @GeneratedValue
    private Long id;
   @Column(name = "faculty_name")
    private String name;
    private String color;

    public Faculty(String name, String color) {
        this.name = name;
        this.color = color;
    }

}
