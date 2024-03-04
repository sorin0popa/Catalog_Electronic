import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

public class ParentPage extends JFrame{

        JLabel label = new JLabel();
        JPanel panel = new JPanel();
        Vector<Notification> notifications = new Vector<>();
        JList<Notification> listNotifications;
        JScrollPane scrollerNotifications;
        Parent p;

        public void getPage(Parent p) {
            this.p = p;
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setTitle("ParentPage");
            setMinimumSize(new Dimension(800, 600));
            setLayout(new BorderLayout());
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            listNotifications = new JList<>(notifications);

            label.setMaximumSize(new Dimension(800, 50));
            label.setText("Ai urmatoarele notificari:");
            label.setBackground(Color.WHITE);
            label.setFont(new Font("Times New Roman", Font.BOLD, 14));

            scrollerNotifications = new JScrollPane(listNotifications);
            listNotifications.setPreferredSize(new Dimension(1000, 300));
            scrollerNotifications.setMaximumSize(new Dimension(800, 200));

            panel.add(Box.createVerticalStrut(20));
            panel.add(label);
            panel.add(Box.createVerticalStrut(20));

            panel.add(scrollerNotifications);


            add(panel, BorderLayout.CENTER);
            pack();
            setVisible(true);
        }
    public void addNotifications(ArrayList<Notification> notifications)
    {
        for(Notification n : notifications)
            this.notifications.add(n);
    }


}


