import java.util.ArrayList;

public class Catalog implements Subject
{
    /* TODO Implementarea pentru clasa Catalog */
    private ArrayList<Course> courses = new ArrayList<>();
    private ArrayList<Observer> observers = new ArrayList<>();

    /*
     * Singleton design pattern
     */
    private static Catalog instance  = new Catalog();
    private Catalog(){}

    public static Catalog getInstance(){
        return instance;
    }
    /*
     * Singleton design pattern code ends
     */

    // Adauga un curs în catalog
    public void addCourse(Course course){
        courses.add(course);
    }
    // Sterge un curs din catalog
    public void removeCourse(Course course) {
        courses.remove(course);
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public ArrayList<Observer> getObservers() {
        return observers;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Grade grade) {
        try{
            grade.getStudent().getMother().update(new Notification("La cursul " + grade.getCourse() + " " + grade.getStudent().toString() + " are scorul de pe parcurs  " + grade.getPartialScore() + " si scorul de la examen " + grade.getExamScore()));
        }
        catch(Exception e) {
        }
    }
    private final ArrayList<Course.CourseBuilder> courseBuilders = new ArrayList<>();

    public void addCourseBuilder(Course.CourseBuilder courseBuilder)
    {
        courseBuilders.add(courseBuilder);
    }

    public ArrayList<Course.CourseBuilder> getCourseBuilders() {
        return courseBuilders;
    }
}

