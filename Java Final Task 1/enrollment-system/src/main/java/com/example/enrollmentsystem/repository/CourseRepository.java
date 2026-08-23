package com.example.enrollmentsystem.repository;

import com.example.enrollmentsystem.domain.Course;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CourseRepository {
    private static final List<Course> courses = new ArrayList<>();

    static {
        courses.add(new Course(1L, "CSC3125", "Advance Programming with Java", 3, "FIROZ", 40));
        courses.add(new Course(2L, "CSC2209", "Computer Organization and Architecture", 3, "Jashim", 35));
        courses.add(new Course(3L, "CSC3217", "Software Engineering", 3, "Kawsur", 50));
        courses.add(new Course(4L, "MAT1102", "Differential Calculus", 3, "Mostofa", 30));
    }

    public List<Course> findAll() {
        return new ArrayList<>(courses);
    }

    public Optional<Course> findById(Long id) {
        for (Course c : courses) {
            if (c.getId().equals(id)) {
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }

    public Course save(Course course) {
        if (course.getId() == null) {
            long maxId = 0;
            for (Course c : courses) {
                if (c.getId() > maxId) {
                    maxId = c.getId();
                }
            }
            course.setId(maxId + 1);
        }
        courses.add(course);
        return course;
    }
}