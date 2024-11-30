package com.example.studentHub.course.repository;

import com.example.studentHub.course.models.Course;
import com.example.studentHub.course.models.dto.CourseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    List<CourseDto> findByClassRoomId(String id);
}
