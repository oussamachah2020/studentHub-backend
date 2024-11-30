package com.example.studentHub.classroom.models;

import com.example.studentHub.auth.models.User;
import com.example.studentHub.course.models.Course;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "classroom")
public class ClassRoom implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "name")
    String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    @ManyToOne
    @JoinColumn(name = "teacherId", referencedColumnName = "id", nullable = false)
    private User teacher;

    @OneToMany
    @JoinColumn(name = "courseId", referencedColumnName = "id")
    private List<Course> courses;
}
