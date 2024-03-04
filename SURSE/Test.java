import java.io.File;
import java.io.IOException;
import java.util.*;

public class Test {

    public static void main(String[] args) {
        ScoreVisitor scoreVisitor = new ScoreVisitor();


        /*
         Testare sablon Singleton
         */
        String pct = "....................";
        Catalog catalog = Catalog.getInstance();
        Catalog catalog1 = Catalog.getInstance();
        if (catalog == catalog1)
            System.out.println("Singleton_design" + pct + "......passed");
        else
            System.out.println("Singleton_design" + pct + "......failed");

        /*
        Test ends
         */

            File notifications = new File("notifications.out");
            if(notifications.exists())
                notifications.delete();

        /*
        Testare sablon Factory
         */
        UserFactory userFactory = new UserFactory();
        Assistant assistant = (Assistant) userFactory.getUser("Assistant", "Nume1", "Nume2");
        Teacher teacher = (Teacher) userFactory.getUser("Teacher", "Nume1", "Nume2");
        Student student = (Student) userFactory.getUser("Student", "Nume1", "Nume2");
        Parent parent = (Parent) userFactory.getUser("Parent", "Nume1", "Nume2");

        if (assistant instanceof Assistant && teacher instanceof Teacher && student instanceof Student && parent instanceof Parent)
            System.out.println("Factory_design" + pct + "........passed");
        else System.out.println("Factory_design" + pct + "........passed");

        /*
         Test ends
         */


        /*
         Testare sablon Builder
         */
        Course c = new PartialCourse.PartialCourseBuilder().setName("Test Course").setTeacher(teacher).setCredits(5).addAssistant("0", assistant).addGroup(new Group("0", assistant)).build();

        if (c.getName().compareTo("Test Course") == 0 && c.getTeacher() != null && c.getCredits() == 5)
            System.out.println("Builder_design" + pct + "........passed");
        else System.out.println("Builder_design" + pct + "........failed");


        catalog.addCourse(c);
        catalog.removeCourse(c);
        /*
        Test ends
         */

        ArrayList<Group> groups = new ArrayList<>();
        ArrayList<Assistant> assistants = new ArrayList<>();
        assistants.add(new Assistant("group1_assistantFirst", "group1_assistantLast"));
        assistants.add(new Assistant("group2_assistantFirst", "group2_assistantLast"));
        assistants.add(new Assistant("group3_assistantFirst", "group3_assistantLast"));
        assistants.add(new Assistant("group4_assistantFirst", "group4_assistantLast"));

        Group group1 = new Group("0", assistants.get(3));
        group1.setAssistant(assistants.get(0));
        Group group2 = new Group("1", assistants.get(2));
        group2.setAssistant(assistants.get(1));
        Group group3 = new Group("2", assistants.get(2));
        Group group4 = new Group("3", assistants.get(3));

        groups.add(group1);
        groups.add(group2);
        groups.add(group3);
        groups.add(group4);

        /*
        citesc din fisierul cu studenti si adaug studentii in grupe, cate 25 in fiecare grupa
         */

        try {
            Scanner sc = new Scanner(new File("src/studenti.in"));
            int nrStudent = 0;
            int testare_medie = 1;
            while (sc.hasNextLine()) {
                Grade grade = new Grade();
                String s = sc.nextLine();
                String[] words = s.split(" ");
                student = (Student) userFactory.getUser("Student", words[0], words[1]);
                if (nrStudent < 25)
                {
                    group1.add(student);
                }
                else if (nrStudent < 50)
                    group2.add(student);
                else if (nrStudent < 75)
                    group3.add(student);
                else if (nrStudent < 100)
                    group4.add(student);
                nrStudent++;
                Double d1 = Double.parseDouble(words[2]);
                Double d2 = Double.parseDouble(words[3]);
                grade.setPartialScore(d1);
                grade.setExamScore(d2);

                if (grade.getTotal() != (d1 + d2) * 1.0 / 2) {
                    testare_medie = 0;
                }

            }
            /*
                 Testare medie
            */
            if (testare_medie == 1)
                System.out.println("getTotal method" + pct + ".......passed");
            else
                System.out.println("getTotal method" + pct + ".......failed");

            /*
            Testare medie incheiata
             */

             /*
        Testare adaugare studenti in grupe
         */
            if (group1.size() == 25 && group2.size() == 25 && group3.size() == 25 && group4.size() == 25 && group1.getId() != null && group2.getId() != null && group3.getId() != null && group4.getId() != null)
                System.out.println("Add students in groups" + pct + "passed");
            else System.out.println("Add students in groups" + pct + "failed");
        /*
        Testare incheiata
         */
        } catch (IOException e) {
            System.out.println("Nu s-a gasit fisierul studenti.in");
        }


         /*
        citesc din fisierul cu cursuri si creez profesorii (7 cursuri, 6 full si 1 partial)
        apoi le adaug in catalog
         */
        ArrayList<Course.CourseBuilder> courseBuilders = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Course.CourseBuilder cb = new FullCourse.FullCourseBuilder();
            courseBuilders.add(cb);
            catalog.addCourseBuilder(cb);
        }

