public class Teacher extends Person {
    String position;
    int group = 0;

    public Teacher(String PIB, String position, int group) {
        super(PIB);
        this.position = position;
        this.group = group;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return PIB + ", посада: " + position + ", група: " + group;
    }
}