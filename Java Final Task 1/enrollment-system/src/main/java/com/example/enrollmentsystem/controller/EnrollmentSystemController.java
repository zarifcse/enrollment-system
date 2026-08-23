package com.example.enrollmentsystem.controller;

import com.example.enrollmentsystem.domain.*;
import com.example.enrollmentsystem.service.EnrollmentSystemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class EnrollmentSystemController {

    private final EnrollmentSystemService service;

    public EnrollmentSystemController(EnrollmentSystemService service) {
        this.service = service;
    }

    @PostMapping("/students")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createStudent(student));
    }

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getStudents(@RequestParam(required = false) String department) {
        return ResponseEntity.ok(service.getStudents(department));
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getStudentById(id));
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        return ResponseEntity.ok(service.updateStudent(id, student));
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        service.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/courses")
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createCourse(course));
    }

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getCourses(@RequestParam(required = false) Integer minCredit) {
        return ResponseEntity.ok(service.getCourses(minCredit));
    }

    @PostMapping("/enrollments")
    public ResponseEntity<Enrollment> enrollStudent(@RequestBody Enrollment enrollment) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.enrollStudent(enrollment));
    }

    @PatchMapping("/enrollments/{id}/grade")
    public ResponseEntity<Enrollment> updateGrade(@PathVariable Long id, @RequestBody Map<String, Double> payload) {
        Double grade = payload.get("grade");
        return ResponseEntity.ok(service.updateGrade(id, grade));
    }

    @GetMapping("/reports/students/{id}/transcript")
    public ResponseEntity<TranscriptReport> getTranscript(@PathVariable Long id) {
        return ResponseEntity.ok(service.getTranscript(id));
    }

    @GetMapping("/reports/courses/{id}/roster")
    public ResponseEntity<CourseRosterReport> getCourseRoster(@PathVariable Long id) {
        return ResponseEntity.ok(service.getCourseRoster(id));
    }

    @GetMapping("/reports/departments/summary")
    public ResponseEntity<List<DepartmentSummaryReport>> getDepartmentSummary() {
        return ResponseEntity.ok(service.getDepartmentSummary());
    }

    @GetMapping("/reports/top-performers")
    public ResponseEntity<List<TopPerformerReport>> getTopPerformers(@RequestParam(defaultValue = "5") int limit) {
        return ResponseEntity.ok(service.getTopPerformers(limit));
    }
}