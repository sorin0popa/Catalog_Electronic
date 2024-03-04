import javax.management.ValueExp;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Teacher_AssistantPage extends JFrame implements ListSelectionListener, ActionListener {

    JPanel panel = new JPanel();
    JPanel westPanel = new JPanel();
    JPanel southPanel = new JPanel();
    JLabel label2 = new JLabel("Selecteaza un student pentru a putea pune note.");
    JTextField text = new JTextField();
    Teacher teacher = null;
    Assistant assistant = null;
    Vector<Course> courses = new Vector<>();
    JList<Course> listCourses;
    JScrollPane scrollerCourses;
    JLabel label = new JLabel();

    JList<Student> listStudents;
    JScrollPane scrollStudents = new JScrollPane();
    DefaultListModel<Student> modelStudents = new DefaultListModel(); //refresh data
    JButton validation = new JButton("Valideaza nota");

    /*
    Alte butoane
     */

    JButton infoCourse = new JButton("Informatii curs");
    JButton addCourse = new JButton("Adauga curs");
    JButton addAsistent = new JButton("Adauga asistent");
    JButton addStudent = new JButton("Adauga student");
    /*
    Pagina infoCurs
     */
    DefaultListModel<Student> modelStud = new DefaultListModel<>();
    JList<Student> listStud = new JList<>(modelStud);
    JTextField studentText = new JTextField();

    JTextField studentText2 = new JTextField();
    JLabel strategy = new JLabel("Selecteaza strategia");

    JList<String> listStrateg;
    JScrollPane scrollStrateg;


    public void getPage(User t)
    {
        if(t instanceof Teacher)
            teacher = (Teacher)t;
        else assistant = (Assistant) t;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if(t instanceof Teacher)
            label.setText("Teacher Page ");
        else if(t instanceof Assistant)
            label.setText("Assistant Page ");
        setMinimumSize(new Dimension(800, 600));
        setLayout(new BorderLayout());

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.X_AXIS));

        label.setMaximumSize(new Dimension(800, 50));
        if(t instanceof Teacher)
            label.setText("Predai la urmatoarele cursuri: ");
        else if(t instanceof Assistant)
            label.setText("Esti asistent la urmatoarele cursuri: ");

        label.setFont(new Font("Times New Roman", Font.BOLD, 14));

        panel.add(Box.createVerticalStrut(20));
        panel.add(label);
        panel.add(Box.createVerticalStrut(10));

        listCourses = new JList<>(courses);
        listCourses.addListSelectionListener(this);
        scrollerCourses = new JScrollPane(listCourses);
        listCourses.setPreferredSize(new Dimension(300, 300));
        scrollerCourses.setMaximumSize(new Dimension(800, 200));
        panel.add(scrollerCourses);


        listStudents = new JList<>(modelStudents);
        scrollStudents = new JScrollPane(listStudents);
        //listCourseInfo.setPreferredSize(new Dimension(300, 300));
        listStudents.addListSelectionListener(new ListSelList());
        scrollStudents.setMaximumSize(new Dimension(800, 300));
        panel.add(Box.createVerticalStrut(20));

        westPanel.add(scrollStudents);

        label2.setMaximumSize(new Dimension(800, 50));
        label2.setText("Selecteaza un curs, apoi un student pentru a putea pune note. ");
        label2.setBackground(Color.WHITE);
        label2.setFont(new Font("Times New Roman", Font.BOLD, 14));

        westPanel.add(Box.createHorizontalStrut(20));
        westPanel.add(label2);

        westPanel.add(text);
        text.setVisible(false);

        validation.setEnabled(false);
        validation.addActionListener(this);
        addCourse.addActionListener(this);
        infoCourse.addActionListener(this);
        infoCourse.setEnabled(false);
        southPanel.add(validation);

        if(teacher != null) {
            southPanel.add(infoCourse);
            southPanel.add(addCourse);
        }

        add(panel, BorderLayout.NORTH);
        add(westPanel, BorderLayout.WEST);
        add(southPanel, BorderLayout.SOUTH);


        pack();
        setVisible(true);
    }

    public void addCourse(ArrayList<Course> courses)
    {
        for(Course c : courses)
            this.courses.add(c);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        Course course = listCourses.getSelectedValue();

        modelStudents.removeAllElements();

        ArrayList<Student> group = new Group("all_groups");
        for(Student s : course.getAllStudents() )
            group.add(s);

        for(Student s : group)
            modelStudents.addElement(s);

        label2.setText("Selecteaza un curs, apoi un student pentru a putea pune note. ");
        text.setVisible(false);
        validation.setEnabled(false);
        infoCourse.setEnabled(true);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        int error = 0;
        if(e.getSource() == infoCourse)
        {
            info_curs(listCourses.getSelectedValue());
            return;
        }

        if(e.getSource() == addStudent)
        {
            modelStud.addElement(new Student(studentText.getText(), studentText2.getText()));
            return;
        }

        if(e.getSource() == addCourse)
        {
            add_course();
            return;
        }

        try{
            Double grade = Double.parseDouble(text.getText());
            if(grade < 1 || grade > 10)
                error = 2;
        }
        catch(Exception exc)
        {
            error = 1;
        }
        if (error != 0)
        {
            label2.setText("Ai introdus o nota invalida. Incearca din nou");
            text.setText("");
        }
        else if(error == 0) {
            label2.setText("Ai introdus nota " + text.getText());


            if(teacher != null) {
                try {
                    FileWriter fw = new FileWriter("src/bonusNoteExamen.txt", true);
                    fw.append(listStudents.getSelectedValue().getFirstName() + "," + listStudents.getSelectedValue().getLastName() + ","+text.getText()+"," + listCourses.getSelectedValue()+"\n");
                    fw.close();
                    new ScoreVisitor().visit(teacher);
                } catch (Exception exc) {
                    System.out.println("Eroare la deschiderea fisierului bonusNoteExamen");
                }
            }
            else if(assistant != null) {
                try {
                    FileWriter fw = new FileWriter("src/bonusNotePartiale.txt", true);
                    fw.append(listStudents.getSelectedValue().getFirstName() + "," + listStudents.getSelectedValue().getLastName() + ","+text.getText()+"," + listCourses.getSelectedValue()+"\n");
                    fw.close();
                } catch (Exception exc) {
                    System.out.println("Eroare la deschiderea fisierului bonusNotePartiale");

                }
            }

            text.setVisible(false);
        }
        text.setText("");
    }

    class ListSelList implements ListSelectionListener
    {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            Student student = listStudents.getSelectedValue();

            label2.setText("Introdu noua nota:");
            validation.setEnabled(true);


            text.setVisible(true);
            text.setEditable(true);
            text.setPreferredSize(new Dimension(100, 50));
            text.setMaximumSize(new Dimension(800, 50));

            if(teacher != null)
            try {
                if (listCourses.getSelectedValue().getGrade(student).getExamScore() == null)
                    text.setText("0.0");
                else
                    text.setText(listCourses.getSelectedValue().getGrade(student).getExamScore().toString());
            }
            catch(Exception exc)
            {
                return;
            }

            else
                try {
                    if (listCourses.getSelectedValue().getGrade(student).getPartialScore() == null)
                        text.setText("0.0");
                    else
                        text.setText(listCourses.getSelectedValue().getGrade(student).getPartialScore().toString());
                }
                catch(Exception exc)
                {
                    return;
                }

            text.setBackground(Color.white);

        }
    }

    public void info_curs(Course c)
    {
        JFrame frame = new JFrame();
        frame.setTitle("Informatii curs.");
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setLayout(new FlowLayout());

        JLabel label = new JLabel("Studentii inscrisi la curs:");
        JLabel label2 = new JLabel("Prenume asistent");
        JLabel label3 = new JLabel("Nume asistent");
        JTextField text = new JTextField();
        text.setPreferredSize(new Dimension(200,30));
        JTextField text2 = new JTextField();
        text2.setPreferredSize(new Dimension(200, 30));



        try {
            for (int i = 0; i < c.getAllStudents().size(); i++)
                modelStud.addElement(c.getAllStudents().get(i));
        }
        catch (Exception e)
        {}
        JScrollPane scrollStudents = new JScrollPane(listStud);
        frame.add(label);
        frame.add(scrollStudents);
        frame.add(label2);
        frame.add(text);
        frame.add(label3);
        frame.add(text2);
        frame.add(addAsistent);

        JLabel studentLabel = new JLabel("Prenume student");
        JLabel studentLabel2 = new JLabel("Nume student");

        studentText.setPreferredSize(new Dimension(200,30));
        studentText2.setPreferredSize(new Dimension(200, 30));

        frame.add(studentLabel);
        frame.add(studentText);
        frame.add(studentLabel2);
        frame.add(studentText2);
        addStudent.addActionListener(this);
        frame.add(addStudent);

        Vector<String> strategyString = new Vector<>();
        strategyString.add("BestPartialScore");
        strategyString.add("BestExamScore");
        strategyString.add("BestTotalScore");

        listStrateg = new JList<>(strategyString);
        scrollStrateg = new JScrollPane(listStrateg);
        JLabel strategyLabel = new JLabel("Cel mai bun student conform strategiei alese este: ");
        JLabel bestStud = new JLabel("Nu ai ales.");


        listStrateg.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                c.getTeacher().setStrategy(listStrateg.getSelectedIndex());
                try{
                    bestStud.setText(c.getBestStudent().toString());
                }
                catch (Exception exc)
                {
                    bestStud.setText("Momentan niciun student nu are nota la acest curs.");
                }
            }
        });

        frame.add(scrollStrateg);

        frame.add(strategyLabel);
        frame.add(bestStud);

        frame.setVisible(true);
    }

    public void add_course()
    {
        JFrame frame = new JFrame();
        frame.setTitle("Adaugare curs.");
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel curs = new JLabel("Nume curs: ");
        JLabel credits = new JLabel("Numar credite: ");
        JLabel nume = new JLabel("Nume profesor titular: ");
        JLabel prenume = new JLabel("Prenume profesor titular: ");

        JTextField cursText = new JTextField();
        cursText.setPreferredSize(new Dimension(200, 30));
        JTextField creditsText = new JTextField();
        creditsText.setPreferredSize(new Dimension(200, 30));
        JTextField numeText = new JTextField();
        numeText.setPreferredSize(new Dimension(200, 30));
        JTextField prenumeText = new JTextField();
        prenumeText.setPreferredSize(new Dimension(200, 30));

        panel.add(curs);
        panel.add(cursText);
        panel.add(credits);
        panel.add(creditsText);
        panel.add(nume);
        panel.add(numeText);
        panel.add(prenume);
        panel.add(prenumeText);

        cursText.setText("");
        creditsText.setText("");
        numeText.setText("");
        prenumeText.setText("");

        JLabel added = new JLabel("Adauga date despre curs.");
        panel.add(added);
        JButton addCurs = new JButton("Adauga curs");
        panel.add(addCurs);
        addCurs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cursText.getText().equals("") == true) {
                    added.setForeground(Color.red);
                    added.setText("Nu ai scris numele corect.");
                    return;
                }
                System.out.println(credits + "  "  + creditsText.getText());

                if(creditsText.getText().equals("") == true) {
                    added.setForeground(Color.red);
                    added.setText("Nu ai scris numarul de credite corect.");
                    return;
                }
                if(numeText.getText().equals("") == true) {
                    added.setForeground(Color.red);
                    added.setText("Nu ai adaugat numele profesorului corect.");
                    return;

                }
                if(prenumeText.getText().equals("") == true) {
                    added.setForeground(Color.red);
                    added.setText("Nu ai adaugat prenumele profesorului corect.");
                    return;
                }
                    try{
                        Double.parseDouble(creditsText.getText());

                        added.setForeground(Color.black);
                        added.setText("Curs adaugat.");
                        cursText.setText("");
                        creditsText.setText("");
                        numeText.setText("");
                        prenumeText.setText("");

                    }
                    catch (Exception exc)
                    {
                        added.setForeground(Color.red);
                        added.setText("Nu ai adaugat un numar valid de credite.");
                    }

            }
        });

        frame.add(panel, BorderLayout.NORTH);
        frame.setVisible(true);
    }
}
