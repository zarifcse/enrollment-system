package com.example.enrollmentsystem.repository;

import com.example.enrollmentsystem.domain.Enrollment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EnrollmentRepository {
    private static final List<Enrollment> enrollments = new ArrayList<>();

    static {
        enrollments.add(new Enrollment(1L, 1L, 1L, "Spring 25-26", 3.8));
        enrollments.add(new Enrollment(2L, 1L, 2L, "Summer 25-26", 3.5));
        enrollments.add(new Enrollment(3L, 2L, 1L, "Spring 25-26", 3.0));
        enrollments.add(new Enrollment(4L, 2L, 3L, "Spring 25-26", 2.5));
        enrollments.add(new Enrollment(5L, 3L, 2L, "Summer 25-26", 4.0));
        enrollments.add(new Enrollment(6L, 3L, 4L, "Summer 25-26", 3.7));
        enrollments.add(new Enrollment(7L, 4L, 3L, "Spring 25-26", 2.8));
        enrollments.add(new Enrollment(8L, 5L, 1L, "Fall 26-27", null));
    }

    public List<Enrollment> findAll() {
        return new ArrayList<>(enrollments);
    }

    public Optional<Enrollment> findById(Long id) {
        for (Enrollment e : enrollments) {
            if (e.getId().equals(id)) {
                return Optional.of(e);
            }
        }
        return Optional.empty();
    }

    public Enrollment save(Enrollment enrollment) {
        if (enrollment.getId() == null) {
            long maxId = 0;
            for (Enrollment e : enrollments) {
                if (e.getId() > maxId) {
                    maxId = e.getId();
                }
            }
            enrollment.setId(maxId + 1);
        }
        enrollments.add(enrollment);
        return enrollment;
    }

    public Optional<Enrollment> update(Long id, Enrollment updatedEnrollment) {
        for (int i = 0; i < enrollments.size(); i++) {
            if (enrollments.get(i).getId().equals(id)) {
                updatedEnrollment.setId(id);
                enrollments.set(i, updatedEnrollment);
                return Optional.of(updatedEnrollment);
            }
        }
        return Optional.empty();
    }
}