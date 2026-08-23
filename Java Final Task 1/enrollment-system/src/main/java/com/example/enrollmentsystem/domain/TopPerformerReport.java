package com.example.enrollmentsystem.domain;

public class TopPerformerReport {
    private String name;
    private String department;
    private Double cgpa;
    private Integer creditsCompleted;
    private Integer coursesPassed;

    public TopPerformerReport(String name, String department, Double cgpa, Integer creditsCompleted, Integer coursesPassed) {
        this.name = name;
        this.department = department;
        this.cgpa = cgpa;
        this.creditsCompleted = creditsCompleted;
        this.coursesPassed = coursesPassed;
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

    public Integer getCreditsCompleted() {
        return creditsCompleted;
    }

    public Integer getCoursesPassed() {
        return coursesPassed;
    }
}