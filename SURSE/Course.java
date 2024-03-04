import java.util.*;

public abstract class Course {
     private final String name;
     private final Teacher teacher;
     private final HashSet<Assistant> assistants;
     private final ArrayList<Grade> grades;
     private final HashMap<String, Group> groups;
     private final int credits;

     public Course(String name, Teacher teacher, int credits, HashSet<Assistant> assistants, ArrayList<Grade> grades, HashMap<String, Group> groups)
    {
        this.name = name;
        this.teacher = teacher;
        this.credits = credits;
        if(assistants == null)
            this.assistants = new HashSet<>();
        else this.assistants = new HashSet<>(assistants);
        if(groups == null)
            this.groups = new HashMap<>();
        else this.groups = new HashMap<>(groups);
        if(grades == null)
            this.grades = new ArrayList<>();
        else this.grades = new ArrayList<>(grades);
    }

    public ArrayList<Student> getAllStudents()
    {
        ArrayList<Student> students = new ArrayList<>();
        for(Map.Entry<String, Group> group : groups.entrySet())
            students.addAll(group.getValue());
        return students;
    }
    public Grade getGrade(Student student){
        HashMap<Student, Grade> allStudentGrades = getAllStudentGrades();
        if(allStudentGrades.containsKey(student))
        {
            return allStudentGrades.get(student);

        }
        else return null;
    }
    public HashMap<Student, Grade> getAllStudentGrades()
    {
        HashMap<Student, Grade> studentsGrades = new HashMap<>();
            for (Grade grade : grades)
                studentsGrades.put(grade.getStudent(), grade);
        return studentsGrades;
    }

    public HashMap<String, Group> getGroups()
    {
        return groups;
    }

    public HashSet<Assistant> getAssistants() {
        return assistants;
    }

    public String getName()
    {
        return name;
    }
    public Teacher getTeacher()
    {
        return teacher;
    }
    public int getCredits()
    {
        return credits;
    }

    public Student getBestStudent()
    {
        return (teacher.getStrategy().getBestStudent(getAllStudentGrades()));
    }

    public abstract ArrayList<Student> getGraduatedStudents();

    public static abstract class CourseBuilder<T extends Course> {
        public abstract CourseBuilder<T> addAssistant(String ID, Assistant assistant);
        public abstract CourseBuilder<T> addStudent(String ID, Student student);

        public abstract CourseBuilder<T> addGroup(Group group);

        public abstract CourseBuilder<T> addGroup(String ID, Assistant assistant);

        public abstract CourseBuilder<T> addGroup(String ID, Assistant assist, Comparator<Student> comp);

        public abstract CourseBuilder<T> addGrade(Grade grade);
        public abstract CourseBuilder<T> setName(String name);
        public abstract CourseBuilder<T> setTeacher(Teacher teacher);
        public abstract CourseBuilder<T> setCredits(int credits);
        public abstract T build();
    }
    private class Snapshot{

        private final ArrayList<Grade> grades1;

        Snapshot(){
            grades1 = new ArrayList<>();
        }

    }

    private Snapshot snapshot = new Snapshot();
    public void makeBackup() throws CloneNotSupportedException {
        for(Grade grade : grades) {
            snapshot.grades1.add((Grade)grade.clone());
        }
    }
    public void undo() {
        if(snapshot.grades1.size() == 0)
        {
            System.out.println("Can't undo");
            return;
        }

        grades.removeAll(grades);

        for(Grade grade : snapshot.grades1)
        {
            grades.add(grade);

        }
    }
}