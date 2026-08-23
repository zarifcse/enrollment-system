package com.example.enrollmentsystem.domain;

import java.util.List;

public class CourseRosterReport {
    private String courseCode;
    private String title;
    private String instructor;
    private Integer capacity;
    private Integer seatsFilled;
    private Integer seatsRemaining;
    private Double classAverageGrade;
    private List<String> enrolledStudentNames;

    public CourseRosterReport(String courseCode, String title, String instructor, Integer capacity, Integer seatsFilled, Integer seatsRemaining, Double classAverageGrade, List<String> enrolledStudentNames) {
        this.courseCode = courseCode;
        this.title = title;
        this.instructor = instructor;
        this.capacity = capacity;
        this.seatsFilled = seatsFilled;
        this.seatsRemaining = seatsRemaining;
        this.classAverageGrade = classAverageGrade;
        this.enrolledStudentNames = enrolledStudentNames;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public String getInstructor() {
        return instructor;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Integer getSeatsFilled() {
        return seatsFilled;
    }

    public Integer getSeatsRemaining() {
        return seatsRemaining;
    }

    public Double getClassAverageGrade() {
        return classAverageGrade;
    }

    public List<String> getEnrolledStudentNames() {
        return enrolledStudentNames;
    }
}