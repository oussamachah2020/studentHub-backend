package com.example.studentHub.lesson.models.dto;

import com.example.studentHub.course.models.Course;
import com.example.studentHub.course.models.dto.CourseDto;
import com.example.studentHub.lesson.models.Status;

public class LessonDto {
    public String id;
    public String title;
    public String description;
    public String courseTitle;

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LessonDto(String id, String title, String description, Status status, String courseId, String courseTitle) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.courseId = courseId;
        this.courseTitle = courseTitle;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public Status status;
    public String courseId;
}
