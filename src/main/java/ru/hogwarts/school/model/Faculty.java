package ru.hogwarts.school.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


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
    @OneToMany(mappedBy = "faculty")
    private List<Student>students;

    public Faculty(String name, String color) {
        this.name = name;
        this.color = color;
    }

}
