import java.util.Arrays;

public class Department {
    private String departName;
    Student[] students = new Student[0];
    Teacher[] teachers = new Teacher[0];

    public Department(String departName) {
        this.departName = departName;
        this.students = students;
        this.teachers = teachers;
    }

    public void addStudent(String PIB, int course, int group) {
        if (students == null) {
            students = new Student[0];
        }
        students = Arrays.copyOf(students, students.length + 1);
        students[students.length - 1] = new Student(PIB, course, group);
    }

    public void addTeacher(String PIB, String position, int group) {
        if (teachers == null) {
            teachers = new Teacher[0];
        }
        teachers = Arrays.copyOf(teachers, teachers.length + 1);
        teachers[teachers.length - 1] = new Teacher(PIB, position, group);
    }

    public static void printStudents(Department department) {
        Student[] students = department.getStudents();
        if (students.length == 0) {
            System.out.println("Студенти відсутні.");
            return;
        }
        for (int i = 0; i < students.length; i++) {
            System.out.println((i + 1) + ". " + students[i].getPIB() + " (Курс: " + students[i].getCourse() + ")");
        }
    }
    public static void printTeachers(Department department) {
        Teacher[] teachers = department.getTeachers();
        if (teachers.length == 0) {
            System.out.println("Викладачі відсутні.");
            return;
        }
        for (int i = 0; i < teachers.length; i++) {
            System.out.println((i + 1) + ". " + teachers[i].getPIB() + " (Посада: " + teachers[i].getPosition() + ")");
        }
    }



    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public Student[] getStudents() {
        return students;
    }

    public void setStudents(Student[] students) {
        this.students = students;
    }

    public Teacher[] getTeachers() {
        return teachers;
    }

    public void setTeachers(Teacher[] teachers) {
        this.teachers = teachers;
    }

    @Override
    public String toString() {
        return departName;
    }


    public void removeStudent(int studentIndex) {
        if (students == null || students.length == 0) {
            System.out.println("Немає студентів для видалення.");
            return;
        }

        if (studentIndex < 0 || studentIndex >= students.length) {
            System.out.println("Некоректний індекс студента.");
            return;
        }

        if (students.length == 1) {
            students = new Student[0];
        } else {
            Student[] newStudents = new Student[students.length - 1];
            int newIndex = 0;
            for (int i = 0; i < students.length; i++) {
                if (i != studentIndex) {
                    newStudents[newIndex++] = students[i];
                }
            }
            students = newStudents;
        }
    }
    public void removeTeacher(int teacherIndex) {
        if (teachers == null || teachers.length == 0) {
            System.out.println("Немає викладачів для видалення.");
            return;
        }

        if (teacherIndex < 0 || teacherIndex >= teachers.length) {
            System.out.println("Некоректний індекс викладача.");
            return;
        }

        if (teachers.length == 1) {
            teachers = new Teacher[0];
        } else {
            Teacher[] newTeachers = new Teacher[teachers.length - 1];
            int newIndex = 0;
            for (int i = 0; i < teachers.length; i++) {
                if (i != teacherIndex) {
                    newTeachers[newIndex++] = teachers[i];
                }
            }
            teachers = newTeachers;
        }
    }

}




