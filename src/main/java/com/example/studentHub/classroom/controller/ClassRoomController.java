package com.example.studentHub.classroom.controller;


import com.example.studentHub.auth.services.JwtService;
import com.example.studentHub.classroom.exceptions.ClassRoomException;
import com.example.studentHub.classroom.models.dto.ClassRoomDTO;
import com.example.studentHub.classroom.models.dto.UpdateDto;
import com.example.studentHub.classroom.services.ClassRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/classroom")
public class ClassRoomController {
    @Autowired
    private ClassRoomService classRoomService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/create")
    public ResponseEntity<?> createClassRoom(@RequestBody ClassRoomDTO req) {
        try {
            classRoomService.createClassroom(req);
            return ResponseEntity.status(HttpStatus.OK).body("Class Created Successfully");
        } catch (ClassRoomException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Class with this name already exists");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> deleteClassRoom(@PathVariable String id) {
        try {
            classRoomService.removeClassRoom(id);
            return ResponseEntity.status(HttpStatus.OK).body("Class removed successfully");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editClassRoom(@PathVariable String id, @RequestBody UpdateDto updateDto) {
        try {
            classRoomService.editClassRoom(id, updateDto);
            return ResponseEntity.status(HttpStatus.OK).body("Class name updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/")
    public HttpEntity<Object> getTeacherClasses(@RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken) {
        String token = accessToken.replace("Bearer ", "");

        String id = jwtService.extractUserId(token);

        if(id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token or Id not found");
        }

        List<ClassRoomDTO> existingClassRoom = classRoomService.getClassRoomsForTeacher(id);

        return ResponseEntity.status(HttpStatus.OK).body(existingClassRoom);
    }
}
