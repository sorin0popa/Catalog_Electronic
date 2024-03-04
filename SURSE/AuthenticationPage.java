import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

public class AuthenticationPage implements ActionListener, ListSelectionListener {

    JTextField firstName_userText = new JTextField();
    JLabel firstName_userLabel = new JLabel();

    JTextField lastName_userText = new JTextField();
    JLabel lastName_userLabel = new JLabel();
    JPasswordField passwordText = new JPasswordField();
    JLabel passwordLabel = new JLabel();
    JPanel panel = new JPanel();
    JButton login = new JButton("LOGIN");
    JButton signUp = new JButton("SIGN UP");
    JCheckBox showPass = new JCheckBox("Show Password");
    JFrame authenticationFrame = new JFrame();
    JFrame signUpFrame = new JFrame();
    int ok = 0;
    /*
    Pentru signUp page
     */
    Vector<String> options = new Vector<>();
    JList<String> listOptions;
    JScrollPane scrollerOptions;
    JLabel label = new JLabel("Alege profilul:");
    JPanel signUpPanel = new JPanel();
    JButton back = new JButton("BACK");
    JButton createAccount = new JButton("CREATE");
    JTextField firstName_Text2 = new JTextField();
    JLabel firstName_Label2 = new JLabel();
    JTextField lastName_Text2 = new JTextField();
    JLabel lastName_Label2 = new JLabel();
    JPasswordField passwordText2 = new JPasswordField();
    JLabel passwordLabel2 = new JLabel();
    JLabel check = new JLabel();

    /*
    folosit pentru Parent
     */
    JLabel firstName_studentLabel = new JLabel();
    JTextField firstName_studentText = new JTextField();
    JLabel lastName_studentLabel = new JLabel();
    JTextField lastName_studentText = new JTextField();
    /*
    pentru profesor
     */
    JLabel courseLabel = new JLabel();
    JList<Course> coursesList;
    JScrollPane scrollCourses;
    JList<Teacher> teachersList;
    JScrollPane scrollTeachers;
    JLabel teacherLabel = new JLabel();
    int sUp = 0;
    String userInstance;
    JCheckBox showPass2 = new JCheckBox("Show Password");

