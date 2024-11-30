package com.example.studentHub.course.models.dto;

public class CourseDto {
    public String id;
    public String title;
    public String description;
    public String coverUrl;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String classRoomId;

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

    public String getClassRoomId() {
        return classRoomId;
    }

    public void setClassRoomId(String classRoomId) {
        this.classRoomId = classRoomId;
    }


    public CourseDto(String id, String title, String description, String coverUrl, String classRoomId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.coverUrl = coverUrl;
        this.classRoomId = classRoomId;
    }
}
