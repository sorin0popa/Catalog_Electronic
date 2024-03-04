import java.util.*;

public class FullCourse extends Course{

    public FullCourse(String name, Teacher teacher, int credits, HashSet<Assistant> assistants, ArrayList<Grade> grades, HashMap<String, Group> groups)
    {
        super(name, teacher, credits, assistants, grades, groups);
    }

    public ArrayList<Student> getGraduatedStudents() {
        ArrayList<Student> students = new ArrayList<>();
        HashMap<Student, Grade> grades = getAllStudentGrades();
        for(Map.Entry<Student, Grade> grade : grades.entrySet())
        {
            if(grade.getValue().getPartialScore() >= 3 && grade.getValue().getExamScore() >= 2)
                students.add(grade.getValue().getStudent());
        }

        return students;
    }
    public static class FullCourseBuilder extends CourseBuilder<FullCourse> {
        private String name;
        private Teacher teacher;
        private HashSet<Assistant> assistants = new HashSet<>();
        private HashMap<String, Group> groups = new HashMap<>();
        private ArrayList<Grade> grades = new ArrayList<>();
        private int credits;

        public CourseBuilder<FullCourse> addAssistant(String ID, Assistant assistant) {
            assistants.add(assistant);
            return this;
        }
        public CourseBuilder<FullCourse> addStudent(String ID, Student student) {
            groups.get(ID).add(student);
            return this;
        }

        public CourseBuilder<FullCourse> addGroup(Group group) {
            groups.put(group.getId(), group);

            return this;
        }

        public CourseBuilder<FullCourse> addGroup(String ID, Assistant assistant) {
            groups.put(ID, new Group(ID, assistant));

            return this;
        }

        public CourseBuilder<FullCourse> addGroup(String ID, Assistant assist, Comparator<Student> comp)
        {
            groups.put(ID, new Group(ID, assist, comp));
            return this;
        }

        public CourseBuilder<FullCourse> addGrade(Grade grade)
        {
            grades.add(grade);
            return this;
        }

        public CourseBuilder<FullCourse> setName(String name) {
            this.name = name;
            return this;
        }

        public CourseBuilder<FullCourse> setTeacher(Teacher teacher) {
            this.teacher = teacher;
            return this;
        }

        public CourseBuilder<FullCourse> setCredits(int credits) {
            this.credits = credits;
            return this;
        }

        public FullCourse build()
        {
            return new FullCourse(name, teacher, credits, assistants, grades, groups);
        }
    }
    public String toString()
    {
        return getName();
    }
}
