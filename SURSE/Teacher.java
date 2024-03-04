public class Teacher extends User implements Element{
    private Strategy strategy;
    public Teacher(String firstName, String lastName) {
        super(firstName, lastName);
    }
    public void setStrategy(int strategy)
    {
        switch(strategy) {

            case 0:
                this.strategy = new BestPartialScore();
            case 1:
                this.strategy = new BestExamScore();
            case 2:
                this.strategy = new BestTotalScore();
        }
    }
    public Strategy getStrategy()
    {
        return strategy;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
