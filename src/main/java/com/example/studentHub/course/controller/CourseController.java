package com.example.studentHub.course.controller;

import com.example.studentHub.classroom.models.ClassRoom;
import com.example.studentHub.classroom.models.dto.ClassRoomDTO;
import com.example.studentHub.course.models.Course;
import com.example.studentHub.course.models.dto.CourseDto;
import com.example.studentHub.course.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/course")
public class CourseController {
    @Autowired
    public CourseService courseService;

    @PostMapping("/new")
    public String addCourse(@RequestBody CourseDto req) {
        return courseService.createCourse(req);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<CourseDto>> getCoursesForClass(@PathVariable String id) {
        List<CourseDto> courses = courseService.getCoursesByClassRoomId(id);

        return ResponseEntity.ok(courses);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<String> deleteCourse(@PathVariable String courseId) {
        String message = courseService.removeCourse(courseId);

        return ResponseEntity.ok(message);
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editCourseInfo(@RequestBody CourseDto course) {
        try {
            courseService.editCourse(course);
            return ResponseEntity.ok("Course updated successfully");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + ex.getMessage());
        }
    }

}
