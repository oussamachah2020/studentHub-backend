package com.example.studentHub.classroom.Repository;

import com.example.studentHub.classroom.models.ClassRoom;
import com.example.studentHub.classroom.models.dto.ClassRoomDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRoomRepository extends JpaRepository<ClassRoom, String> {
    List<ClassRoomDTO> findByTeacherId(String teacherId);
    ClassRoom findByName(String name);
}
