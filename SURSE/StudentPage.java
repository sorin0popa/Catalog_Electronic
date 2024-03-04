import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

public class StudentPage extends JFrame implements ListSelectionListener {

    JLabel label = new JLabel();
    JPanel panel = new JPanel();
    Vector<Course> courses = new Vector<>();
    JList<Course> listCourses;
    JScrollPane scrollerCourses;

    JList<String> listCourseInfo;
    JScrollPane scrollCourseInfo = new JScrollPane();
    DefaultListModel<String> modelCourseInfo = new DefaultListModel(); //refresh data
    Student s;
    JLabel label2 = new JLabel("Te rog selecteaza un curs pentru a afla mai multe detalii.");

    public void getPage(Student s) {
        this.s = s;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("StudentPage");
        setMinimumSize(new Dimension(800, 600));
        setLayout(new BorderLayout());
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        listCourses = new JList<>(courses);
        listCourses.addListSelectionListener(this);

        label.setMaximumSize(new Dimension(800, 50));
        label.setText("Esti inscris la urmatoarele cursuri: ");
        label.setBackground(Color.WHITE);
        label.setFont(new Font("Times New Roman", Font.BOLD, 14));

        scrollerCourses = new JScrollPane(listCourses);
        listCourses.setPreferredSize(new Dimension(300, 300));
        scrollerCourses.setMaximumSize(new Dimension(800, 200));

        panel.add(Box.createVerticalStrut(20));
        panel.add(label);
        panel.add(Box.createVerticalStrut(20));

        panel.add(scrollerCourses);

        modelCourseInfo.addElement("Niciun curs selectat.");
        listCourseInfo = new JList<>(modelCourseInfo);
        scrollCourseInfo = new JScrollPane(listCourseInfo);
        //listCourseInfo.setPreferredSize(new Dimension(300, 300));
        scrollCourseInfo.setMaximumSize(new Dimension(800, 300));
        panel.add(Box.createVerticalStrut(20));
        panel.add(scrollCourseInfo);

        label2.setMaximumSize(new Dimension(800, 50));
        label2.setBackground(Color.WHITE);
        label2.setFont(new Font("Times New Roman", Font.BOLD, 14));

        panel.add(Box.createVerticalStrut(20));
        panel.add(label2);

        add(panel, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }


    public void addCourse(ArrayList<Course> courses) {
        for (Course c : courses)
            this.courses.add(c);
    }


    @Override
    public void valueChanged(ListSelectionEvent e) {
        Course course = listCourses.getSelectedValue();
        Double partial = 0.0, exam = 0.0;
        modelCourseInfo.removeAllElements();

        int ok = 0;
        if (Catalog.getInstance().getCourseBuilders().get(0).build().getGroups().get("5") == null) {
            try {
                if (course.getAllStudentGrades().get(s).getPartialScore() == null)
                    partial = 0.0;
                else partial = course.getAllStudentGrades().get(s).getPartialScore();

                if (course.getAllStudentGrades().get(s).getExamScore() == null)
                    exam = 0.0;
                else
                    exam = course.getAllStudentGrades().get(s).getExamScore();
            } catch (Exception exc) {
                partial = 0.0;
                exam = 0.0;
            }
            try {
                String label[] = {"Nume curs: " + course.getName(),
                        "Numar credite: " + course.getCredits(),
                        "Profesor titular: " + course.getTeacher(),
                        "PartialScore: " + partial,
                        "ExamScore: " + exam,
                        "Asistentul asociat: " + course.getGroups().get("0").getAssistant(),
                        "Lista asistenti: " + course.getAssistants()
                };
                label2.setText("Ai o medie de: " + course.getAllStudentGrades().get(s).getTotal() + " la acest curs.");
                for (String s : label)
                    modelCourseInfo.addElement(s);
                ok = 1;
            } catch (Exception exc) {
            }
        }
//pentru bonus
        if (Catalog.getInstance().getCourseBuilders().get(0).build().getGroups().get("5") != null) {
            for (Student stud : Catalog.getInstance().getCourseBuilders().get(0).build().getAllStudents())
                if (s.getFirstName().compareTo(stud.getFirstName()) == 0 && s.getLastName().compareTo(stud.getLastName()) == 0) {
                    try {
                        try {
                            if (course.getAllStudentGrades().get(stud).getPartialScore() == null)
                                partial = 0.0;
                            else partial = course.getAllStudentGrades().get(stud).getPartialScore();

                            if (course.getAllStudentGrades().get(stud).getExamScore() == null)
                                exam = 0.0;
                            else
                                exam = course.getAllStudentGrades().get(s).getExamScore();
                        } catch (Exception exc) {
                            partial = 0.0;
                            exam = 0.0;
                        }

                        String label[] = {
                                "Nume curs: " + course.getName(),
                                "Numar credite: " + course.getCredits(),
                                "Profesor titular: " + course.getTeacher(),
                                "PartialScore: " + partial,
                                "ExamScore: " + exam,
                                "Asistentul asociat: " + course.getGroups().get("5").getAssistant(),
                                "Lista asistenti: " + course.getAssistants()
                        };

                        for (String s : label)
                            modelCourseInfo.addElement(s);

                        if(course.getAllStudentGrades().get(stud) != null) {

                            label2.setText("Ai o medie de: " + course.getAllStudentGrades().get(stud).getTotal() + " la acest curs.");

                            ok = 1;
                        }
                        break;
                    }
                    catch (Exception exc) {
                    }
                }
        }

            if (ok == 0)
                label2.setText("Nu ai note puse la acest curs.");
        }
    }
