package com.example.studentHub.session.models;

import com.example.studentHub.auth.models.User;
import com.example.studentHub.classroom.models.ClassRoom;
import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "sessions")
public class Session implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "title")
    String title;

    @OneToMany
    @JoinColumn(name = "studentId", referencedColumnName = "id")
    private List<User> students;

    @ManyToOne
    @JoinColumn(name = "teacherId", referencedColumnName = "id", nullable = false)
    private User teacher;
}
