package ru.hogwarts.school.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="avatars")
public class Avatar {
    @Id
    @GeneratedValue
    private Long avatarId;
    private String filePath;
    private long fileSize;
    private String mediaType;
    @Lob
    private byte[] data;
    @OneToOne
//    @MapsId
//    @JoinColumn(name="student_id")
    private Student student;
}
