package com.example.enrollmentsystem.repository;

import com.example.enrollmentsystem.domain.Student;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepository {
    private static final List<Student> students = new ArrayList<>();

    static {
        students.add(new Student(1L, "Zarif", "zarif@student.aiub.edu", "CSE", 2023));
        students.add(new Student(2L, "Rifaz", "rifaz@student.aiub.edu", "EEE", 2022));
        students.add(new Student(3L, "Salim", "salim@student.aiub.edu", "BBA", 2024));
        students.add(new Student(4L, "Zahin", "zahin@student.aiub.edu", "CSE", 2021));
        students.add(new Student(5L, "Firaz", "firaz@student.aiub.edu", "English", 2023));
    }

    public List<Student> findAll() {
        return new ArrayList<>(students);
    }

    public Optional<Student> findById(Long id) {
        for (Student s : students) {
            if (s.getId().equals(id)) {
                return Optional.of(s);
            }
        }
        return Optional.empty();
    }

    public Student save(Student student) {
        if (student.getId() == null) {
            long maxId = 0;
            for (Student s : students) {
                if (s.getId() > maxId) {
                    maxId = s.getId();
                }
            }
            student.setId(maxId + 1);
        }
        students.add(student);
        return student;
    }

    public Optional<Student> update(Long id, Student updatedStudent) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(id)) {
                updatedStudent.setId(id);
                students.set(i, updatedStudent);
                return Optional.of(updatedStudent);
            }
        }
        return Optional.empty();
    }

    public boolean deleteById(Long id) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(id)) {
                students.remove(i);
                return true;
            }
        }
        return false;
    }
}