import java.util.HashMap;

public interface Strategy {
    public Student getBestStudent(HashMap<Student, Grade> studentGrades);
}
