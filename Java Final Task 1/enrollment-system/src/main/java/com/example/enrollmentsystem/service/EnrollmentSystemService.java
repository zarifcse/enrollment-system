package com.example.enrollmentsystem.service;

import com.example.enrollmentsystem.domain.*;
import com.example.enrollmentsystem.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentSystemService {

    private final StudentRepository studentRepo;
    private final CourseRepository courseRepo;
    private final EnrollmentRepository enrollmentRepo;

    public EnrollmentSystemService(StudentRepository studentRepo, CourseRepository courseRepo, EnrollmentRepository enrollmentRepo) {
        this.studentRepo = studentRepo;
        this.courseRepo = courseRepo;
        this.enrollmentRepo = enrollmentRepo;
    }

    public Student createStudent(Student student) {
        for (Student s : studentRepo.findAll()) {
            if (s.getEmail().equalsIgnoreCase(student.getEmail())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT);
            }
        }
        return studentRepo.save(student);
    }

    public List<Student> getStudents(String department) {
        List<Student> allStudents = studentRepo.findAll();
        if (department == null || department.isEmpty()) {
            return allStudents;
        }

        List<Student> filteredList = new ArrayList<>();
        for (Student s : allStudents) {
            if (s.getDepartment().equalsIgnoreCase(department)) {
                filteredList.add(s);
            }
        }
        return filteredList;
    }

    public Student getStudentById(Long id) {
        Optional<Student> student = studentRepo.findById(id);
        if (student.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return student.get();
    }

    public Student updateStudent(Long id, Student student) {
        Optional<Student> updated = studentRepo.update(id, student);
        if (updated.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return updated.get();
    }

    public void deleteStudent(Long id) {
        for (Enrollment e : enrollmentRepo.findAll()) {
            if (e.getStudentId().equals(id)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT);
            }
        }
        boolean deleted = studentRepo.deleteById(id);
        if (!deleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public Course createCourse(Course course) {
        for (Course c : courseRepo.findAll()) {
            if (c.getCode().equalsIgnoreCase(course.getCode())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT);
            }
        }
        return courseRepo.save(course);
    }

    public List<Course> getCourses(Integer minCredit) {
        List<Course> allCourses = courseRepo.findAll();
        if (minCredit == null) {
            return allCourses;
        }

        List<Course> filteredList = new ArrayList<>();
        for (Course c : allCourses) {
            if (c.getCredit() >= minCredit) {
                filteredList.add(c);
            }
        }
        return filteredList;
    }

    public Enrollment enrollStudent(Enrollment enrollment) {
        Optional<Student> student = studentRepo.findById(enrollment.getStudentId());
        if (student.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Optional<Course> course = courseRepo.findById(enrollment.getCourseId());
        if (course.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        for (Enrollment e : enrollmentRepo.findAll()) {
            if (e.getStudentId().equals(enrollment.getStudentId()) &&
                    e.getCourseId().equals(enrollment.getCourseId()) &&
                    e.getSemester().equalsIgnoreCase(enrollment.getSemester())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT);
            }
        }

        int currentEnrollments = 0;
        for (Enrollment e : enrollmentRepo.findAll()) {
            if (e.getCourseId().equals(enrollment.getCourseId())) {
                currentEnrollments++;
            }
        }

        if (currentEnrollments >= course.get().getCapacity()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return enrollmentRepo.save(enrollment);
    }

    public Enrollment updateGrade(Long id, Double grade) {
        if (grade < 0.00 || grade > 4.00) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Optional<Enrollment> e = enrollmentRepo.findById(id);
        if (e.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Enrollment currentEnrollment = e.get();
        currentEnrollment.setGrade(grade);

        Optional<Enrollment> updated = enrollmentRepo.update(id, currentEnrollment);
        return updated.orElseThrow();
    }

    public TranscriptReport getTranscript(Long studentId) {
        Student student = getStudentById(studentId);

        List<TranscriptReport.CourseRecord> courseRecords = new ArrayList<>();
        double totalPoints = 0;
        int totalCredits = 0;

        for (Enrollment e : enrollmentRepo.findAll()) {
            if (e.getStudentId().equals(studentId)) {
                Optional<Course> c = courseRepo.findById(e.getCourseId());
                if (c.isPresent()) {
                    Course course = c.get();
                    courseRecords.add(new TranscriptReport.CourseRecord(
                            course.getCode(), course.getTitle(), e.getSemester(), e.getGrade()
                    ));

                    if (e.getGrade() != null) {
                        totalCredits += course.getCredit();
                        totalPoints += (e.getGrade() * course.getCredit());
                    }
                }
            }
        }

        double cgpa = 0.0;
        if (totalCredits > 0) {
            cgpa = totalPoints / totalCredits;
            cgpa = Math.round(cgpa * 100.0) / 100.0;
        }

        return new TranscriptReport(student.getId(), student.getName(), student.getDepartment(), cgpa, totalCredits, courseRecords);
    }

    public CourseRosterReport getCourseRoster(Long courseId) {
        Optional<Course> c = courseRepo.findById(courseId);
        if (c.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Course course = c.get();

        List<String> studentNames = new ArrayList<>();
        double totalGrades = 0;
        int gradedCount = 0;
        int seatsFilled = 0;

        for (Enrollment e : enrollmentRepo.findAll()) {
            if (e.getCourseId().equals(courseId)) {
                seatsFilled++;
                Optional<Student> s = studentRepo.findById(e.getStudentId());
                if (s.isPresent()) {
                    studentNames.add(s.get().getName());
                }
                if (e.getGrade() != null) {
                    totalGrades += e.getGrade();
                    gradedCount++;
                }
            }
        }

        int seatsRemaining = course.getCapacity() - seatsFilled;
        double avgGrade = 0.0;
        if (gradedCount > 0) {
            avgGrade = totalGrades / gradedCount;
            avgGrade = Math.round(avgGrade * 100.0) / 100.0;
        }

        return new CourseRosterReport(course.getCode(), course.getTitle(), course.getInstructor(), course.getCapacity(), seatsFilled, seatsRemaining, avgGrade, studentNames);
    }

    public List<DepartmentSummaryReport> getDepartmentSummary() {
        List<DepartmentSummaryReport> summaryReports = new ArrayList<>();
        List<Student> allStudents = studentRepo.findAll();
        List<String> uniqueDepartments = new ArrayList<>();

        for (Student s : allStudents) {
            if (!uniqueDepartments.contains(s.getDepartment())) {
                uniqueDepartments.add(s.getDepartment());
            }
        }

        for (String dept : uniqueDepartments) {
            int numStudents = 0;
            List<Student> deptStudents = new ArrayList<>();
            for (Student s : allStudents) {
                if (s.getDepartment().equalsIgnoreCase(dept)) {
                    numStudents++;
                    deptStudents.add(s);
                }
            }

            int totalEnrollments = 0;
            double totalCgpaSum = 0;
            List<Enrollment> deptEnrollments = new ArrayList<>();

            for (Student s : deptStudents) {
                totalCgpaSum += calculateStudentCgpa(s.getId());
                for (Enrollment e : enrollmentRepo.findAll()) {
                    if (e.getStudentId().equals(s.getId())) {
                        totalEnrollments++;
                        deptEnrollments.add(e);
                    }
                }
            }

            double avgCgpa = 0.0;
            if (numStudents > 0) {
                avgCgpa = totalCgpaSum / numStudents;
                avgCgpa = Math.round(avgCgpa * 100.0) / 100.0;
            }

            String mostPopularCourse = "None";
            int maxEnrollmentCount = 0;

            for (Course c : courseRepo.findAll()) {
                int courseCount = 0;
                for (Enrollment e : deptEnrollments) {
                    if (e.getCourseId().equals(c.getId())) {
                        courseCount++;
                    }
                }
                if (courseCount > maxEnrollmentCount) {
                    maxEnrollmentCount = courseCount;
                    mostPopularCourse = c.getTitle();
                }
            }

            summaryReports.add(new DepartmentSummaryReport(dept, numStudents, totalEnrollments, avgCgpa, mostPopularCourse));
        }

        return summaryReports;
    }

    public List<TopPerformerReport> getTopPerformers(int limit) {
        List<TopPerformerReport> allPerformers = new ArrayList<>();

        for (Student s : studentRepo.findAll()) {
            int creditsCompleted = 0;
            int coursesPassed = 0;
            double totalPoints = 0;

            for (Enrollment e : enrollmentRepo.findAll()) {
                if (e.getStudentId().equals(s.getId()) && e.getGrade() != null) {
                    Optional<Course> c = courseRepo.findById(e.getCourseId());
                    if (c.isPresent()) {
                        Course course = c.get();
                        creditsCompleted += course.getCredit();
                        totalPoints += (e.getGrade() * course.getCredit());
                        if (e.getGrade() >= 2.00) {
                            coursesPassed++;
                        }
                    }
                }
            }

            double cgpa = 0.0;
            if (creditsCompleted > 0) {
                cgpa = totalPoints / creditsCompleted;
                cgpa = Math.round(cgpa * 100.0) / 100.0;
            }

            allPerformers.add(new TopPerformerReport(s.getName(), s.getDepartment(), cgpa, creditsCompleted, coursesPassed));
        }

        for (int i = 0; i < allPerformers.size() - 1; i++) {
            for (int j = 0; j < allPerformers.size() - i - 1; j++) {
                if (allPerformers.get(j).getCgpa() < allPerformers.get(j + 1).getCgpa()) {
                    TopPerformerReport temp = allPerformers.get(j);
                    allPerformers.set(j, allPerformers.get(j + 1));
                    allPerformers.set(j + 1, temp);
                }
            }
        }

        List<TopPerformerReport> topList = new ArrayList<>();
        for (int i = 0; i < limit && i < allPerformers.size(); i++) {
            topList.add(allPerformers.get(i));
        }

        return topList;
    }

    private double calculateStudentCgpa(Long studentId) {
        double points = 0;
        int credits = 0;
        for (Enrollment e : enrollmentRepo.findAll()) {
            if (e.getStudentId().equals(studentId) && e.getGrade() != null) {
                Optional<Course> c = courseRepo.findById(e.getCourseId());
                if (c.isPresent()) {
                    credits += c.get().getCredit();
                    points += (e.getGrade() * c.get().getCredit());
                }
            }
        }
        if (credits == 0) return 0.0;
        return points / credits;
    }
}