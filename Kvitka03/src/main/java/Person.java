abstract class Person {
    protected String PIB;

    public Person(String PIB) {
        this.PIB = PIB;
    }

    public String getPIB() {
        return PIB;
    }

    public String toString() {
        return PIB;
    }
}