    public void getPage() {
        if (ok == 0) {
            authenticationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            authenticationFrame.setTitle("authenticationPage");
            authenticationFrame.setMinimumSize(new Dimension(800, 600));
            authenticationFrame.setLayout(new BorderLayout());

            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            firstName_userLabel.setMaximumSize(new Dimension(200, 20));
            firstName_userLabel.setText("Prenume:");
            firstName_userLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
            panel.add(firstName_userLabel);
            panel.add(Box.createVerticalStrut(10));

            firstName_userText.setMaximumSize(new Dimension(200, 20));
            panel.add(firstName_userText);

            lastName_userLabel.setMaximumSize(new Dimension(200, 20));
            lastName_userLabel.setText("Nume:");
            lastName_userLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
            panel.add(lastName_userLabel);
            panel.add(Box.createVerticalStrut(10));
            lastName_userText.setMaximumSize(new Dimension(200, 20));
            panel.add(lastName_userText);


            passwordLabel.setMaximumSize(new Dimension(200, 20));
            passwordLabel.setText("Parola:");
            passwordLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
            panel.add(passwordLabel);
            panel.add(Box.createVerticalStrut(10));


            passwordText.setMaximumSize(new Dimension(200, 20));
            panel.add(passwordText);
            panel.add(Box.createVerticalStrut(10));

            panel.add(showPass);
            panel.add(Box.createVerticalStrut(20));

            panel.add(login);
            panel.add(Box.createVerticalStrut(20));

            panel.add(signUp);
            panel.add(Box.createVerticalStrut(20));

            authenticationFrame.add(panel, BorderLayout.WEST);

            login.addActionListener(this);
            signUp.addActionListener(this);
            showPass.addActionListener(this);

            authenticationFrame.setVisible(true);
            ok = 1;
        } else {
            authenticationFrame.add(panel);
            authenticationFrame.setVisible(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == showPass) {
            if (showPass.isSelected()) {
                passwordText.setEchoChar((char) 0);
            } else {
                passwordText.setEchoChar('*');
            }
        } else if (e.getSource() == signUp) {
            firstName_Text2.setText("");
            lastName_Text2.setText("");
            passwordText2.setText("");
            firstName_studentText.setText("");
            lastName_studentText.setText("");

            authenticationFrame.dispose();
            signUpPage();
        } else if (e.getSource() == login) {
            try {
                File file = new File("src/bonusParoleStudenti.txt");
                Scanner sc = new Scanner(file);
                while (sc.hasNextLine()) {
                    String s = sc.nextLine();
                    String[] words = s.split(":");
                    String[] username = words[0].split(",");
                    if (firstName_userText.getText().compareTo(username[0]) == 0 && lastName_userText.getText().compareTo(username[1]) == 0) {
                        if (encrypt(passwordText.getText()).compareTo(words[1]) == 0) {
                            authenticationFrame.dispose();
                            StudentPage sP = new StudentPage();
                            ArrayList<Course> courses = new ArrayList<>();
                            for (int i = 0; i < TestareBonus.courseBuilders.size(); i++)
                                courses.add(TestareBonus.courseBuilders.get(i).build());

                            sP.addCourse(courses);

                            for (Student stud : TestareBonus.courseBuilders.get(0).build().getAllStudents()) {
                                if (stud.getFirstName().compareTo(username[0]) == 0 && stud.getLastName().compareTo(username[1]) == 0) {
                                    sP.getPage(stud);
                                    break;
                                }
                            }
                        }
                    }
                }
            } catch (IOException exc) {
                System.out.println("Eroare la bonusParoleStudenti.txt");
            }
            //caut in fisierul cu asistenti
            try {
                File file = new File("src/bonusParoleAsistenti.txt");
                Scanner sc = new Scanner(file);
                while (sc.hasNextLine()) {
                    String s = sc.nextLine();
                    String[] words = s.split(":");
                    String[] username = words[0].split(",");
                    if (firstName_userText.getText().compareTo(username[0]) == 0 && lastName_userText.getText().compareTo(username[1]) == 0) {
                        if (encrypt(passwordText.getText()).compareTo(words[1]) == 0) {
                            authenticationFrame.dispose();
                            Teacher_AssistantPage assistantPage = new Teacher_AssistantPage();
                            ArrayList<Course> courses = new ArrayList<>();
                            for (int i = 0; i < TestareBonus.courseBuilders.size(); i++)
                                courses.add(TestareBonus.courseBuilders.get(i).build());

                            assistantPage.addCourse(courses);

                            for (Assistant a : TestareBonus.courseBuilders.get(0).build().getAssistants()) {
                                if (a.getFirstName().compareTo(username[0]) == 0 && a.getLastName().compareTo(username[1]) == 0)
                                {
                                    assistantPage.getPage(a);
                                    break;
                                }
                            }
                        }
                    }
                }
            } catch (IOException exc) {
                System.out.println("Eroare la bonusParoleAsistenti.txt");
            }

            //caut in fisierul cu parinti
            try {
                File file = new File("src/bonusParoleParinti.txt");
                Scanner sc = new Scanner(file);
                while (sc.hasNextLine()) {
                    String s = sc.nextLine();
                    String[] words = s.split(":");
                    String[] username = words[0].split(",");
                    if (firstName_userText.getText().compareTo(username[0]) == 0 && lastName_userText.getText().compareTo(username[1]) == 0) {
                        if (encrypt(passwordText.getText()).compareTo(words[1]) == 0) {
                            authenticationFrame.dispose();
                            ParentPage parentPage = new ParentPage();
                            ArrayList<Notification> notifications = new ArrayList<>();
                            try {
                                File partialGrades = new File("src/bonusNotePartiale.txt");
                                Scanner pg = new Scanner(partialGrades);
                                while (pg.hasNextLine()) {
                                    String sPG = pg.nextLine();
                                    String[] wordsPG = sPG.split(",");
                                    if(wordsPG[0].compareTo(username[2]) == 0 && wordsPG[1].compareTo(username[3]) == 0)
                                        notifications.add(new Notification(username[2] + " " + username[3] + " are nota pe parcurs " + wordsPG[2] + " la cursul " + wordsPG[3]));
                                }
                            }
                            catch (Exception exc)
                            {System.out.println("Eroare la bonusNotePartiale.txt");}
                            try {
                                File partialGrades = new File("src/bonusNoteExamen.txt");
                                Scanner eg = new Scanner(partialGrades);
                                while (eg.hasNextLine()) {
                                    String sEG = eg.nextLine();
                                    String[] wordsEG = sEG.split(",");
                                    if(wordsEG[0].compareTo(username[2]) == 0 && wordsEG[1].compareTo(username[3]) == 0)
                                        notifications.add(new Notification(username[2] + " " + username[3] + " are nota de la examen " + wordsEG[2] + " la cursul " + wordsEG[3]));
                                }
                            }
                            catch (Exception exc)
                            {System.out.println("Eroare la bonusNoteExamen.txt");}
                            parentPage.addNotifications(notifications);
                            parentPage.getPage((Parent) (new UserFactory().getUser("Parent", username[0], username[1])));
                            break;
                        }
                    }
                }
            } catch (IOException exc) {
                {System.out.println("Eroare la bonusParoleParinti.txt");}

            }

            //caut in fisierul cu profesori
            try {
                File file = new File("src/bonusParoleProfesori.txt");
                Scanner sc = new Scanner(file);
                while (sc.hasNextLine()) {
                    String s = sc.nextLine();
                    String[] words = s.split(":");
                    String[] username = words[0].split(",");
                    if (firstName_userText.getText().compareTo(username[0]) == 0 && lastName_userText.getText().compareTo(username[1]) == 0) {
                        if (encrypt(passwordText.getText()).compareTo(words[1]) == 0) {
                            authenticationFrame.dispose();
                            Teacher_AssistantPage teacherPage = new Teacher_AssistantPage();
                            ArrayList<Course> courses = new ArrayList<>();
                            for (int i = 0; i < TestareBonus.courseBuilders.size(); i++)
                                courses.add(TestareBonus.courseBuilders.get(i).build());

                            teacherPage.addCourse(courses);

                            for(int i = 0; i < TestareBonus.courseBuilders.size(); i++) {
                                Teacher teacher = TestareBonus.courseBuilders.get(i).build().getTeacher();

                                if (teacher.getFirstName().compareTo(username[0]) == 0 && teacher.getLastName().compareTo(username[1]) == 0) {
                                    teacherPage.getPage(teacher);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            catch (IOException exc) {
                System.out.println("Eroare la deschiderea fisierului bonusParoleProfesori.txt");
            }

        }
        else if (e.getSource() == back) {
            firstName_userText.setText("");
            lastName_userText.setText("");
            passwordText.setText("");
            signUpFrame.dispose();
            listOptions.setEnabled(true);
            getPage();
        }
        else if (e.getSource() == createAccount) {
            if (lastName_Text2.getText().length() == 0 || firstName_Text2.getText().length() == 0 || passwordText2.getText().length() == 0) {
                check.setText("Nu ai introdus toate datele valid.");
                check.setForeground(Color.RED);
                signUpFrame.dispose();

                signUpPage();
                return;
            }

            if (userInstance.compareTo("Student") == 0) {
                try{
                    File file = new File("src/bonusParoleStudenti.txt");
                    Scanner sc = new Scanner(file);
                    while(sc.hasNextLine()) {
                        String s = sc.nextLine();
                        String[] words = s.split(":");
                        String[] username = words[0].split(",");
                        if(username[0].compareTo(firstName_Text2.getText()) == 0 && username[1].compareTo(lastName_Text2.getText()) == 0)
                        {
                            check.setText("Deja exista un cont cu acest nume.");
                            check.setForeground(Color.red);
                            signUpPage();
                            return;
                        }
                    }}
                catch(Exception exc)
                {             System.out.println("Eroare la deschiderea fisierului bonusParoleStudenti.txt");
                }
                try {

                    for(int i = 0; i < TestareBonus.courseBuilders.size(); i++)
                    {
                        Student s =  (Student)(new UserFactory()).getUser("Student", firstName_Text2.getText(), lastName_Text2.getText());
                        TestareBonus.courseBuilders.get(i).addStudent("5",s);
                        TestareBonus.courseBuilders.get(i).addGrade(new Grade(0.0, 0.0, s, TestareBonus.courseBuilders.get(i).build().getName()));
                    }

                    FileWriter pass = new FileWriter("src/bonusParoleStudenti.txt", true);
                    pass.append(firstName_Text2.getText() + ",");
                    pass.append(lastName_Text2.getText() + ":");
                    pass.append(encrypt(passwordText2.getText()));
                    pass.append("\n");

                    pass.close();
                } catch (IOException exc) {
                    System.out.println("Eroare la deschiderea fisierului bonusParoleStudenti.txt");
                }

            } else if (userInstance.compareTo("Assistant") == 0) {
                try{
                    File file = new File("src/bonusParoleAsistenti.txt");
                    Scanner sc = new Scanner(file);
                    while(sc.hasNextLine()) {
                        String s = sc.nextLine();
                        String[] words = s.split(":");
                        String[] username = words[0].split(",");
                        if(username[0].compareTo(firstName_Text2.getText()) == 0 && username[1].compareTo(lastName_Text2.getText()) == 0)
                        {
                            check.setText("Deja exista un cont cu acest nume.");
                            check.setForeground(Color.red);
                            signUpPage();
                            return;
                        }
                    }}
                catch(Exception exc)
                {             System.out.println("Eroare la deschiderea fisierului bonusParoleAsistenti.txt");
                }


                try {
                    for(int i = 0; i < TestareBonus.courseBuilders.size(); i++)
                        TestareBonus.courseBuilders.get(i).addAssistant("0",(Assistant)(new UserFactory()).getUser("Assistant", firstName_Text2.getText(), lastName_Text2.getText()));

                    FileWriter pass = new FileWriter("src/bonusParoleAsistenti.txt", true);
                    pass.append(firstName_Text2.getText() + ",");
                    pass.append(lastName_Text2.getText() + ":");
                    pass.append(encrypt(passwordText2.getText()));
                    pass.append("\n");

                    pass.close();
                } catch (IOException exc) {
                    System.out.println("Eroare la deschiderea fisierului bonusParoleAsistenti.txt");

                }

            } else if (userInstance.compareTo("Teacher") == 0) {
                try{
                    File file = new File("src/bonusParoleProfesori.txt");
                    Scanner sc = new Scanner(file);
                    while(sc.hasNextLine()) {
                        String s = sc.nextLine();
                        String[] words = s.split(":");
                        String[] username = words[0].split(",");
                        if(username[0].compareTo(firstName_Text2.getText()) == 0 && username[1].compareTo(lastName_Text2.getText()) == 0)
                        {
                            check.setText("Deja exista un cont cu acest nume.");
                            check.setForeground(Color.red);
                            signUpPage();
                            return;
                        }
                    }}
                catch(Exception exc)
                {             System.out.println("Eroare la deschiderea fisierului bonusParoleProfesori.txt");
                }

                try {
                    int ok = 0;
                    for(int i = 0; i < TestareBonus.courseBuilders.size(); i++)
                        if(TestareBonus.courseBuilders.get(i).build().getTeacher().getFirstName().compareTo(firstName_Text2.getText()) == 0 && TestareBonus.courseBuilders.get(i).build().getTeacher().getLastName().compareTo(lastName_Text2.getText()) == 0)
                            ok = 1;

                    if(ok == 1) {
                        FileWriter pass = new FileWriter("src/bonusParoleProfesori.txt", true);
                        pass.append(firstName_Text2.getText() + ",");
                        pass.append(lastName_Text2.getText() + ":");

                        pass.append(encrypt(passwordText2.getText()));
                        pass.append("\n");

                        pass.close();
                    }

                    else
                    {
                        signUpPage();
                        return;
                    }
                } catch (IOException exc) {
                    System.out.println("Eroare la deschiderea fisierului bonusParoleProfesori.txt");

                }

            } else if (userInstance.compareTo("Parent") == 0) {
                try{
                    File file = new File("src/bonusParoleParinti.txt");
                    Scanner sc = new Scanner(file);
                    while(sc.hasNextLine()) {
                        String s = sc.nextLine();
                        String[] words = s.split(":");
                        String[] username = words[0].split(",");
                        if(username[0].compareTo(firstName_Text2.getText()) == 0 && username[1].compareTo(lastName_Text2.getText()) == 0)
                        {
                            check.setText("Deja exista un cont cu acest nume.");
                            check.setForeground(Color.red);
                            signUpPage();
                            return;
                        }
                    }}
                catch(Exception exc)
                {             System.out.println("Eroare la deschiderea fisierului bonusParoleParinti.txt");
                }
                try {
                    for(Student stud : TestareBonus.courseBuilders.get(0).build().getAllStudents()) {
                        if (stud.getFirstName().compareTo(firstName_studentText.getText()) == 0 || stud.getLastName().compareTo(lastName_studentText.getText()) == 0) {
                            ok = 1;
                            Parent parent = (Parent)(new UserFactory()).getUser("Parent", firstName_Text2.getText(),lastName_Text2.getText());
                            stud.setMother(parent);
                            Catalog.getInstance().addObserver(parent);

                            if(ok == 0) {
                                signUpFrame.dispose();
                                signUpPage();
                                return;
                            }
                        }
                    }

                    FileWriter pass = new FileWriter("src/bonusParoleParinti.txt", true);
                    pass.append(firstName_Text2.getText() + ",");
                    pass.append(lastName_Text2.getText() + ",");
                    pass.append(firstName_studentText.getText() + ",");
                    pass.append(lastName_studentText.getText() + ":");
                    pass.append(encrypt(passwordText2.getText()));
                    pass.append("\n");

                    pass.close();
                } catch (IOException exc) {

                }


            }
            signUpFrame.dispose();
            getPage();
        } else if (e.getSource() == showPass2) {
            if (showPass2.isSelected()) {
                passwordText2.setEchoChar((char) 0);
            } else {
                passwordText2.setEchoChar('*');
            }
        }
    }

    public void signUpPage() {

        if (sUp == 0) {
            options.add("Student");
            options.add("Teacher");
            options.add("Assistant");
            options.add("Parent");

            listOptions = new JList<>(options);
            scrollerOptions = new JScrollPane(listOptions);

            signUpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            signUpFrame.setTitle("signUpPage");
            signUpFrame.setMinimumSize(new Dimension(1000, 600));
            signUpFrame.setLayout(new BorderLayout());

            signUpPanel.setLayout(new BoxLayout(signUpPanel, BoxLayout.Y_AXIS));

            listOptions.setMaximumSize(new Dimension(300, 80));
            scrollerOptions.setMaximumSize(new Dimension(400, 80));

            listOptions.addListSelectionListener(this);
            signUpPanel.add(label);
            signUpPanel.add(scrollerOptions, BorderLayout.NORTH);

            signUpFrame.add(signUpPanel);
            signUpFrame.setVisible(true);
            sUp = 1;
        } else {
            signUpFrame.setVisible(true);
            listOptions.setEnabled(true);
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(e.getSource() == teachersList)
        {
            firstName_Text2.setText(teachersList.getSelectedValue().getFirstName());
            lastName_Text2.setText(teachersList.getSelectedValue().getLastName());
        }
        else if(e.getSource() == listOptions)
        {
            for (Component c : signUpPanel.getComponents())
                signUpPanel.remove(c);

            signUpPanel.revalidate();
            signUpPanel.repaint();

            signUpPanel.add(label);
            signUpPanel.add(scrollerOptions);
            signUpPanel.add(check);
            check.setText("Introdu toate datele cerute.");
            check.setForeground(Color.black);


            if (showPass2.getActionListeners().length == 0)
                showPass2.addActionListener(this);
            if (back.getActionListeners().length == 0)
                back.addActionListener(this);
            signUpPanel.add(Box.createVerticalStrut(20));
            signUpPanel.add(back);


            firstName_Label2.setMaximumSize(new Dimension(200, 20));
            firstName_Label2.setFont(new Font("Times New Roman", Font.BOLD, 14));
            signUpPanel.add(firstName_Label2);
            signUpPanel.add(Box.createVerticalStrut(5));

            firstName_Text2.setMaximumSize(new Dimension(200, 20));
            signUpPanel.add(firstName_Text2);

            lastName_Label2.setMaximumSize(new Dimension(200, 20));
            lastName_Label2.setFont(new Font("Times New Roman", Font.BOLD, 14));
            signUpPanel.add(lastName_Label2);
            signUpPanel.add(Box.createVerticalStrut(5));

            lastName_Text2.setMaximumSize(new Dimension(200, 20));
            signUpPanel.add(lastName_Text2);


            passwordLabel2.setMaximumSize(new Dimension(200, 20));
            passwordLabel2.setFont(new Font("Times New Roman", Font.BOLD, 14));
            signUpPanel.add(passwordLabel2);
            signUpPanel.add(Box.createVerticalStrut(5));

            passwordText2.setMaximumSize(new Dimension(200, 20));
            signUpPanel.add(passwordText2);
            signUpPanel.add(Box.createVerticalStrut(5));


            JList list = (JList) e.getSource();
            String instance = (String) list.getSelectedValue();

            if (instance.compareTo("Teacher") == 0) {
                userInstance = "Teacher";

                firstName_Label2.setText("Prenume profesor:");
                lastName_Label2.setText("Nume profesor:");
                passwordLabel2.setText("Parola profesor:");

                teacherLabel.setText("Trebuie sa fii unul dintre:");

                courseLabel.setMaximumSize(new Dimension(200, 20));
                signUpPanel.add(teacherLabel);
                Vector<Teacher> teachers = new Vector<>();
                for(int i = 0; i < TestareBonus.courseBuilders.size(); i++)
                    teachers.add(TestareBonus.courseBuilders.get(i).build().getTeacher());

                teachersList=new JList<>(teachers);
                scrollTeachers = new JScrollPane(teachersList);

                teachersList.setMaximumSize(new Dimension(200, 40));
                scrollTeachers.setMaximumSize(new Dimension(400, 80));
                teachersList.addListSelectionListener(this);

                signUpPanel.add(scrollTeachers);


                courseLabel.setText("Alege cursul:");
                courseLabel.setMaximumSize(new Dimension(200, 20));


                Vector<Course> courses = new Vector<>();
                for(int i = 0; i < TestareBonus.courseBuilders.size(); i++)
                    courses.add(TestareBonus.courseBuilders.get(i).build());

                signUpPanel.add(courseLabel);
                coursesList=new JList<>(courses);
                scrollCourses = new JScrollPane(coursesList);

                coursesList.setMaximumSize(new Dimension(200, 40));
                scrollCourses.setMaximumSize(new Dimension(400, 80));
                coursesList.addListSelectionListener(this);
                signUpPanel.add(scrollCourses);

            } else if (instance.compareTo("Assistant") == 0) {
                userInstance = "Assistant";

                firstName_Label2.setText("Prenume asistent:");
                lastName_Label2.setText("Nume asistent:");

                passwordLabel2.setText("Parola asistent:");


            } else if (instance.compareTo("Student") == 0) {
                userInstance = "Student";

                firstName_Label2.setText("Prenume student:");

                lastName_Label2.setText("Nume student:");

                passwordLabel2.setText("Parola student:");
            } else if (instance.compareTo("Parent") == 0) {
                userInstance = "Parent";

                firstName_Label2.setText("Prenume parinte:");
                lastName_Label2.setText("Nume parinte:");

                passwordLabel2.setText("Parola parinte:");

                firstName_studentLabel.setMaximumSize(new Dimension(200, 20));
                firstName_studentLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
                firstName_studentLabel.setText("Prenume student:");
                signUpPanel.add(firstName_studentLabel);
                signUpPanel.add(Box.createVerticalStrut(5));

                firstName_studentText.setMaximumSize(new Dimension(200, 20));
                signUpPanel.add(firstName_studentText);


                lastName_studentLabel.setMaximumSize(new Dimension(200, 20));
                lastName_studentLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
                lastName_studentLabel.setText("Nume student:");
                signUpPanel.add(lastName_studentLabel);
                signUpPanel.add(Box.createVerticalStrut(5));

                lastName_studentText.setMaximumSize(new Dimension(200, 20));
                signUpPanel.add(lastName_studentText);
            }
            if (createAccount.getActionListeners().length == 0)
                createAccount.addActionListener(this);

            createAccount.add(Box.createVerticalStrut(20));
            createAccount.setMaximumSize(new Dimension(100, 20));
            signUpPanel.add(showPass2);
            signUpPanel.add(createAccount);
        }
    }

    public String encrypt(String s) {
        return Integer.toString(s.hashCode());
    }
}