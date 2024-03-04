import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ScoreVisitor implements Visitor {
    private class Tuple<T1, T2> {
        private final T1 x;
        private final T2 y;

        private Tuple(T1 x, T2 y) {
            this.x = x;
            this.y = y;
        }

        public T1 get_first() {
            return x;
        }

        public T2 get_second() {
            return y;
        }
    }

    private HashMap<Teacher, ArrayList<Tuple<String, Double>>> examScores = new HashMap<>();
    private HashMap<Assistant, ArrayList<Tuple<String, Double>>> partialScores = new HashMap<>();

    @Override
    public void visit(Assistant assistant) {
        Catalog catalog = Catalog.getInstance();

        int o = 0;
        if (partialScores.get(assistant) == null)
            partialScores.put(assistant, new ArrayList<>());
        if(catalog.getCourseBuilders().get(0).build().getGroups().get("5") != null)
            o = 1;
        if(o == 0)
        try {
            Scanner sc = new Scanner(new File("src/studenti.in"));
            while (sc.hasNextLine()) {
                String s = sc.nextLine();
                String[] words = s.split(" ");
                Student student = (Student) new UserFactory().getUser("Student", words[0], words[1]);

                Double partialGrade = Double.parseDouble(words[2]);
                partialScores.get(assistant).add(new Tuple<>(words[0] + " " + words[1], partialGrade));
                partialScores.put(assistant, partialScores.get(assistant));
                int i;
                Grade g;
                for (i = 0; i < 7; i++) {
                    //verific ca este asistentul care punele notele la grupa cu id-ul 0
                    if (catalog.getCourseBuilders().get(i).build().getGroups().get("0").getAssistant() == assistant) {
                        //Verific daca studentul este in grupa respectiva
                        for (Student stud : catalog.getCourseBuilders().get(i).build().getGroups().get("0")) {
                            if (stud.getFirstName().compareTo(student.getFirstName()) == 0 && stud.getLastName().compareTo(student.getLastName()) == 0) {

                                if (catalog.getCourseBuilders().get(i).build().getGrade(stud) == null) {
                                    g = new Grade(Double.parseDouble(words[2]), 0.0, stud, catalog.getCourses().get(i).getName());

                                    catalog.getCourseBuilders().get(i).addGrade(g);
                                    catalog.notifyObservers(g);

                                } else {
                                    g = catalog.getCourseBuilders().get(i).build().getGrade(stud);
                                    g.setPartialScore(Double.parseDouble(words[2]));
                                    catalog.notifyObservers(g);

                                }
                            }
                        }
                    }

                    if (catalog.getCourseBuilders().get(i).build().getGroups().get("1").getAssistant() == assistant) {

                        //Verific daca studentul este in grupa respectiva
                        for (Student stud : catalog.getCourseBuilders().get(i).build().getGroups().get("1")) {
                            if (stud.getFirstName().compareTo(student.getFirstName()) == 0 && stud.getLastName().compareTo(student.getLastName()) == 0) {

                                if (catalog.getCourseBuilders().get(i).build().getGrade(stud) == null) {
                                    g = new Grade(Double.parseDouble(words[2]), 0.0, stud, catalog.getCourses().get(i).getName());

                                    catalog.getCourseBuilders().get(i).addGrade(g);
                                    catalog.notifyObservers(catalog.getCourseBuilders().get(i).build().getGrade(stud));

                                } else {
                                    g = catalog.getCourseBuilders().get(i).build().getGrade(stud);
                                    g.setPartialScore(Double.parseDouble(words[2]));
                                    catalog.notifyObservers(catalog.getCourseBuilders().get(i).build().getGrade(stud));

                                }
                            }
                        }
                    }

                    if (catalog.getCourseBuilders().get(i).build().getGroups().get("2").getAssistant() == assistant) {
                        //Verific daca studentul este in grupa respectiva
                        for (Student stud : catalog.getCourseBuilders().get(i).build().getGroups().get("2")) {
                            if (stud.getFirstName().compareTo(student.getFirstName()) == 0 && stud.getLastName().compareTo(student.getLastName()) == 0) {

                                if (catalog.getCourseBuilders().get(i).build().getGrade(stud) == null) {
                                    g = new Grade(Double.parseDouble(words[2]), 0.0, stud, catalog.getCourses().get(i).getName());

                                    catalog.getCourseBuilders().get(i).addGrade(g);
                                    catalog.notifyObservers(catalog.getCourseBuilders().get(i).build().getGrade(stud));

                                } else {
                                    g = catalog.getCourseBuilders().get(i).build().getGrade(stud);
                                    g.setPartialScore(Double.parseDouble(words[2]));
                                    catalog.notifyObservers(catalog.getCourseBuilders().get(i).build().getGrade(stud));

                                }
                            }
                        }
                    }

                    if (catalog.getCourseBuilders().get(i).build().getGroups().get("3").getAssistant() == assistant) {
                        //Verific daca studentul este in grupa respectiva
                        for (Student stud : catalog.getCourseBuilders().get(i).build().getGroups().get("3")) {
                            if (stud.getFirstName().compareTo(student.getFirstName()) == 0 && stud.getLastName().compareTo(student.getLastName()) == 0) {

                                if (catalog.getCourseBuilders().get(i).build().getGrade(stud) == null) {
                                    g = new Grade(Double.parseDouble(words[2]), 0.0, stud, catalog.getCourses().get(i).getName());

                                    catalog.getCourseBuilders().get(i).addGrade(g);
                                    catalog.notifyObservers(catalog.getCourseBuilders().get(i).build().getGrade(stud));

                                } else {
                                    g = catalog.getCourseBuilders().get(i).build().getGrade(stud);
                                    g.setPartialScore(Double.parseDouble(words[2]));
                                    catalog.notifyObservers(catalog.getCourseBuilders().get(i).build().getGrade(stud));

                                }
                            }
                        }
                    }
                }
            }
            return;
        }
        catch (IOException e) {
            System.out.println("Nu s-a gasit fisierul studenti.in");
        }

        else
            try {
                Scanner sc = new Scanner(new File("src/bonusNotePartiale.txt"));
                Scanner sc2 = new Scanner(new File("src/bonusParoleParinti.txt"));
                while (sc.hasNextLine()) {
                    String s = sc.nextLine();
                    String[] words = s.split(":");
                    String[] username = words[0].split(",");
                    Student student = (Student) new UserFactory().getUser("Student", username[0], username[1]);

                    int ok = 0;
                    String[] username2 = new String[4];
                    while (sc2.hasNextLine()) {
                        String s2 = sc2.nextLine();
                        String[] words2 = s2.split(":");
                        username2 = words2[0].split(",");
                        if (username2[2].compareTo(username[0]) == 0 && username2[3].compareTo(username[1]) == 0) {
                            ok = 1;
                            break;
                        }
                    }
                    if (ok == 1) {
                        try {
                            FileWriter filewrit = new FileWriter("src/bonusNotifications.txt", true);
                            filewrit.write(username2[0] + "," + username2[1] + "," + username[0] + "," + username[1] + "," + username[2] + "\n");

                            filewrit.close();
                        } catch (Exception exc) {
                        }
                    }

                        Double partialGrade = Double.parseDouble(username[2]);
                        partialScores.get(assistant).add(new Tuple<>(username[0] + " " + username[1], partialGrade));
                        partialScores.put(assistant, examScores.get(assistant));
                        int i;
                        Grade g;

                        for (i = 0; i < 7; i++) {
                            for (Student stud : catalog.getCourseBuilders().get(i).build().getGroups().get("5")) {
                                if (stud.getFirstName().compareTo(student.getFirstName()) == 0 && stud.getLastName().compareTo(student.getLastName()) == 0) {

                                    if (catalog.getCourseBuilders().get(i).build().getGrade(stud) == null) {
                                        g = new Grade(Double.parseDouble(words[2]), 0.0, stud, catalog.getCourses().get(i).getName());

                                        catalog.getCourseBuilders().get(i).addGrade(g);
                                        catalog.notifyObservers(catalog.getCourseBuilders().get(i).build().getGrade(stud));

                                    } else {
                                        g = catalog.getCourseBuilders().get(i).build().getGrade(stud);
                                        g.setPartialScore(Double.parseDouble(words[2]));
                                        catalog.notifyObservers(catalog.getCourseBuilders().get(i).build().getGrade(stud));
                                    }
                                }
                            }

                        }
                }
            }
            catch (Exception e) {
            }
    }
    @Override
    public void visit(Teacher teacher) {
        Catalog catalog = Catalog.getInstance();
        /*
    adaug notele de la examen pentru fiecare student
       preiau informatia din fisierul studenti.in
     */
        //System.out.println("Visit teacher");
        int o = 0;
        if (examScores.get(teacher) == null)
            examScores.put(teacher, new ArrayList<>());
        if (catalog.getCourseBuilders().get(0).build().getGroups().get("5") != null)
            o = 1;
        if (o == 0) {
            try {
                Scanner sc = new Scanner(new File("src/studenti.in"));
                Scanner sc2 = new Scanner(new File("src/parents.in"));
                while (sc.hasNextLine()) {
                    String s = sc.nextLine();
                    String[] words = s.split(" ");
                    Student student = (Student) new UserFactory().getUser("Student", words[0], words[1]);

                    String s2 = sc2.nextLine();
                    String[] words2 = s2.split(" ");
                    Parent mother = (Parent) new UserFactory().getUser("Parent", words2[0], words2[1]);

                    s2 = sc2.nextLine();
                    words2 = s2.split(" ");
                    Parent father = (Parent) new UserFactory().getUser("Parent", words2[0], words2[1]);

                    student.setMother(mother);
                    student.setFather(father);

                    Double examGrade = Double.parseDouble(words[3]);
                    examScores.get(teacher).add(new Tuple<>(words[0] + " " + words[1], examGrade));
                    examScores.put(teacher, examScores.get(teacher));
                    int i;
                    Grade g;
                    for (i = 0; i < catalog.getCourseBuilders().size(); i++) {
                        if (catalog.getCourseBuilders().get(i).build().getTeacher() == teacher) //profesorul titular cursului pune notele
                        {
                            for (Student stud : catalog.getCourseBuilders().get(i).build().getAllStudents()) {
                                if (stud.getFirstName().compareTo(student.getFirstName()) == 0 && stud.getLastName().compareTo(student.getLastName()) == 0) {
                                    if (catalog.getCourseBuilders().get(i).build().getGrade(stud) == null) {
                                        g = new Grade(0.0, Double.parseDouble(words[3]), stud, catalog.getCourses().get(i).getName());
                                        catalog.getCourseBuilders().get(i).addGrade(g);
                                        catalog.notifyObservers(catalog.getCourseBuilders().get(i).build().getGrade(stud));
                                    } else {
                                        g = catalog.getCourseBuilders().get(i).build().getGrade(stud);
                                        g.setExamScore(Double.parseDouble(words[3]));
                                        catalog.getCourseBuilders().get(i).addGrade(g);
                                        catalog.notifyObservers(catalog.getCourseBuilders().get(i).build().getGrade(stud));
                                    }
                                }
                            }
                        }
                    }
                }
                return;
            } catch (IOException e) {
                System.out.println("Nu s-a gasit fisierul studenti.in");
            }
        } else {
            try {
                Scanner sc = new Scanner(new File("src/bonusNoteExamen.txt"));
                Scanner sc2 = new Scanner(new File("src/bonusParoleParinti.txt"));
                while (sc.hasNextLine()) {
                    String s = sc.nextLine();
                    String[] words = s.split(":");
                    String[] username = words[0].split(",");
                    Student student = (Student) new UserFactory().getUser("Student", username[0], username[1]);

                    int ok = 0;
                    String[] username2 = new String[4];
                    while (sc2.hasNextLine()) {
                        String s2 = sc2.nextLine();
                        String[] words2 = s2.split(":");
                        username2 = words2[0].split(",");
                        if (username2[2].compareTo(username[0]) == 0 && username2[3].compareTo(username[1]) == 0) {
                            ok = 1;
                            break;
                        }
                    }
                    if (ok == 1) {

                        try {
                            FileWriter filewrit = new FileWriter("src/bonusNotifications.txt", true);
                            filewrit.write(username2[0] + "," + username2[1] + "," + username[0] + "," + username[1] + "," + username[2] + "\n");

                            filewrit.close();
                        } catch (Exception exc) {
                        }
                    }


                        Double examGrade = Double.parseDouble(username[2]);
                        examScores.get(teacher).add(new Tuple<>(username[0] + " " + username[1], examGrade));
                        examScores.put(teacher, examScores.get(teacher));
                        int i;
                        Grade g;

                        for (i = 0; i < catalog.getCourseBuilders().size(); i++) {
                            if (catalog.getCourseBuilders().get(i).build().getTeacher().getFirstName().compareTo(teacher.getFirstName()) == 0) //profesorul titular cursului pune notele
                            {

                                for (Student stud : catalog.getCourseBuilders().get(i).build().getAllStudents()) {
                                    if (stud.getFirstName().compareTo(student.getFirstName()) == 0 && stud.getLastName().compareTo(student.getLastName()) == 0) {
                                        if (catalog.getCourseBuilders().get(i).build().getGrade(stud) == null) {
                                            g = new Grade(0.0, Double.parseDouble(username[2]), stud, catalog.getCourseBuilders().get(i).build().getName());
                                            catalog.getCourseBuilders().get(i).addGrade(g);
                                            catalog.notifyObservers(catalog.getCourseBuilders().get(i).build().getGrade(stud));
                                        } else {
                                            g = catalog.getCourseBuilders().get(i).build().getGrade(stud);
                                            g.setExamScore(Double.parseDouble(username[2]));
                                            catalog.getCourseBuilders().get(i).addGrade(g);
                                            catalog.notifyObservers(catalog.getCourseBuilders().get(i).build().getGrade(stud));
                                        }
                                    }
                                }
                            }
                        }

                    }
            } catch (Exception e) {
            }
        }

    }
    public HashMap<Teacher, ArrayList<Tuple<String, Double>>> getExamScores() {
        return examScores;
    }

    public HashMap<Assistant, ArrayList<Tuple<String, Double>>> getPartialScores() {
        return partialScores;
    }
}
