package com.example.enrollmentsystem.domain;

public class DepartmentSummaryReport {
    private String department;
    private Integer numberOfStudents;
    private Integer totalEnrollments;
    private Double averageCgpa;
    private String mostPopularCourse;

    public DepartmentSummaryReport(String department, Integer numberOfStudents, Integer totalEnrollments, Double averageCgpa, String mostPopularCourse) {
        this.department = department;
        this.numberOfStudents = numberOfStudents;
        this.totalEnrollments = totalEnrollments;
        this.averageCgpa = averageCgpa;
        this.mostPopularCourse = mostPopularCourse;
    }

    public String getDepartment() {
        return department;
    }

    public Integer getNumberOfStudents() {
        return numberOfStudents;
    }

    public Integer getTotalEnrollments() {
        return totalEnrollments;
    }

    public Double getAverageCgpa() {
        return averageCgpa;
    }

    public String getMostPopularCourse() {
        return mostPopularCourse;
    }
}