package com.example.studentHub.lesson.models.dto;

import com.example.studentHub.lesson.models.Lesson;
import com.example.studentHub.lesson.models.Status;

public class LessonCourseDto {
    private String id;
    private String title;
    private String description;
    private Status status;
    private String courseId;
    private String courseTitle;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LessonCourseDto(String id, String title, String description, Status status, String courseId, String courseTitle) {
       this.id = id;
       this.title = title;
       this.description = description;
       this.status = status;
       this.courseId = courseId;
       this.courseTitle = courseTitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String lessonTitle) {
        this.title = lessonTitle;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    // Constructor
    public LessonCourseDto(Lesson lesson) {
        this.id = lesson.getId();
        this.title = lesson.getTitle();
        this.courseId = lesson.getCourse().getId();
        this.courseTitle = lesson.getCourse().getTitle();
    }

    // Getters and setters
}