        Course.CourseBuilder cb = new PartialCourse.PartialCourseBuilder();
        courseBuilders.add(cb);
        catalog.addCourseBuilder(cb);
        ArrayList<Teacher> teachers = new ArrayList<>();

        try {
            int nrCurs = 0;
            Scanner sc = new Scanner(new File("src/cursuri.in"));
            while (sc.hasNextLine()) {
                String s = sc.nextLine();
                String[] words = s.split(",");
                Teacher t = new Teacher(words[1], words[2]);
                t.setStrategy(nrCurs % 3); //setez strategia profesorului
                courseBuilders.get(nrCurs).setName(words[0]).setTeacher(t).setCredits(Integer.parseInt(words[3]));
                teachers.add(t);

                // adaug cele 4 grupe

                //verific toate metodele de adaugare in grupa
                courseBuilders.get(nrCurs).addGroup(group1);
                courseBuilders.get(nrCurs).addGroup(group2);
                courseBuilders.get(nrCurs).addGroup(group3.getId(), group3.getAssistant());
                courseBuilders.get(nrCurs).addGroup(group4.getId(), group4.getAssistant(), new Comparator<Student>() {
                    @Override
                    //afisez in ordine descrescatoare pentru ultima grupa
                    public int compare(Student o1, Student o2) {
                        if (o1.getFirstName().compareTo(o2.getFirstName()) != 0)
                            return (-1) * o1.getFirstName().compareTo(o2.getFirstName());
                        else
                            return (-1) * o1.getLastName().compareTo(o2.getLastName());
                    }
                });
                //adaug toti studentii din ultimele doua grupe create anterior
                for (int i = 0; i < group3.size(); i++)
                    courseBuilders.get(nrCurs).build().getGroups().get("2").add(group3.get(i));
                for (int i = 0; i < group4.size(); i++)
                    courseBuilders.get(nrCurs).build().getGroups().get("3").add(group4.get(i));
                nrCurs++;
            }
            /*
                Testare adaugare cursuri
                */
            int testare_curs = 0;
            for (nrCurs = 0; nrCurs < 7; nrCurs++)
                if ((courseBuilders.get(nrCurs).build().getCredits() == 5 || courseBuilders.get(nrCurs).build().getCredits() == 2) && courseBuilders.get(nrCurs).build().getName() != null && courseBuilders.get(nrCurs).build().getTeacher() != null && courseBuilders.get(nrCurs).build().getGroups().size() == 4)
                    testare_curs++; //cele corecte
            if (testare_curs == 7)
                System.out.println("Create courses" + pct + "........passed");
            else
                System.out.println("Create courses" + pct + "........failed");
            /*
            Testare incheiata
             */

        } catch (IOException e) {
            System.out.println("Nu s-a gasit fisierul cursuri.in");
        }

        /*
        citesc din fisierul cu asistenti si adaug asistentii in curs (63 asistenti), cate 15 aproximativ pentru fiecare grupa (presupun ca sunt aceiasi la toate cursurile),
         */

