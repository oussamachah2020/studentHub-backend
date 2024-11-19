package com.example.studentHub.classroom.services;

import com.example.studentHub.auth.models.User;
import com.example.studentHub.auth.repositories.UserRepository;
import com.example.studentHub.classroom.Repository.ClassRoomRepository;
import com.example.studentHub.classroom.exceptions.ClassRoomException;
import com.example.studentHub.classroom.models.ClassRoom;
import com.example.studentHub.classroom.models.dto.ClassRoomDTO;
import com.example.studentHub.classroom.models.dto.UpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassRoomService {
    @Autowired
    private ClassRoomRepository classRepository;

    @Autowired
    private UserRepository teacherRepository;

    public void createClassroom(ClassRoomDTO req) {
        if (req == null) {
            throw new IllegalArgumentException("ClassRoom Data is required");
        }

        // Check if the classroom already exists
        ClassRoom existingClassroom = classRepository.findByName(req.getName());
        if (existingClassroom != null) {
            throw new ClassRoomException("ClassRoom with this name already exists");
        }

        // Check if the teacher exists
        User teacher = teacherRepository.findById(req.getTeacherId())
                .orElseThrow(() -> new IllegalArgumentException("Teacher does not exist"));

        // Create and save the new classroom
        ClassRoom classRoom = new ClassRoom();
        classRoom.setName(req.getName());
        classRoom.setTeacher(teacher);

        classRepository.save(classRoom);
    }

    public void removeClassRoom(String id) {
        if(id == null) {
            throw new IllegalArgumentException("id required");
        }

        classRepository.deleteById(id);
    }

    public void editClassRoom(String id, UpdateDto updateDto) {
        // Validate the input ID
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Classroom ID is required and cannot be empty");
        }

        // Validate the new name
        if (updateDto == null || updateDto.getNewName() == null || updateDto.getNewName().trim().isEmpty()) {
            throw new IllegalArgumentException("New classroom name is required and cannot be empty");
        }

        // Find the existing classroom by ID
        ClassRoom existingClassRoom = classRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Classroom with the given ID does not exist"));

        // Update the name
        existingClassRoom.setName(updateDto.getNewName().trim());

        // Save the updated classroom
        classRepository.save(existingClassRoom);
    }


    public List<ClassRoomDTO> getClassRoomsForTeacher(String teacherId) {
        return classRepository.findByTeacherId(teacherId);
    }
}
