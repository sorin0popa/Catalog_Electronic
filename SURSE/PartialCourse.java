import java.util.*;

public class PartialCourse extends Course {

    public PartialCourse(String name, Teacher teacher, int credits, HashSet<Assistant> assistants, ArrayList<Grade> grades, HashMap<String, Group> groups)
    {
        super(name, teacher, credits, assistants, grades, groups);
    }

    @Override
    public ArrayList<Student> getGraduatedStudents() {
        ArrayList<Student> students = new ArrayList<>();
        HashMap<Student, Grade> grades = getAllStudentGrades();
        for(Map.Entry<Student, Grade> grade : grades.entrySet())
        {
            if(grade.getValue().getTotal() >= 5)
                students.add(grade.getValue().getStudent());
        }

        return students;
    }

    public static class PartialCourseBuilder extends CourseBuilder<PartialCourse> {
        private String name;
        private Teacher teacher;
        private HashSet<Assistant> assistants = new HashSet<>();
        private HashMap<String, Group> groups = new HashMap<>();
         private ArrayList<Grade> grades = new ArrayList<>();
        private int credits;

        public CourseBuilder<PartialCourse> addAssistant(String ID, Assistant assistant) {
        //    groups.get(ID).setAssistant(assistant);
            assistants.add(assistant);
            return this;
        }
        public CourseBuilder<PartialCourse> addStudent(String ID, Student student) {
            groups.get(ID).add(student);
            return this;
        }

        public CourseBuilder<PartialCourse> addGroup(Group group) {
            groups.put(group.getId(), group);
            return this;
        }

        public CourseBuilder<PartialCourse> addGroup(String ID, Assistant assistant) {
            groups.put(ID, new Group(ID, assistant));
            return this;
        }

        public CourseBuilder<PartialCourse> addGroup(String ID, Assistant assist, Comparator<Student> comp)
        {
            groups.put(ID, new Group(ID, assist, comp));
            return this;
        }

        public CourseBuilder<PartialCourse> addGrade(Grade grade)
        {
            grades.add(grade);
            grades.sort(new Comparator<Grade>() {
                @Override
                public int compare(Grade o1, Grade o2) {
                    if(o1.getTotal() > o2.getTotal())
                        return 1;
                    else if(o1.getTotal() == o2.getTotal())
                        return 0;
                    else return -1;
                }
            });
            return this;
        }

        public CourseBuilder<PartialCourse> setName(String name) {
            this.name = name;
            return this;
        }

        public CourseBuilder<PartialCourse> setTeacher(Teacher teacher) {
            this.teacher = teacher;
            return this;
        }

        public CourseBuilder<PartialCourse> setCredits(int credits) {
            this.credits = credits;
            return this;
        }

        public PartialCourse build()
        {
            return new PartialCourse(name, teacher, credits, assistants, grades, groups);
        }
    }
    public String toString()
    {
        return getName();
    }

}
