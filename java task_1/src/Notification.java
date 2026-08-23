public class Notification {
    public static void sendMessages(String... messages) {
        for (String message : messages) {
            System.out.println("Notification: " + message);
        }
    }
}
