package com.example.studentHub.lesson.models;

import com.example.studentHub.lesson.models.Status;
import com.example.studentHub.course.models.Course;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "lessons")
public class Lesson implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "title")
    String title;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "description")
    String description;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    Status status;

    @ManyToOne
    @JoinColumn(name = "courseId", referencedColumnName = "id", nullable = false)
    private Course course;
}
