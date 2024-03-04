import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Parent extends  User implements Observer {
    public Parent(String firstName, String lastName) {
        super(firstName, lastName);
    }

    @Override
    public void update(Notification notification) {
        if(Catalog.getInstance().getCourseBuilders().get(0).build().getGroups().get("5") == null)
        try {
            FileWriter myFile = new FileWriter("src/notifications.out", true);

            myFile.write(notification.toString() + "\n");
            myFile.close();
        }
        catch(Exception e)
        {
            System.out.println("Eroare");

        }

        else{
            try {
                FileWriter myFile = new FileWriter("src/bonusNotificationsText.out", true);

                myFile.write(notification.toString() + "\n");
                myFile.close();
            }
            catch(Exception e)
            {
                System.out.println("Eroare");

            }

        }
    }
}
