class UserFactory {
    public User getUser(String userType, String firstName, String lastName) {
        switch (userType) {
            case "Assistant":
                return new Assistant(firstName, lastName);
            case "Parent":
                return new Parent(firstName, lastName);
            case "Student":
                return new Student(firstName, lastName);
            case "Teacher":
                return new Teacher(firstName, lastName);
        }
        throw new IllegalArgumentException("The user type " + userType + " is not recognized.");
    }
}