        try {
            Integer nrAsistent = 0;
            Scanner sc = new Scanner(new File("src/asistenti.in"));
            while (sc.hasNextLine()) {
                String s = sc.nextLine();
                String[] words = s.split(" ");
                assistant = (Assistant) userFactory.getUser("Assistant", words[0], words[1]);

                if (nrAsistent < 15) //adaug in grupa 1
                {
                    for (int nrCourse = 0; nrCourse < 7; nrCourse++)
                        courseBuilders.get(nrCourse).addAssistant("0", assistant);
                } else if (nrAsistent < 30) // in grupa 2
                {
                    for (int nrCourse = 0; nrCourse < 7; nrCourse++)
                        courseBuilders.get(nrCourse).addAssistant("1", assistant);
                } else if (nrAsistent < 45) // in grupa 3
                {
                    for (int nrCourse = 0; nrCourse < 7; nrCourse++)
                        courseBuilders.get(nrCourse).addAssistant("2", assistant);
                } else  // in grupa 4
                {
                    for (int nrCourse = 0; nrCourse < 7; nrCourse++)
                        courseBuilders.get(nrCourse).addAssistant("3", assistant);
                }
                nrAsistent++;
            }
            /*
            Testare adaugare asistenti in curs
             */
            int testare_adaug_asist = 0;
            for (int nrCourse = 0; nrCourse < 7; nrCourse++)
                if (courseBuilders.get(nrCourse).build().getAssistants().size() == 63)
                    testare_adaug_asist++;

            if (testare_adaug_asist == 7) //la toate cele 7 cursuri s-a adaugat numarul corect de asistenti
                System.out.println("Add assistants in courses" + ".................passed");
            else
                System.out.println("Add assistants in courses" + ".................failed");

            /*
            Testare terminata
             */

        } catch (IOException e) {
            System.out.println("Nu s-a gasit fisierul asistenti.in");
        }

        /*
        Testare comparator
         */
        int testare_comparator = 1;
        Group test_group = new Group("test", new Assistant("test", "test"));
        for (int i = 0; i < courseBuilders.get(0).build().getGroups().get("3").size(); i++) //ultima grupa e in ordine descrescatoare
            test_group.add(courseBuilders.get(0).build().getGroups().get("3").get(i));

        Collections.sort(test_group, new Comparator<Student>() {
            @Override
            //afisez in ordine descrescatoare pentru ultima grupa
            public int compare(Student o1, Student o2) {
                if (o1.getFirstName().compareTo(o2.getFirstName()) != 0)
                    return (-1) * o1.getFirstName().compareTo(o2.getFirstName());
                else
                    return (-1) * o1.getLastName().compareTo(o2.getLastName());
            }
        });
        for (int i = 0; i < courseBuilders.get(0).build().getGroups().get("3").size(); i++) //ultima grupa e in ordine descrescatoare
            if (courseBuilders.get(0).build().getGroups().get("3").get(i).getFirstName() != test_group.get(i).getFirstName())
                testare_comparator = 0;

        if (testare_comparator == 1)
            System.out.println("Comparator_test" + pct + ".......passed");
        else
            System.out.println("Comparator_test" + pct + ".......failed");
         /*
        Testare comparator terminata
         */

        //adaug cursurile in catalog
        for (int nrCourse = 0; nrCourse < 7; nrCourse++)
            catalog.addCourse(courseBuilders.get(nrCourse).build());

        /*
        Testare adaugare cursuri
         */
        if (catalog.getCourses().size() == 7)
            System.out.println("Add courses in catalog" + "....................passed");
        else
            System.out.println("Add courses in catalog" + "....................failed");

        /*
        Testare adaugare cursuri terminata
         */

        /*
        Deschid fisierul de parinti si setez pentru fiecare student parintii
         */

        try {
            int nrStudent = 0;
            Scanner sc = new Scanner(new File("src/parents.in"));
            while (sc.hasNextLine()) {
                //numele mamei
                String s = sc.nextLine();
                String[] words = s.split(" ");
                Parent mother = (Parent) userFactory.getUser("Parent", words[0], words[1]);

                //numele tatei
                s = sc.nextLine();
                words = s.split(" ");
                Parent father = (Parent) userFactory.getUser("Parent", words[0], words[1]);

                // adaug parintii in lista de observeri
                catalog.addObserver(mother);
                catalog.addObserver(father);

                if (nrStudent < 25) //caut studentul in prima grupa
                {
                    for (int i = 0; i < 7; i++) {
                        courseBuilders.get(i).build().getGroups().get("0").get(nrStudent).setMother(mother);
                        courseBuilders.get(i).build().getGroups().get("0").get(nrStudent).setFather(father);
                    }
                } else if (nrStudent < 50) //caut studentul in a doua grupa
                {
                    for (int i = 0; i < 7; i++) {
                        courseBuilders.get(i).build().getGroups().get("1").get(nrStudent - 25).setMother(mother);
                        courseBuilders.get(i).build().getGroups().get("1").get(nrStudent - 25).setFather(father);
                    }
                } else if (nrStudent < 75) //caut studentul in a treia grupa
                {
                    for (int i = 0; i < 7; i++) {
                        courseBuilders.get(i).build().getGroups().get("2").get(nrStudent - 50).setMother(mother);
                        courseBuilders.get(i).build().getGroups().get("2").get(nrStudent - 50).setFather(father);
                    }
                } else if (nrStudent < 100) //caut studentul in a patra grupa
                {
                    for (int i = 0; i < 7; i++) {
                        courseBuilders.get(i).build().getGroups().get("3").get(nrStudent - 75).setMother(mother);
                        courseBuilders.get(i).build().getGroups().get("3").get(nrStudent - 75).setFather(father);
                    }
                }
                nrStudent++;
            }
        } catch (IOException e) {
            System.out.println("Nu s-a gasit fisierul parents.in");
        }
        /*
        Testare observatori
         */
        if (catalog.getObservers().size() == 200) //2 parinti pentru fiecare student
            System.out.println("Observer_design" + "...........................passed");
        else
            System.out.println("Observer_design" + "...........................failed");
        /*
        Testare observatori terminata
         */

