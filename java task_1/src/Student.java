public class Student {
    String studentId;
    String studentName;
    Course[] enrolledCourses;

    public Student(String studentId, String studentName, Course[] enrolledCourses) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.enrolledCourses = enrolledCourses;
    }

    public void enroll(Course c) {

    }

    public void showCourses() {
        System.out.println("Courses for " + studentName + ":");
        for (Course c: enrolledCourses) {
            if (c != null) {
                c.displayInfo();
            }
        }
    }

    public class Grade {
        Course course;
        double marks;

        public Grade(Course course, double marks) {
            this.course = course;
            this.marks = marks;
        }

        public String getLetterGrade() {
            if (marks >= 80) {
                return "A";
            }
            else if (marks >= 70 && marks <= 79) {
                return "B";
            }
            else if (marks >= 60 && marks <= 69) {
                return "C";
            }
            else if (marks >= 50 && marks <= 59) {
                return "D";
            }
            else {
                return "F";
            }
        }

        public static class Validator {
            public static boolean isValidId(String id) {
                if (id.startsWith("S") && id.length() >= 4) {
                    return true;
                }
                else {
                    return false;
                }
            }
        }
    }
}
