import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class Group extends ArrayList<Student> {
    private Assistant assistant;
    private String id;
    private final Comparator<Student> comp;
    public void setAssistant(Assistant assistant)
    {
        this.assistant = assistant;
    }
    public void setId(String id)
    {
        this.id = id;
    }
    public Assistant getAssistant()
    {
        return assistant;
    }
    public String getId()
    {
        return id;
    }
    public Group(String ID, Assistant assistant, Comparator<Student> comp) {
        this.id = ID;
        this.assistant = assistant;
        this.comp = comp;
    }
    public Group(String ID, Assistant assistant) {
        this.assistant = assistant;
        this.id = ID;
        comp = new Comparator<Student>() {
            @Override
            //implicit se compara in ordine alfabetica
            public int compare(Student o1, Student o2) {
                return o1.toString().compareTo(o2.toString());
            }
        };
    }

    public Group(String ID) {
        this.id = ID;
        comp = new Comparator<Student>() {
            @Override
            //implicit se compara in ordine alfabetica
            public int compare(Student o1, Student o2) {
                return o1.toString().compareTo(o2.toString());
            }
        };
    }

    //adaug un student in grupa pe baza functiei de comparator
    @Override
    public boolean add(Student student) {
        super.add(student);
        Collections.sort(this, comp);
        return true;
    }
}