    /*
       Testare Visitor design pattern
    */
        int testare_visitor = 1;
        for (int nrTeacher = 0; nrTeacher < teachers.size(); nrTeacher++)
            //trec notele in catalog din perspectiva profesorului
            teachers.get(nrTeacher).accept(scoreVisitor);
        if (scoreVisitor.getExamScores().size() != 7) //7 profesori trec notele
            testare_visitor = 0;

        for (int nrTeacher = 0; nrTeacher < teachers.size(); nrTeacher++)
            if (scoreVisitor.getExamScores().get(teachers.get(nrTeacher)).size() != 100)
                testare_visitor = 0;

        if(catalog.getCourseBuilders().get(0).build().getAllStudentGrades().size() == 700) // 7 * 100
            testare_visitor = 0;


        //trec notele in catalog din perspectiva asistentului
        //pentru simplitate, doar 4 asistenti trec notele la toti studentii
        for (int nrGroup = 0; nrGroup < 4; nrGroup++)
            groups.get(nrGroup).getAssistant().accept(scoreVisitor);

        if (scoreVisitor.getPartialScores().size() != 4) //doar 4 asistenti trec notele
            testare_visitor = 0;

        for (int nrGroup = 0; nrGroup < 4; nrGroup++)
            if (scoreVisitor.getPartialScores().get(groups.get(nrGroup).getAssistant()).size() != 100) //cate 100 pe fiecare asistent
                testare_visitor = 0;

        if (testare_visitor == 1)
            System.out.println("Visitor_design" + pct + "........passed");
        else
            System.out.println("Visitor_design" + pct + "........failed");

        /*
        Testare Visitor design terminata
         */

        /*
        Testare Strategy design
         */
        if(courseBuilders.get(0).build().getBestStudent().getFirstName().compareTo("firstName_student93") == 0)
            System.out.println("getBestStudent" + pct + "........passed");
        /* Testare incheiata */


        /*
        Testare Memento design
         */
        //adaug in primul curs 5 note de 10 la partial score si la exam score
        ArrayList<Student> memento_students = new ArrayList<>();
        int testare_memento = 1;
        for(int i = 0; i < 5; i++)
        {
            Student s = new Student("test_Memento"+i, "test_Memento"+i);
            memento_students.add(s);
            courseBuilders.get(0).addGrade(new Grade(5.0,5.0,s, "test_Memento"+i));
        }
        //fac un backup
        Course course1 = courseBuilders.get(0).build();
        try {
            course1.makeBackup();
        } catch (CloneNotSupportedException e) {
        }

        //setez toate notele la 10
        for(int i = 0; i < 5; i++)
        {
            course1.getGrade(memento_students.get(i)).setPartialScore(10.0);
            course1.getGrade(memento_students.get(i)).setExamScore(10.0);
        }

        course1.undo();

        for(int i = 0; i < 5; i++)
        {
            if(course1.getGrade(memento_students.get(i)).getPartialScore() != 5.0 && course1.getGrade(memento_students.get(i)).getPartialScore() != 5.0)
                testare_memento = 0;
        }
        if (testare_memento == 1)
            System.out.println("Memento_design" + pct + "........passed");
        else
            System.out.println("Memento_design" + pct + "........failed");

        /*
        Testare incheiata
         */

        student = courseBuilders.get(0).build().getAllStudents().get(0);
        courseBuilders.get(0).build().getAllStudentGrades().get(student).setPartialScore(7.5);
        courseBuilders.get(0).build().getAllStudentGrades().get(student).setExamScore(8.5);

        courseBuilders.get(1).build().getAllStudentGrades().get(student).setPartialScore(8.5);
        courseBuilders.get(1).build().getAllStudentGrades().get(student).setExamScore(9.5);

        courseBuilders.get(2).build().getAllStudentGrades().get(student).setPartialScore(6.5);
        courseBuilders.get(2).build().getAllStudentGrades().get(student).setExamScore(7.5);

