import java.io.IOException;
import java.util.Arrays;

public class University {
    Faculty[] faculties = new Faculty[0];
    int facultyCount = 0;

    void addFaculty(String name) {
        faculties = Arrays.copyOf(faculties, facultyCount + 1);
        faculties[facultyCount++] = new Faculty(name);
    }

    Student[] allStudents = new Student[0];
    public Student[] getAllStudents() {
        int allStudentsCount = 0;
        for(Faculty f : faculties) {
            if (f.getDepartments() == null) continue;
            for (Department d : f.getDepartments()) {
                if (d.getStudents() == null) continue;
                for (Student s : d.getStudents()) {
                    allStudents = Arrays.copyOf(allStudents, allStudentsCount + 1);
                    allStudents[allStudentsCount++] = s;
                }
            }
        }
        return allStudents;
    }

    public void printSortedStudentsByDepartmentByCourse() {
        Faculty[] faculty = getFaculties();
        if(faculty.length == 0) {
            System.out.println("Жодного факультету не знайдено");
            return;
        }
        System.out.println("\nОберіть факультет:");
        for (int i = 0; i < faculty.length; i++) {
            System.out.println((i + 1) + ". " + faculty[i].getFacultyName());
        }
        int facNum;
        while(true) {
            try {
                facNum = DataInput.getInt("Введіть номер факультету: ") - 1;
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введіть число!");
            }
        }
        if (facNum < 0 || facNum >= faculty.length) {
            System.out.println("Некоректний номер факультету");
            return;
        }
        Faculty selectedFaculty = faculty[facNum];

        Department[] departments = selectedFaculty.getDepartments();
        if(departments.length == 0) {
            System.out.println("Жодної кафедри не знайдено");
            return;
        }
        System.out.println("\nОберіть кафедру:");
        for(int i = 0; i < departments.length; i++) {
            System.out.println((i + 1) + ". " + departments[i].getDepartName());
        }
        int depNum;
        while(true) {
            try {
                depNum = DataInput.getInt("Введіть номер кафедри: ") - 1;
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введіть число!");
            }
        }
        if (depNum < 0 || depNum >= departments.length) {
            System.out.println("Некоректний номер кафедри");
            return;
        }
        Department d = departments[depNum];

        Student[] students = sortStudentsByCourse(d.getStudents());

        if(students.length == 0) {
            System.out.println("Жодного студента не знайдено");
            return;
        }

        System.out.println("\nСтуденти кафедри " + d.getDepartName() + ", відсортовані за курсом");
        for(Student s : students) {
            System.out.println(s);
        }
    }

    public Student[] getAllStudentsByFaculty(Faculty f) {
        if (f == null) {
            System.out.println("Факультет не знайдено");
            return new Student[0];
        }
        if(f.getDepartments() == null) {
            System.out.println("Жодної кафедри не знайдено");
            return new Student[0];
        }
        int allStudentsCount = 0;
        Student[] students = new Student[0];
        for (Department d : f.getDepartments()) {
            if (d.getStudents() == null) continue;
            for (Student s : d.getStudents()) {
                students = Arrays.copyOf(students, allStudentsCount + 1);
                students[allStudentsCount++] = s;
            }
        }
        return students;
    }

    public Student[] sortStudentsByPIB(Student[] students) {
        if (students == null || students.length == 0) {
            return new Student[0];
        }
        int n = students.length;
        for(int i = 0; i < n - 1; i++) {
            for(int j = 0; j < n - i - 1; j++) {
                if(students[j].getPIB().compareToIgnoreCase(students[j + 1].getPIB()) > 0) {
                    Student temp = students[j];
                    students[j] = students[j + 1];
                    students[j + 1] = temp;
                }
            }
        }
        return students;
    }

    public void printStudentByFacSortedByPIB() {
        if(faculties.length == 0) {
            System.out.println("Жодного факультету не знайдено");
            return;
        }

        System.out.println("\nОберіть факультет");
        for (int i = 0; i < faculties.length; i++) {
            System.out.println((i+1) + " - " + faculties[i].getFacultyName());
        }
        int facNum;
        while(true) {
            try {
                facNum = DataInput.getInt("Введіть номер факультету: ") - 1;
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введіть число!");
            }
        }
        Faculty faculty = faculties[facNum];

        Student[] student = getAllStudentsByFaculty(faculty);
        Student[] sortedStudents = sortStudentsByPIB(student);

        if(sortedStudents.length == 0) {
            System.out.println("Жодного студента не знайдено");
            return;
        }

        System.out.println("\nCтуденти факультету \"" + faculty.getFacultyName() + "\" за алфавітом:");
        for(Student s : sortedStudents) {
            System.out.println(s);
        }
    }

