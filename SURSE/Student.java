public class Student extends User{
        private Parent mother, father;

    public Student(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public Parent getFather() {
        return father;
    }

    public Parent getMother() {
        return mother;
    }

    public void setMother(Parent mother) { /* TODO */
        this.mother = mother;
    }
    public void setFather(Parent father) { /* TODO */
        this.father = father;
    }
}
