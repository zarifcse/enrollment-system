public class User {
    public void login() {
        System.out.println("User logged in");
    }
}

class StudentUser extends User {
    @Override
    public void login() {
        System.out.println("Student logged in");
    }
}

class TeacherUser extends User {
    @Override
    public void login() {
        System.out.println("Teacher logged in");
    }
}
