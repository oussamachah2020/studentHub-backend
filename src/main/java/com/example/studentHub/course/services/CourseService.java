package com.example.studentHub.course.services;

import com.example.studentHub.classroom.Repository.ClassRoomRepository;
import com.example.studentHub.classroom.models.ClassRoom;
import com.example.studentHub.course.models.Course;
import com.example.studentHub.course.models.dto.CourseDto;
import com.example.studentHub.course.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Autowired
    private CourseRepository courseRepository;

    public String createCourse(CourseDto req) {
        if (req == null) {
            return "Course Data is required";
        }

        if (req.getClassRoomId() == null) {
            throw new IllegalArgumentException("ClassRoom ID is required");
        }

        ClassRoom existingClassRoom = classRoomRepository.findById(req.getClassRoomId()).orElseThrow(() ->  new IllegalArgumentException("ClassRoom not found"));

        Course newCourse = new Course();

        newCourse.setTitle(req.getTitle());
        newCourse.setDescription(req.getDescription());
        newCourse.setCoverUrl(req.getCoverUrl());
        newCourse.setClassRoom(existingClassRoom);

        courseRepository.save(newCourse);

        return "Course added successfully";
    }

    public List<CourseDto> getCoursesByClassRoomId(String id) {
        return courseRepository.findByClassRoomId(id);
    }

    public String removeCourse(String courseId) {
        if(courseId == null) {
            return "Course ID is required";
        }

        courseRepository.deleteById(courseId);
        return "Course Deleted Successfully";
    }

    public void editCourse(CourseDto course) {
        if (course == null) {
            throw new IllegalArgumentException("Course data is required");
        }

        Course existingCourse = courseRepository.findById(course.getId())
                .orElseThrow(() -> new IllegalArgumentException("No course found with the provided ID"));

        existingCourse.setTitle(course.getTitle());
        existingCourse.setDescription(course.getDescription());
        existingCourse.setCoverUrl(course.getCoverUrl());

        courseRepository.save(existingCourse);
    }

}