    public Teacher[] getAllTeachersByFaculty(Faculty f) {
        if (f == null) {
            System.out.println("Факультет не знайдено");
            return new Teacher[0];
        }
        if(f.getDepartments() == null) {
            System.out.println("Жодної кафедри не знайдено");
            return new Teacher[0];
        }

        int allTeachersCount = 0;
        Teacher[] teachersByFac = new Teacher[0];

        for (Department d : f.getDepartments()) {
            for (Teacher t : d.getTeachers()) {
                teachersByFac = Arrays.copyOf(teachersByFac, allTeachersCount + 1);
                teachersByFac[allTeachersCount++] = t;
            }
        }
        return teachersByFac;
    }

    public Teacher[] sortTeachersByPIB(Teacher[] teachers) {
        int n = teachers.length;
        for(int i = 0; i < n - 1; i++) {
            for(int j = 0; j < n - i - 1; j++) {
                if(teachers[j].getPIB().compareToIgnoreCase(teachers[j + 1].getPIB()) > 0) {
                    Teacher temp = teachers[j];
                    teachers[j] = teachers[j + 1];
                    teachers[j + 1] = temp;
                }
            }
        }
        return teachers;
    }

    public void printTeachersByFacSortedByPIB() {
        if(faculties.length == 0) {
            System.out.println("\nЖодного факультету не знайдено");
            return;
        }

        System.out.println("\nОберіть факультет");
        for (int i = 0; i < faculties.length; i++) {
            System.out.println((i+1) + " - " + faculties[i].getFacultyName());
        }
        int facNum;
        while(true) {
            try {
                facNum = DataInput.getInt("Введіть номер факультету: ") - 1;
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введіть число!");
            }
        }
        Faculty faculty = faculties[facNum];

        Teacher[] teacher = getAllTeachersByFaculty(faculty);
        Teacher[] sortedTeachers = sortTeachersByPIB(teacher);

        if(sortedTeachers.length == 0) {
            System.out.println("Жодного викладача не знайдено");
            return;
        }

        System.out.println("\nВикладачі факультету \"" + faculty.getFacultyName() + "\" за алфавітом:");
        for(Teacher t : sortedTeachers) {
            System.out.println(t);
        }
    }

