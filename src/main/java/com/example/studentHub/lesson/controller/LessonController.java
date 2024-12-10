package com.example.studentHub.lesson.controller;

import com.example.studentHub.lesson.models.Lesson;
import com.example.studentHub.lesson.models.dto.LessonCourseDto;
import com.example.studentHub.lesson.models.dto.LessonDto;
import com.example.studentHub.lesson.services.LessonService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lesson")
public class LessonController {
    @Autowired
    private LessonService lessonService;

    @PostMapping("/create")
    public ResponseEntity<?> createLesson(@RequestBody LessonDto lesson) {
        try {
            lessonService.createLesson(lesson);
            return ResponseEntity.status(HttpStatus.OK).body("Lesson Created Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getLessonsForActiveCourse(@Param("courseId") String courseId) {
        try {
            List<LessonCourseDto> existingLessons = lessonService.getLessonCourseDetails(courseId);
            return ResponseEntity.status(HttpStatus.OK).body(existingLessons);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/remove/{lessonId}")
    public ResponseEntity<?> deleteLesson(@PathVariable String lessonId) {
        try {
            lessonService.removeLesson(lessonId);
            return ResponseEntity.status(HttpStatus.OK).body("Lesson deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editLesson(@RequestBody LessonDto lesson) {
        try {
            lessonService.editLesson(lesson);
            return ResponseEntity.status(HttpStatus.OK).body("Lesson updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
