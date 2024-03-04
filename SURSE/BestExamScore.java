import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class BestExamScore implements Strategy{
    @Override
    public Student getBestStudent(HashMap<Student, Grade> studentGrades) {
        ArrayList<Grade> grades = (ArrayList<Grade>) studentGrades.values();

        grades.sort(new Comparator<Grade>() {
            @Override
            public int compare(Grade o1, Grade o2) {
                return o1.getExamScore().compareTo(o2.getExamScore());
            }
        });
        if(grades == null)
            return null;
        return grades.get(grades.size()-1).getStudent();
    }

    }
