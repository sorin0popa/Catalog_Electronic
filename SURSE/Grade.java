import java.util.ArrayList;

public class Grade implements Comparable, Cloneable {
    private Double partialScore, examScore;
    private Student student;
    private String course; // Numele cursului

    public Grade()
    {

    }
    public Grade(Double partialScore, Double examScore, Student student, String course)
    {
        this.partialScore = partialScore;
        this.examScore = examScore;
        this.student = student;
        this.course = course;
    }

    //getters and setters
    public void setStudent(Student student) {
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCourse() {
        return course;
    }

    public void setPartialScore(Double partialScore) {
        this.partialScore = partialScore;
    }

    public Double getPartialScore() {
        return partialScore;
    }

    public void setExamScore(Double examScore) {
        this.examScore = examScore;
    }

    public Double getExamScore() {
        return examScore;
    }

    public Double getTotal() { /* TODO */
        return (partialScore + examScore) * 1.0 / 2;
    }

    @Override
    public int compareTo(Object o) {
        if (((Grade) o).getTotal() > this.getTotal())
            return 1;
        else if (((Grade) o).getTotal() == this.getTotal())
            return 0;
        else
            return -1;
    }
    protected Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
    public String toString()
    {
        return student.getFirstName() + " " + student.getLastName() + ": partial score = " + partialScore + " exam score = " + examScore;

    }
}