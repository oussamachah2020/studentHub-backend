package com.example.studentHub.classroom.models.dto;

public class ClassRoomDTO {
    public String id;
    public String name;
    public String teacherId;

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public ClassRoomDTO(String id, String name, String teacherId) {
        this.id = id;
        this.name = name;
        this.teacherId = teacherId;
    }
}
