package com.example.enrollmentsystem.domain;

import java.util.List;

public class TranscriptReport {
    private Long studentId;
    private String name;
    private String department;
    private Double cgpa;
    private Integer totalCreditsEarned;
    private List<CourseRecord> courses;

    public TranscriptReport(Long studentId, String name, String department, Double cgpa, Integer totalCreditsEarned, List<CourseRecord> courses) {
        this.studentId = studentId;
        this.name = name;
        this.department = department;
        this.cgpa = cgpa;
        this.totalCreditsEarned = totalCreditsEarned;
        this.courses = courses;
    }

    public Long getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public Double getCgpa() {
        return cgpa;
    }

    public Integer getTotalCreditsEarned() {
        return totalCreditsEarned;
    }

    public List<CourseRecord> getCourses() {
        return courses;
    }

    public static class CourseRecord {
        private String courseCode;
        private String title;
        private String semester;
        private Double grade;

        public CourseRecord(String courseCode, String title, String semester, Double grade) {
            this.courseCode = courseCode;
            this.title = title;
            this.semester = semester;
            this.grade = grade;
        }

        public String getCourseCode() {
            return courseCode;
        }

        public String getTitle() {
            return title;
        }

        public String getSemester() {
            return semester;
        }

        public Double getGrade() {
            return grade;
        }
    }
}