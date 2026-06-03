public class Student extends Person {
    int course;
    int group;

    public Student(String PIB, int course, int group) {
        super(PIB);
        this.course = course;
        this.group = group;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return PIB + ", курс: " + course + ", група: " + group;
    }
}

