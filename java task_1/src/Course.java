public class Course {
    String courseId;
    String courseName;
    CourseType type;
    double credit;

    public Course(String courseId, String courseName, CourseType type, double credit) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.type = type;
        this.credit = credit;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public CourseType getType() {
        return type;
    }

    public double getCredit() {
        return credit;
    }

    public void displayInfo() {
        System.out.println("Course: " + courseId + " Name: " + courseName + " Type: " + type + " Credits: " + credit);
    }
}