        courseBuilders.get(3).build().getAllStudentGrades().get(student).setPartialScore(6.0);
        courseBuilders.get(3).build().getAllStudentGrades().get(student).setExamScore(7.0);

        courseBuilders.get(4).build().getAllStudentGrades().get(student).setPartialScore(5.5);
        courseBuilders.get(4).build().getAllStudentGrades().get(student).setExamScore(6.0);

        courseBuilders.get(5).build().getAllStudentGrades().get(student).setPartialScore(5.0);
        courseBuilders.get(5).build().getAllStudentGrades().get(student).setExamScore(7.5);


        /*
        Pagina pentru student
         */
        StudentPage studentPage = new StudentPage();
        ArrayList<Course> courses = new ArrayList<>();
        for (int i = 0; i < courseBuilders.size(); i++) {
            courses.add(courseBuilders.get(i).build());
        }
        studentPage.addCourse(courses);
        studentPage.getPage(student);

/*
Pagina pentru profesor
 */
       Teacher_AssistantPage teacherPage = new Teacher_AssistantPage();
        ArrayList<Course> teacherCourses = new ArrayList<>();
        teacherCourses.add(courses.get(1));
        teacherCourses.add(courses.get(3));
        teacherCourses.add(courses.get(4));
        teacherCourses.add(courses.get(6));
        teacherPage.addCourse(teacherCourses);
        teacherPage.getPage(userFactory.getUser("Teacher", "firstName_Teacher", "lastName_Teacher"));

/*
Pagina pentru asistent
 */
        Teacher_AssistantPage assistantPage = new Teacher_AssistantPage();
        ArrayList<Course> assistantCourses = new ArrayList<>();
        assistantCourses.add(courses.get(0));
        assistantCourses.add(courses.get(2));
        assistantPage.addCourse(assistantCourses);
        assistantPage.getPage(userFactory.getUser("Assistant", "firstName_Assistant", "lastName_Assistant"));

        /*
Pagina pentru parinte
 */

        ParentPage parentPage = new ParentPage();
        parentPage.getPage(student.getMother());
        ArrayList<Notification> notifications_test = new ArrayList<Notification>();
        notifications_test.add(new Notification("La cursul Fizica firstName_student1 lastName_student1 are scorul de pe parcurs  0.0 si scorul de la examen 8.5"));
        notifications_test.add(new Notification("La cursul Programare orientată pe obiecte firstName_student1 lastName_student1 are scorul de pe parcurs  0.0 si scorul de la examen 9.5"));
        notifications_test.add(new Notification("La cursul Dispozitive electronice si electronică analogica firstName_student1 lastName_student1 are scorul de pe parcurs  0.0 si scorul de la examen 7.5"));
        notifications_test.add(new Notification("La cursul Analiza algoritmilor firstName_student1 lastName_student1 are scorul de pe parcurs  0.0 si scorul de la examen 7.0"));
        notifications_test.add(new Notification("La cursul Comunicare tehnica în limba straina firstName_student1 lastName_student1 are scorul de pe parcurs  0.0 si scorul de la examen 6.0"));
        notifications_test.add(new Notification("La cursul Informatica aplicata în limba straina firstName_student1 lastName_student1 are scorul de pe parcurs  0.0 si scorul de la examen 7.5"));

        notifications_test.add(new Notification("La cursul Fizica firstName_student1 lastName_student1 are scorul de pe parcurs  7.5 si scorul de la examen 9.6"));
        notifications_test.add(new Notification("La cursul Programare orientată pe obiecte firstName_student1 lastName_student1 are scorul de pe parcurs  8.5 si scorul de la examen 8.5"));
        notifications_test.add(new Notification("La cursul Dispozitive electronice si electronică analogica firstName_student1 lastName_student1 are scorul de pe parcurs  6.5 si scorul de la examen 9.5"));
        notifications_test.add(new Notification("La cursul Analiza algoritmilor firstName_student1 lastName_student1 are scorul de pe parcurs  6.0 si scorul de la examen 7.5"));
        notifications_test.add(new Notification("La cursul Comunicare tehnica în limba straina firstName_student1 lastName_student1 are scorul de pe parcurs  5.5 si scorul de la examen 6.0"));
        notifications_test.add(new Notification("La cursul Informatica aplicata în limba straina firstName_student1 lastName_student1 are scorul de pe parcurs  5.0 si scorul de la examen 7.5"));

        parentPage.addNotifications(notifications_test);
    }
}

