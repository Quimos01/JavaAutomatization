import java.util.Arrays;

public class Faculty {
    String facultyName;
    Department[] departments = new Department[0];

    public Faculty(String facultyName) {
        this.facultyName = facultyName;
    }

    public void addDepartment(String name) {
        if (departments == null) {
            departments = new Department[0];
        }
        departments = Arrays.copyOf(departments, departments.length + 1);
        departments[departments.length - 1] = new Department(name);
    }

    public static void printDepartments(Faculty faculty) {
        Department[] departments = faculty.getDepartments();
        for (int i = 0; i < departments.length; i++) {
            System.out.println((i + 1) + ". " + departments[i].getDepartName());
        }
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public Department[] getDepartments() {
        return departments;
    }

    public void setDepartments(Department[] departments) {
        this.departments = departments;
    }

    @Override
    public String toString() {
        return facultyName + "\nКафедри факультету" + Arrays.toString(getDepartments());
    }

    public void removeDepartment(int departmentIndex) {
        if (departments == null || departments.length == 0) {
            System.out.println("Немає кафедр для видалення.");
            return;
        }

        if (departmentIndex < 0 || departmentIndex >= departments.length) {
            System.out.println("Некоректний індекс кафедри.");
            return;
        }

        Department[] newDepartments = new Department[departments.length - 1];
        int newIndex = 0;
        for (int i = 0; i < departments.length; i++) {
            if (i != departmentIndex) {
                newDepartments[newIndex++] = departments[i];
            }
        }

        departments = newDepartments;
    }

}
