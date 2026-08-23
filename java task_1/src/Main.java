public class Main implements Reportale {
    @Override
    public void generateReport() {
        System.out.println("Generating the official student report...");
    }

    public static void main(String[] args) {
        System.out.println(" Dynamic Dispatch Demo ");
        User u;

        u = new StudentUser();
        u.login();

        u = new TeacherUser();
        u.login();

        var course1 = new Course();
        var course2 = new Course();

        var student1 = new Student ("22-48598-3', "ZAHIN");


        student1.enroll(course1);
        student1.enroll(course2);

        System.out.println();
        var mySystem = new Main();

        mySystem.printHeader();
        mySystem.generateReport();
        student1.showCourses();
        Reportale.printFooter();

    }
}