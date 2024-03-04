import java.util.*;

public class BestTotalScore implements Strategy{
    @Override
    public Student getBestStudent(HashMap<Student, Grade> studentGrades) {
        ArrayList<Grade> grades = new ArrayList<>(studentGrades.values());

        grades.sort(new Comparator<Grade>() {
            @Override
            public int compare(Grade o1, Grade o2) {
                return o1.getTotal().compareTo(o2.getTotal());
            }
        });
        if(grades == null)
            return null;
        return grades.get(grades.size()-1).getStudent();
    }
}
