package com.example.studentHub.course.models;

import com.example.studentHub.classroom.models.ClassRoom;
import com.example.studentHub.lesson.models.Lesson;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "courses")
public class Course implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "title")
    String title;

    @Column(name = "description")
    String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    public List<Lesson> getLesson() {
        return Lesson;
    }

    public void setLesson(List<Lesson> lesson) {
        Lesson = lesson;
    }

    @Column(name = "coverUrl")
    String coverUrl;

    @ManyToOne
    @JoinColumn(name = "classId", referencedColumnName = "id", nullable = false)
    private ClassRoom classRoom;

    @OneToMany
    @JoinColumn(name = "lessonId", referencedColumnName = "id")
    private List<Lesson> Lesson;
}
