import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class TestareBonus {

    Catalog catalog = Catalog.getInstance();
    static ArrayList<Course.CourseBuilder> courseBuilders = new ArrayList<>();

    public static void main(String[] args) {
        AuthenticationPage page = new AuthenticationPage();
        try {
            new File("src/bonusParoleAsistenti.txt").createNewFile();
        }
        catch(Exception e) {
            System.out.println("Eroare la deschiderea fisierului bonusParoleAsistenti.txt");
        }
        try {
            new File("src/bonusParoleParinti.txt").createNewFile();
        }
        catch (Exception e){
            System.out.println("Eroare la deschiderea fisierului bonusParoleParinti.txt");

        }
        try {
            new File("src/bonusParoleProfesori.txt").createNewFile();
        }
        catch (Exception e){
            System.out.println("Eroare la deschiderea fisierului bonusParoleProfesori.txt");
        }
        try {
            new File("src/bonusParoleStudenti.txt").createNewFile();
        }
        catch (Exception e) {
            System.out.println("Eroare la deschiderea fisierului bonusParoleStudenti.txt");

        }
        try {
            new File("src/bonusNoteExamen.txt").createNewFile();
        }
        catch (Exception e) {
            System.out.println("Eroare la deschiderea fisierului bonusNoteExamen.txt");

        }
        try {
            new File("src/bonusNotePartiale.txt").createNewFile();
        }
        catch (Exception e) {
            System.out.println("Eroare la deschiderea fisierului bonusNotePartiale.txt");

        }
        try {
            new File("src/bonusNotifications.txt").createNewFile();
        }
        catch (Exception e) {
            System.out.println("Eroare la deschiderea fisierului bonusNotifications.txt");

        }

        TestareBonus obj = new TestareBonus();
        obj.createCourses();
        obj.addStudents();
        obj.addAssistants();
        obj.addStudentGrades();

        page.getPage();
    }

    public void createCourses() {
        try {
            for (int i = 0; i < 6; i++) {
                Course.CourseBuilder cb = new FullCourse.FullCourseBuilder();
                courseBuilders.add(cb);
                catalog.addCourseBuilder(cb);
            }

            Course.CourseBuilder cb = new PartialCourse.PartialCourseBuilder();
            courseBuilders.add(cb);
            catalog.addCourseBuilder(cb);

            int nrCurs = 0;
            Scanner sc = new Scanner(new File("src/cursuri.in"));
            while (sc.hasNextLine()) {
                String s = sc.nextLine();
                String[] words = s.split(",");
                Teacher t = new Teacher(words[1], words[2]);
                t.setStrategy(nrCurs % 3); //setez strategia profesorului
                courseBuilders.get(nrCurs).setName(words[0]).setTeacher(t).setCredits(Integer.parseInt(words[3]));
                courseBuilders.get(nrCurs).addGroup("5", new Assistant("asistent", "asistent"));
                nrCurs++;
            }
        }
        catch(Exception e)
        {
            System.out.println("Fisierul cursuri.in nu s-a gasit.");
        }
    }
    public void addStudents() //daca sunt studenti in fisier
    {
        try {
            Scanner sc = new Scanner(new File("src/bonusParoleStudenti.txt"));
            while (sc.hasNextLine()) {
                String s = sc.nextLine();
                String[] words = s.split(":");
                String[] username = words[0].split(",");
                for(int i = 0; i < courseBuilders.size(); i++)
                {
                    courseBuilders.get(i).addStudent("5",new Student(username[0], username[1]));
                }
            }
        }
        catch(Exception e){
            System.out.println("Eroare la deschiderea fisierului bonusParoleStudenti.txt");

        }
    }

    public void addStudentGrades()
    {
        try {
            Scanner sc = new Scanner(new File("src/bonusNoteExamen.txt"));
            while (sc.hasNextLine()) {
                String s = sc.nextLine();
                String[] words = s.split(",");
                for(Student stud : courseBuilders.get(0).build().getAllStudents())
                    if(stud.getFirstName().compareTo(words[0]) == 0 && stud.getLastName().compareTo(words[1]) == 0)
                        for(int i = 0; i < courseBuilders.size(); i++) {
                            if (courseBuilders.get(i).build().getName().compareTo(words[3]) == 0) {
                                if (courseBuilders.get(i).build().getGrade(stud) == null)
                                    courseBuilders.get(i).addGrade(new Grade(0.0, Double.parseDouble(words[2]), stud, courseBuilders.get(i).build().getName()));
                                else courseBuilders.get(i).build().getGrade(stud).setExamScore(Double.parseDouble(words[2]));
                            }
                        }
            }
        }
        catch(Exception e){
            System.out.println("Eroare la deschiderea fisierului bonusNoteExamen.txt");

        }

        try {
            Scanner sc = new Scanner(new File("src/bonusNotePartiale.txt"));
            while (sc.hasNextLine()) {
                String s = sc.nextLine();
                String[] words = s.split(",");
                for(Student stud : courseBuilders.get(0).build().getAllStudents())
                    if(stud.getFirstName().compareTo(words[0]) == 0 && stud.getLastName().compareTo(words[1]) == 0)
                        for(int i = 0; i < courseBuilders.size(); i++) {
                            if (courseBuilders.get(i).build().getName().compareTo(words[3]) == 0) {
                                if (courseBuilders.get(i).build().getGrade(stud) == null)
                                    courseBuilders.get(i).addGrade(new Grade( Double.parseDouble(words[2]), 0.0, stud, courseBuilders.get(i).build().getName()));
                                else courseBuilders.get(i).build().getGrade(stud).setPartialScore(Double.parseDouble(words[2]));
                            }
                        }
            }
        }
        catch(Exception e){
            System.out.println("Eroare la deschiderea fisierului bonusNotePartiale.txt");

        }

    }

    public void addAssistants()
    {
        try {
            Scanner sc = new Scanner(new File("src/bonusParoleAsistenti.txt"));
            while (sc.hasNextLine()) {
                String s = sc.nextLine();
                String[] words = s.split(":");
                String[] username = words[0].split(",");
                for(int i = 0; i < courseBuilders.size(); i++)
                {
                    TestareBonus.courseBuilders.get(i).addAssistant("5",(Assistant)(new UserFactory()).getUser("Assistant", username[0], username[1]));
                }
            }
        }
        catch(Exception e){
            System.out.println("Eroare la deschiderea fisierului bonusParoleAsistenti.txt");

        }
    }
}
