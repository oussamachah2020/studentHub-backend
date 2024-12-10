package com.example.studentHub.lesson.repository;

import com.example.studentHub.lesson.models.Lesson;
import com.example.studentHub.lesson.models.dto.LessonCourseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, String> {
    @Query("SELECT new com.example.studentHub.lesson.models.dto.LessonCourseDto(" +
            "l.id, " +
            "l.title, " +
            "l.description, " +
            "l.status, " +
            "l.course.id, " +
            "l.course.title) " +
            "FROM Lesson l")
    List<LessonCourseDto> findByCourseId(String courseId);
}
