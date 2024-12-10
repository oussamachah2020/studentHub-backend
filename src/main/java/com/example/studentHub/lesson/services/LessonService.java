package com.example.studentHub.lesson.services;

import com.example.studentHub.course.models.Course;
import com.example.studentHub.course.repository.CourseRepository;
import com.example.studentHub.lesson.models.Lesson;
import com.example.studentHub.lesson.models.dto.LessonCourseDto;
import com.example.studentHub.lesson.models.dto.LessonDto;
import com.example.studentHub.lesson.repository.LessonRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LessonService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LessonRepository lessonRepository;


    @Autowired
    private ModelMapper modelMapper;

    public void createLesson(LessonDto lesson) {
        if(lesson == null) {
            throw new IllegalArgumentException("Missing Lesson Data");
        }

        Course existingCourse = courseRepository.findById(lesson.getCourseId()).orElseThrow(() -> new IllegalArgumentException("No Course found with this id"));

        Lesson newLesson = new Lesson();

        newLesson.setTitle(lesson.getTitle());
        newLesson.setDescription(lesson.getDescription());
        newLesson.setStatus(lesson.getStatus());
        newLesson.setCourse(existingCourse);

        lessonRepository.save(newLesson);
    }

    public void editLesson(LessonDto req) {
        if(req == null) {
            throw new IllegalArgumentException("Missing lesson data");
        }

        Lesson existingLesson = lessonRepository.findById(req.getId()).orElseThrow(() -> new IllegalArgumentException("No Lesson found with this id"));

        existingLesson.setTitle(req.getTitle());
        existingLesson.setDescription(req.getDescription());
        existingLesson.setStatus(req.getStatus());

        lessonRepository.save(existingLesson);
    }

    public void removeLesson(String lessonId) {
        if(lessonId == null) {
            throw new IllegalArgumentException("Lesson ID is required");
        }

        lessonRepository.deleteById(lessonId);
    }

    public List<LessonCourseDto> getLessonCourseDetails(String courseId) {
        List<LessonCourseDto> rawData = lessonRepository.findByCourseId(courseId);
        return rawData.stream()
                .map(map -> modelMapper.map(map, LessonCourseDto.class))
                .collect(Collectors.toList());
    }
}
