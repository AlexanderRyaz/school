package ru.hogwarts.school.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name="students")
public class Student {
   @Id
   @GeneratedValue
    private Long id;
   @Column(name = "student_name")
   private String name;
    private int age;
    @ManyToOne
    @JoinColumn(name="faculty_id", nullable=false)
    private Faculty faculty;
    @OneToOne(mappedBy = "student")
    @PrimaryKeyJoinColumn
    private Avatar avatar;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

}
