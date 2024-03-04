import java.text.Normalizer;

public class Notification {
    private String notification = "Notification!";

    public Notification(String string)
    {
        notification = string;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String toString()
    {
        return notification;
    }

}