    // Сортує прийнятий масив студентів за курсом
    public Student[] sortStudentsByCourse(Student[] students) {
        int n = students.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (students[j].getCourse() > students[j + 1].getCourse()) {
                    Student temp = students[j];
                    students[j] = students[j + 1];
                    students[j + 1] = temp;
                }
            }
        }
        return students;
    }

    public void printAllSortedStudents() {
        Student[] sortedStudents = sortStudentsByCourse(getAllStudents());
        if(sortedStudents.length == 0) {
            System.out.println("Жодного студента не знайдено");
            return;
        }

        for(Student s : sortedStudents) {
            System.out.println(s);
        }
    }

    public Faculty[] getFaculties() {
        return faculties;
    }

    public void setFaculties(Faculty[] faculties) {
        this.faculties = faculties;
    }

    public int getFacultyCount() {
        return facultyCount;
    }

    public void setFacultyCount(int facultyCount) {
        this.facultyCount = facultyCount;
    }

    public static void printFaculties(Faculty[] faculties) {
        for (int i = 0; i < faculties.length; i++) {
            System.out.println((i + 1) + ". " + faculties[i].getFacultyName());
        }
    }

    public void addDepartment() throws IOException {
        Faculty[] faculties = getFaculties();
        if (faculties == null || faculties.length == 0) {
            System.out.println("Жодного факультету не знайдено.");
            return;
        }

        System.out.println("\nОберіть факультет:");
        for (int i = 0; i < faculties.length; i++) {
            System.out.println((i + 1) + ". " + faculties[i].getFacultyName());
        }

        int facultyIndex = DataInput.getInt("Введіть номер факультету: ") - 1;
        if (facultyIndex < 0 || facultyIndex >= faculties.length) {
            System.out.println("Некоректний номер факультету.");
            return;
        }

        Faculty selectedFaculty = faculties[facultyIndex];

        System.out.print("\nВведіть назву кафедри: ");
        String depName = DataInput.getString().trim();

        if (depName.isEmpty()) {
            System.out.println("Назва кафедри не може бути порожньою.");
            return;
        }

        selectedFaculty.addDepartment(depName);
        System.out.println("Кафедру успішно додано.");
    }

    public void removeFaculty(int index) {
        if (index < 0 || index >= facultyCount) {
            System.out.println("Некоректний номер факультету.");
            return;
        }

        for (int i = index; i < facultyCount - 1; i++) {
            faculties[i] = faculties[i + 1];
        }

        facultyCount--;
        faculties = Arrays.copyOf(faculties, facultyCount);

        System.out.println("Факультет успішно видалено.");
    }

    public void editDepartmentName() throws IOException {
        if(faculties.length == 0) {
            System.out.println("Жодного факультету не знайдено");
            return;
        }
        System.out.println("Оберіть факультет, у якому хочете редагувати кафедру:");
        printFaculties(faculties);
        int facultyIndex;
        while(true) {
            try {
                facultyIndex = DataInput.getInt("Введіть номер факультету: ") - 1;
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введіть число!");
            }
        }

        if (facultyIndex < 0 || facultyIndex >= facultyCount) {
            System.out.println("Некоректний номер факультету.");
            return;
        }
        Faculty faculty = faculties[facultyIndex];
        Department[] departments = faculty.getDepartments();

        if (departments.length == 0) {
            System.out.println("У цьому факультеті немає кафедр.");
            return;
        }

        System.out.println("Оберіть кафедру, яку хочете редагувати:");
        for (int i = 0; i < departments.length; i++) {
            System.out.println((i + 1) + ". " + departments[i].getDepartName());
        }

        int departmentIndex;
        while(true) {
            try {
                departmentIndex = DataInput.getInt("Введіть номер кафедри: ") - 1;
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введіть число!");
            }
        }

        if (departmentIndex < 0 || departmentIndex >= departments.length) {
            System.out.println("Некоректний номер кафедри.");
            return;
        }

        System.out.println("Введіть нову назву кафедри:");
        String newName = DataInput.getString();
        departments[departmentIndex].setDepartName(newName);

        System.out.println("Назву кафедри змінено на: " + newName);
    }

    public void removeDepartment() {
        if (faculties == null || faculties.length == 0) {
            System.out.println("Жодного факультету не знайдено.");
            return;
        }

        System.out.println("Оберіть факультет, з якого хочете видалити кафедру:");
        printFaculties(faculties);

        int facultyIndex = DataInput.getInt("Введіть номер факультету: ") - 1;
        if (facultyIndex < 0 || facultyIndex >= faculties.length) {
            System.out.println("Некоректний номер факультету.");
            return;
        }

        Faculty selectedFaculty = faculties[facultyIndex];
        Department[] departments = selectedFaculty.getDepartments();

        if (departments == null || departments.length == 0) {
            System.out.println("У цьому факультеті немає кафедр.");
            return;
        }

        System.out.println("Оберіть кафедру, яку хочете видалити:");
        for (int i = 0; i < departments.length; i++) {
            System.out.println((i + 1) + ". " + departments[i].getDepartName());
        }

        int departmentIndex = DataInput.getInt("Введіть номер кафедри: ") - 1;
        if (departmentIndex < 0 || departmentIndex >= departments.length) {
            System.out.println("Некоректний номер кафедри.");
            return;
        }

        selectedFaculty.removeDepartment(departmentIndex);
        System.out.println("Кафедру успішно видалено.");
    }

    public void printSortedStudentsByDepartmentByPIB() {
        if(faculties.length == 0) {
            System.out.println("Жодного факультету не знайдено");
            return;
        }
        System.out.println("\nВиберіть факультет");
        for (int i = 0; i < faculties.length; i++) {
            System.out.println((i+1) + " - " + faculties[i].getFacultyName());
        }
        int facNum;
        while(true) {
            try {
                facNum = DataInput.getInt("Введіть номер факультету: ") - 1;
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введіть число!");
            }
        }
        Faculty selectedFaculty = faculties[facNum];

        Department[] departments = selectedFaculty.getDepartments();
        if(departments.length == 0) {
            System.out.println("Жодної кафедри не знайдено");
            return;
        }
        System.out.println("\nВиберіть кафедру");
        for (int i = 0; i < departments.length; i++) {
            System.out.println((i+1) + " - " + departments[i].getDepartName());
        }
        int depNum;
        while(true) {
            try {
                depNum = DataInput.getInt("Введіть номер кафедри: ") - 1;
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введіть число!");
            }
        }
        Department selectedDepartment = departments[depNum];

        Student[] sortedStudents = sortStudentsByPIB(selectedDepartment.getStudents());

        if(sortedStudents.length == 0) {
            System.out.println("Жодного студента не знайдено");
            return;
        }

        System.out.println("\nCтуденти кафедри \"" + selectedDepartment.getDepartName() + "\" за алфавітом:");
        for(Student s : sortedStudents) {
            System.out.println(s);
        }
    }

    public void printSortedTeachersByDepartmentByPIB() {
        if(faculties.length == 0) {
            System.out.println("Жодного факультету не знайдено");
            return;
        }
        System.out.println("\nВиберіть факультет");
        for (int i = 0; i < faculties.length; i++) {
            System.out.println((i+1) + " - " + faculties[i].getFacultyName());
        }
        int facNum;
        while(true) {
            try {
                facNum = DataInput.getInt("Введіть номер факультету: ") - 1;
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введіть число!");
            }
        }
        Faculty selectedFaculty = faculties[facNum];

        Department[] departments = selectedFaculty.getDepartments();
        if(departments.length == 0) {
            System.out.println("Жодної кафедри не знайдено");
            return;
        }
        System.out.println("\nВиберіть кафедру");
        for (int i = 0; i < departments.length; i++) {
            System.out.println((i+1) + " - " + departments[i].getDepartName());
        }
        int depNum;
        while(true) {
            try {
                depNum = DataInput.getInt("Введіть номер кафедри: ") - 1;
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введіть число!");
            }
        }
        Department selectedDepartment = departments[depNum];

        Teacher[] sortedTeachers = sortTeachersByPIB(selectedDepartment.getTeachers());

        if(sortedTeachers.length == 0) {
            System.out.println("Жодного викладача не знайдено");
            return;
        }

        System.out.println("\nВикладачі кафедри \"" + selectedDepartment.getDepartName() + "\" за алфавітом:");
        for(Teacher t : sortedTeachers) {
            System.out.println(t);
        }
    }

    public void printStudentsByDepartmentByCertainCourse() {
        if(faculties.length == 0) {
            System.out.println("Жодного факультету не знайдено");
            return;
        }
        System.out.println("\nВиберіть факультет");
        for (int i = 0; i < faculties.length; i++) {
            System.out.println((i+1) + " - " + faculties[i].getFacultyName());
        }
        int facNum;
        while(true) {
            try {
                facNum = DataInput.getInt("Введіть номер факультету: ") - 1;
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введіть число!");
            }
        }
        Faculty selectedFaculty = faculties[facNum];

        Department[] departments = selectedFaculty.getDepartments();
        if(departments.length == 0) {
            System.out.println("Жодної кафедри не знайдено");
            return;
        }
        System.out.println("\nВиберіть кафедру");
        for (int i = 0; i < departments.length; i++) {
            System.out.println((i+1) + " - " + departments[i].getDepartName());
        }
        int depNum;
        while(true) {
            try {
                depNum = DataInput.getInt("Введіть номер кафедри: ") - 1;
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введіть число!");
            }
        }
        Department selectedDepartment = departments[depNum];
        if (selectedDepartment == null) {
            System.out.println("Не знайдено жодного студента");
            return;
        }

        int courseNum = 0;
        while(true) {
            courseNum = DataInput.getInt("\nСтудентів якого курсу ви хочете вивести: ");
            if(courseNum >= 1 && courseNum <= 4) {
                break;
            }
            System.out.println("Курс студентів: від 1 до 4");
        }

        Student[] studentsByCourse = new Student[0];

        for(Student s : selectedDepartment.getStudents()) {
            if(courseNum == s.getCourse()) {
                studentsByCourse = Arrays.copyOf(studentsByCourse, studentsByCourse.length + 1);
                studentsByCourse[studentsByCourse.length - 1] = s;
            }
        }

        if(studentsByCourse.length == 0) {
            System.out.println("Жодного студента не знайдено");
            return;
        }

        System.out.println("Студенти кафедри \"" + selectedDepartment.getDepartName() + "\" за алфавітом:");
        for(Student s : studentsByCourse) {
            System.out.println(s);
        }
    }

    public void printStudentsByDepartmentByCertainCourseSortedByPIB() {
        if(faculties.length == 0) {
            System.out.println("Жодного факультету не знайдено");
            return;
        }
        System.out.println("\nВиберіть факультет");
        for (int i = 0; i < faculties.length; i++) {
            System.out.println((i+1) + " - " + faculties[i].getFacultyName());
        }
        int facNum;
        while(true) {
            try {
                facNum = DataInput.getInt("Введіть номер факультету: ") - 1;
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введіть число!");
            }
        }
        Faculty selectedFaculty = faculties[facNum];

        Department[] departments = selectedFaculty.getDepartments();
        if(departments.length == 0) {
            System.out.println("Жодної кафедри не знайдено");
            return;
        }
        System.out.println("\nВиберіть кафедру");
        for (int i = 0; i < departments.length; i++) {
            System.out.println((i+1) + " - " + departments[i].getDepartName());
        }
        int depNum;
        while(true) {
            try {
                depNum = DataInput.getInt("Введіть номер кафедри: ") - 1;
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введіть число!");
            }
        }
        Department selectedDepartment = departments[depNum];
        if (selectedDepartment == null) {
            System.out.println("Не знайдено жодного студента");
            return;
        }

        int courseNum = 0;
        while(true) {
            courseNum = DataInput.getInt("Студентів якого курсу ви хочете вивести: ");
            if(courseNum >= 1 && courseNum <= 4) {
                break;
            }
            System.out.println("Курс студентів: від 1 до 4");
        }

        Student[] studentsByCourse = new Student[0];
        for(Student s : selectedDepartment.getStudents()) {
            if(courseNum == s.getCourse()) {
                studentsByCourse = Arrays.copyOf(studentsByCourse, studentsByCourse.length + 1);
                studentsByCourse[studentsByCourse.length - 1] = s;
            }
        }

        Student[] sortedStudents = sortStudentsByPIB(studentsByCourse);

        if(sortedStudents.length == 0) {
            System.out.println("Жодного студента не знайдено");
            return;
        }

        System.out.println("Студенти кафедри \"" + selectedDepartment.getDepartName() + "\" " + courseNum + " курсу за алфавітом:");
        for(Student s : sortedStudents) {
            System.out.println(s);
        }
    }

    public void transferStudent() {
        System.out.println("З якого факультету потрібно перенести студента?");
        printFaculties(faculties);
        int facultyIndex;
        while(true) {
            try {
                facultyIndex = DataInput.getInt("Введіть номер факультету: ") - 1;
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введіть число!");
            }
        }
        Faculty selectedFaculty = faculties[facultyIndex];

        System.out.println("З якої кафедри потрібно перенести студента?");
        Faculty.printDepartments(selectedFaculty);
        int oldDepartmentIndex;
        while(true) {
            try {
                oldDepartmentIndex = DataInput.getInt("Введіть номер кафедри: ") - 1;
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введіть число!");
            }
        }
        Department oldDepartment = selectedFaculty.getDepartments()[oldDepartmentIndex];

        System.out.println("Якого студента потрібно перенести?");
        Department.printStudents(oldDepartment);
        int studentIndex;
        while(true) {
            try {
                studentIndex = DataInput.getInt("Введіть номер студента: ") - 1;
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введіть число!");
            }
        }
        Student studentToTransfer = oldDepartment.getStudents()[studentIndex];

        System.out.println("На який факультет потрібно перенести студента?");
        printFaculties(faculties);
        int newFacultyIndex;
        while(true) {
            try {
                newFacultyIndex = DataInput.getInt("Введіть номер факультету: ") - 1;
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введіть число!");
            }
        }
        Faculty newFaculty = faculties[newFacultyIndex];

        System.out.println("На яку кафедру потрібно перенести студента?");
        Faculty.printDepartments(newFaculty);
        int newDepartmentIndex;
        while(true) {
            try {
                newDepartmentIndex = DataInput.getInt("Введіть номер кафедри: ") - 1;
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введіть число!");
            }
        }
        Department newDepartment = newFaculty.getDepartments()[newDepartmentIndex];

        newDepartment.addStudent(studentToTransfer.getPIB(), studentToTransfer.getCourse(), studentToTransfer.getGroup());

        Student[] students = oldDepartment.getStudents();
        for (int i = studentIndex; i < students.length - 1; i++) {
            students[i] = students[i + 1];
        }
        oldDepartment.setStudents(Arrays.copyOf(students, students.length - 1));

        System.out.println("Студента успішно перенесено на нову кафедру!");
    }

    public void transferTeacher() {
        System.out.println("З якого факультету потрібно перенести викладача?");
        printFaculties(faculties);
        int facultyIndex;
        while(true) {
            try {
                facultyIndex = DataInput.getInt("Введіть номер факультету: ") - 1;
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введіть число!");
            }
        }
        Faculty selectedFaculty = faculties[facultyIndex];

        System.out.println("З якої кафедри потрібно перенести викладача?");
        Faculty.printDepartments(selectedFaculty);
        int oldDepartmentIndex;
        while(true) {
            try {
                oldDepartmentIndex = DataInput.getInt("Введіть номер кафедри: ") - 1;
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введіть число!");
            }
        }
        Department oldDepartment = selectedFaculty.getDepartments()[oldDepartmentIndex];

        System.out.println("Якого викладача потрібно перенести?");
        Department.printTeachers(oldDepartment);
        int teacherIndex;
        while(true) {
            try {
                teacherIndex = DataInput.getInt("Введіть номер викладача: ") - 1;
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введіть число!");
            }
        }
        Teacher teacherToTransfer = oldDepartment.getTeachers()[teacherIndex];

        System.out.println("На який факультет потрібно перенести викладача?");
        printFaculties(faculties);
        int newFacultyIndex;
        while(true) {
            try {
                newFacultyIndex = DataInput.getInt("Введіть номер факультету: ") - 1;
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введіть число!");
            }
        }
        Faculty newFaculty = faculties[newFacultyIndex];

        System.out.println("На яку кафедру потрібно перенести викладача?");
        Faculty.printDepartments(newFaculty);
        int newDepartmentIndex;
        while(true) {
            try {
                newDepartmentIndex = DataInput.getInt("Введіть номер кафедри: ") - 1;
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введіть число!");
            }
        }
        Department newDepartment = newFaculty.getDepartments()[newDepartmentIndex];

        newDepartment.addTeacher(teacherToTransfer.getPIB(), teacherToTransfer.getPosition(), teacherToTransfer.getGroup());

        Teacher[] teachers = oldDepartment.getTeachers();
        for (int i = teacherIndex; i < teachers.length - 1; i++) {
            teachers[i] = teachers[i + 1];
        }
        oldDepartment.setTeachers(Arrays.copyOf(teachers, teachers.length - 1));

        System.out.println("Викладача успішно перенесено на нову кафедру!");
    }

    public void findPerson() throws IOException {
        boolean found = false;

        System.out.println("1 - ПІБ");
        System.out.println("2 - Курс");
        System.out.println("3 - Група");
        Integer searchChoice;
        while(true) {
            try {
                searchChoice = DataInput.getInt("Як ви хочете знайти студента: ");
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введіть число!");
            }
        }


        String pib = null;
        Integer course = null;
        Integer group = null;

        switch (searchChoice) {
            case 1:
                pib = DataInput.getString().toLowerCase();
                break;
            case 2:
                while(true) {
                    try {
                        course = DataInput.getInt("Введіть курс (1-4): ");
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Введіть число!");
                    }
                }
                if (course < 1 || course > 4) {
                    System.out.println("Невірний курс. Курс має бути від 1 до 4.");
                    return;
                }
                break;
            case 3:
                while(true) {
                    try {
                        group = DataInput.getInt("Введіть номер групи: ");
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Введіть число!");
                    }
                }

                break;
            default:
                System.out.println("Невірний вибір!");
                return;
        }

        System.out.println("\nРезультати пошуку:");

        for (Faculty faculty : faculties) {
            if (faculty.getDepartments() == null) continue;

            for (Department department : faculty.getDepartments()) {
                if (department.getStudents() != null) {

                    for (Student student : department.getStudents()) {
                        if ((pib == null || student.getPIB().toLowerCase().contains(pib)) &&
                                (course == null || student.getCourse() == course) &&
                                (group == null || student.getGroup() == group)) {
                            System.out.println("Студент: " + student);
                            found = true;
                        }
                    }
                }
                if (department.getTeachers() != null) {
                    for (Teacher teacher : department.getTeachers()) {
                        if ((pib == null || teacher.getPIB().toLowerCase().contains(pib)) &&
                                (group == null || teacher.getGroup() == group)) {
                            System.out.println("Викладач: " + teacher);
                            found = true;
                        }
                    }
                }
            }
        }


        if (!found) {
            System.out.println("Нічого не знайдено.");
        }
    }

    public void addStudentToDepartment() throws IOException {
        Faculty[] faculties = getFaculties();
        if (faculties == null || faculties.length == 0) {
            System.out.println("Жодного факультету не знайдено.");
            return;
        }

        System.out.println("\nОберіть факультет:");
        for (int i = 0; i < faculties.length; i++) {
            System.out.println((i + 1) + ". " + faculties[i].getFacultyName());
        }
        int facNum;
        while(true) {
            try {
                facNum = DataInput.getInt("Введіть номер факультету: ") - 1;
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введіть число!");
            }
        }
        if (facNum < 0 || facNum >= faculties.length) {
            System.out.println("Некоректний номер факультету.");
            return;
        }
        Faculty selectedFaculty = faculties[facNum];

        Department[] departments = selectedFaculty.getDepartments();
        if (departments == null || departments.length == 0) {
            System.out.println("Жодної кафедри не знайдено.");
            return;
        }

        System.out.println("\nОберіть кафедру:");
        for (int i = 0; i < departments.length; i++) {
            System.out.println((i + 1) + ". " + departments[i].getDepartName());
        }
        int depNum;
        while(true) {
            try {
                depNum = DataInput.getInt("Введіть номер кафедри: ") - 1;
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введіть число!");
            }
        }
        if (depNum < 0 || depNum >= departments.length) {
            System.out.println("Некоректний номер кафедри.");
            return;
        }
        Department selectedDepartment = departments[depNum];

        System.out.print("\n\nВведіть ПІБ студента: ");
        String PIB = DataInput.getString();

        int course;
        while (true) {
            course = DataInput.getInt("Введіть рік навчання студента: ");
            if (course >= 1 && course <= 4) {
                break;
            }
            System.out.println("Курс студентів: від 1 до 4");
        }
        int group = DataInput.getInt("Введіть групу студента: ");

        selectedDepartment.addStudent(PIB, course, group);

        System.out.println("\nСтуденти, що належать до \"" + selectedDepartment.getDepartName() + "\"");
        for (Student s : selectedDepartment.getStudents()) {
            System.out.println(s);
        }
    }

    public void removeStudent() {
        if (faculties == null || faculties.length == 0) {
            System.out.println("Жодного факультету не знайдено.");
            return;
        }

        System.out.println("Оберіть факультет, у якому знаходиться студент:");
        printFaculties(faculties);

        int facultyIndex = DataInput.getInt("Введіть номер факультету: ") - 1;
        if (facultyIndex < 0 || facultyIndex >= faculties.length) {
            System.out.println("Некоректний номер факультету.");
            return;
        }
        Faculty selectedFaculty = faculties[facultyIndex];

        Department[] departments = selectedFaculty.getDepartments();
        if (departments == null || departments.length == 0) {
            System.out.println("У цьому факультеті немає кафедр.");
            return;
        }

        System.out.println("Оберіть кафедру, з якої хочете видалити студента:");
        Faculty.printDepartments(selectedFaculty);

        int departmentIndex = DataInput.getInt("Введіть номер кафедри: ") - 1;
        if (departmentIndex < 0 || departmentIndex >= departments.length) {
            System.out.println("Некоректний номер кафедри.");
            return;
        }
        Department selectedDepartment = departments[departmentIndex];

        Student[] students = selectedDepartment.getStudents();
        if (students == null || students.length == 0) {
            System.out.println("У цій кафедрі немає студентів.");
            return;
        }

        System.out.println("Оберіть студента для видалення:");
        Department.printStudents(selectedDepartment);

        int studentIndex = DataInput.getInt("Введіть номер студента: ") - 1;
        if (studentIndex < 0 || studentIndex >= students.length) {
            System.out.println("Некоректний номер студента.");
            return;
        }

        selectedDepartment.removeStudent(studentIndex);
        System.out.println("Студента успішно видалено.");
    }

    public void removeTeacher() {
        if (faculties == null || faculties.length == 0) {
            System.out.println("Жодного факультету не знайдено.");
            return;
        }

        System.out.println("Оберіть факультет, у якому знаходиться викладач:");
        printFaculties(faculties);

        int facultyIndex = DataInput.getInt("Введіть номер факультету: ") - 1;
        if (facultyIndex < 0 || facultyIndex >= faculties.length) {
            System.out.println("Некоректний номер факультету.");
            return;
        }
        Faculty selectedFaculty = faculties[facultyIndex];

        Department[] departments = selectedFaculty.getDepartments();
        if (departments == null || departments.length == 0) {
            System.out.println("Жодної кафедри не знайдено.");
            return;
        }

        System.out.println("Оберіть кафедру, з якої хочете видалити викладача:");
        Faculty.printDepartments(selectedFaculty);

        int departmentIndex = DataInput.getInt("Введіть номер кафедри: ") - 1;
        if (departmentIndex < 0 || departmentIndex >= departments.length) {
            System.out.println("Некоректний номер кафедри.");
            return;
        }
        Department selectedDepartment = departments[departmentIndex];

        Teacher[] teachers = selectedDepartment.getTeachers();
        if (teachers == null || teachers.length == 0) {
            System.out.println("У цій кафедрі немає викладачів.");
            return;
        }

        System.out.println("Оберіть викладача для видалення:");
        Department.printTeachers(selectedDepartment);

        int teacherIndex = DataInput.getInt("Введіть номер викладача: ") - 1;
        if (teacherIndex < 0 || teacherIndex >= teachers.length) {
            System.out.println("Некоректний номер викладача.");
            return;
        }

        selectedDepartment.removeTeacher(teacherIndex);
        System.out.println("Викладача успішно видалено.");
    }

    public void addTeacherToDepartment() throws IOException {
        Faculty[] faculties = getFaculties();
        if (faculties == null || faculties.length == 0) {
            System.out.println("Жодного факультету не знайдено.");
            return;
        }

        System.out.println("\nОберіть факультет:");
        for (int i = 0; i < faculties.length; i++) {
            System.out.println((i + 1) + ". " + faculties[i].getFacultyName());
        }
        int facNum;
        while(true) {
            try {
                facNum = DataInput.getInt("Введіть номер факультету: ") - 1;
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введіть число!");
            }
        }
        if (facNum < 0 || facNum >= faculties.length) {
            System.out.println("Некоректний номер факультету.");
            return;
        }
        Faculty selectedFaculty = faculties[facNum];

        Department[] departments = selectedFaculty.getDepartments();
        if (departments == null || departments.length == 0) {
            System.out.println("Жодної кафедри не знайдено.");
            return;
        }

        System.out.println("\nОберіть кафедру:");
        for (int i = 0; i < departments.length; i++) {
            System.out.println((i + 1) + ". " + departments[i].getDepartName());
        }
        int depNum;
        while(true) {
            try {
                depNum = DataInput.getInt("Введіть номер кафедри: ") - 1;
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введіть число!");
            }
        }
        if (depNum < 0 || depNum >= departments.length) {
            System.out.println("Некоректний номер кафедри.");
            return;
        }
        Department selectedDepartment = departments[depNum];

        System.out.print("\n\nВведіть ПІБ викладача: ");
        String PIB = DataInput.getString();
        System.out.println("Введіть посаду викладача: ");
        String position = DataInput.getString();
        int group = DataInput.getInt("Введіть групу викладача: ");

        selectedDepartment.addTeacher(PIB, position, group);

        System.out.println("\nВикладачі, що належать до \"" + selectedDepartment.getDepartName() + "\"");
        for (Teacher t : selectedDepartment.getTeachers()) {
            System.out.println(t);
        }
    }
}