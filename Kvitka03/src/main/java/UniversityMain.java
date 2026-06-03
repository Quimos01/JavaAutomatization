import java.io.IOException;

public class UniversityMain {
    public static void main(String[] args) throws IOException {
        University university = new University();

        System.out.println("Ця програма дозволяє керувати списками факультетів, кафедр та студентів з викладачами, які до них належать");
        while(true) {
            System.out.println("\nСписок опцій: " +
                    "\n1.Створити/видалити/редагувати факультет" +
                    "\n2.Створити/видалити/редагувати кафедру факультету" +
                    "\n3.Додати/видалити/редагувати студента/викладача до кафедри" +
                    "\n4.Знайти студента/викладача за ПІБ, курсом або групою" +
                    "\n5.Вивести всіх студентів впорядкованих за курсами" +
                    "\n6.Вивести всіх студентів/викладачів факультету впорядкованих за алфавітом" +
                    "\n7.Вивести всіх студентів кафедри впорядкованих за курсами" +
                    "\n8.Вивести всіх студентів/викладачів кафедри впорядкованих за алфавітом" +
                    "\n9.Вивести всіх студентів кафедри вказаного курсу" +
                    "\n10.Вивести всіх студентів кафедри вказаного курсу впорядкованих за алфавітом" +
                    "\n0.Вихід");
            int optionNum = 0;
            optionNum = getOption(optionNum);

            if(optionNum == 0) {
                System.out.println("Програму завершено.");
                break;
            }

            String facName = "";
            switch (optionNum) {
                /* Створити/видалити/редагувати факультет */
                case 1:
                    System.out.println("Що ви хочете зробити з факультетами " +
                            "\n1 - Створити" +
                            "\n2 - Редагувати" +
                            "\n3 - Видалити" +
                            "\n4 - Повернутися до опцій");
                    int case1;
                    case1 = getOption(optionNum);

                    switch(case1) {
                        // Додати факультет
                        case 1:
                            boolean exist = false;
                            facName = getFacName();
                            for(Faculty fac : university.getFaculties()) {
                                if(fac.getFacultyName().equalsIgnoreCase(facName)) {
                                    exist = true;
                                    break;
                                }
                            }
                            if(!exist) {
                                university.addFaculty(facName);
                            } else {
                                System.out.println("Факультет уже існує");
                            }
                            printFacultyList(university.getFaculties());
                            break;
                        // Редагувати факультет
                        case 2:
                            Faculty[] faculties = university.getFaculties();
                            if(faculties.length == 0) {
                                System.out.println("Жодного факультету не знайдено");
                                continue;
                            }
                            System.out.println("Оберіть факультет, що хочете редагувати: ");
                            University.printFaculties(university.getFaculties());
                            int choice;
                            while (true) {
                                try {
                                    choice = DataInput.getInt("Введіть номер: ") - 1;
                                    break;
                                } catch (NumberFormatException e) {
                                    System.out.println("Введіть число");
                                }
                            }
                            if (choice >= 0 && choice < university.getFaculties().length) {
                                System.out.println("Введіть нове ім'я факультету: ");
                                String newName = DataInput.getString();
                                faculties[choice].setFacultyName(newName);
                                System.out.println("Ім'я факультету було змінено на: " + newName);
                            }
                            else
                                System.out.println("Некоректний номер факультету.");

                            printFacultyList(university.getFaculties());
                            break;
                        // Видалити факультет
                        case 3:
                            Faculty[] faculties1 = university.getFaculties();
                            if(faculties1.length == 0) {
                                System.out.println("Жодного факультету не знайдено");
                                continue;
                            }
                            System.out.println("Оберіть факультет, що хочете видалити: ");
                            University.printFaculties(university.getFaculties());
                            int choice1;
                            while (true) {
                                try {
                                    choice1 = DataInput.getInt("Введіть номер: ") - 1;
                                    break;
                                } catch (NumberFormatException e) {
                                    System.out.println("Введіть число!");
                                }
                            }

                            university.removeFaculty(choice1);

                            printFacultyList(university.getFaculties());
                            break;

                        case 4:
                            break;
                        default:
                            System.out.println("Неправильне введення!");
                            break;
                    }
                    break;

                /* Створити/видалити/редагувати кафедру факультету */
                case 2:
                    System.out.println("Що ви хочете зробити з кафедрами " +
                            "\n1 - Створити" +
                            "\n2 - Редагувати" +
                            "\n3 - Видалити" +
                            "\n4 - Повернутися до опцій");
                    int case2 = 0;
                    case2 = getOption(case2);
                    switch(case2) {
                        case 1:
                            university.addDepartment();

                            printFacultyList(university.getFaculties());
                            break;
                        case 2:
                            university.editDepartmentName();
                            break;
                        case 3:
                            university.removeDepartment();
                            break;
                        case 4:
                            break;
                        default:
                            System.out.println("Неправильне введення!");
                            break;
                    }
                    break;

                /* Додати/видалити/редагувати студента/викладача до кафедри */
                case 3:
                    System.out.println("Що ви хочете зробити зі студентами/викладачами" +
                            "\n1 - Додати" +
                            "\n2 - Редагувати" +
                            "\n3 - Видалити"+
                            "\n4 - Повернутися до опцій");
                    int case3 = 0;
                    case3 = getOption(case3);
                    int ch = 0;
                    switch(case3) {
                        case 1:
                            System.out.println("Додати студента чи викладача?" +
                                    "\n1 - Студента" +
                                    "\n2 - Викладача"+
                                    "\n3 - Назад");
                            ch = getOption(ch);
                            switch(ch) {
                                // Додати студента
                                case 1:
                                    university.addStudentToDepartment();
                                    break;
                                // Додати викладача
                                case 2:
                                    university.addTeacherToDepartment();
                                    break;
                                case 3:
                                    break;
                                default:
                                    System.out.println("Неправильне введення!");
                                    break;
                            }
                            break;
                        // Редагувати студента/викладача
                        case 2:
                            System.out.println("Редагувати студента чи викладача?" +
                                    "\n1 - Студента" +
                                    "\n2 - Викладача"+
                                    "\n3 - Назад");
                            ch = getOption(ch);
                            switch(ch) {
                                case 1:
                                    university.transferStudent();
                                    break;
                                case 2:
                                    university.transferTeacher();
                                    break;
                                case 3:
                                    break;
                                default:
                                    System.out.println("Неправильне введення!");
                                    break;
                            }
                            break;
                        // Видалити студента/викладача
                        case 3:
                            System.out.println("Видалити студента чи викладача?" +
                                    "\n1 - Студента" +
                                    "\n2 - Викладача" +
                                    "\n3 - Назад");;
                            ch = getOption(ch);
                            switch(ch) {
                                case 1:
                                    university.removeStudent();
                                    break;

                                case 2:
                                    university.removeTeacher();
                                    break;

                                case 3:
                                    break;
                                default:
                                    System.out.println("Неправильне введення!");
                                    break;
                            }
                            break;

                        case 4:

                            break;
                    }
                    break;
                /* Знайти студента/викладача за ПІБ, курсом або групою */
                case 4:
                    university.findPerson();
                    break;
                /* Вивести всіх студентів впорядкованих за курсами */
                case 5:
                    university.printAllSortedStudents();
                    break;
                /* Вивести всіх студентів/викладачів факультету впорядкованих за алфавітом */
                case 6:
                    System.out.println("Вивести студентів чи викладачів?" +
                            "\n1 - Студентів" +
                            "\n2 - Викладачів"+
                            "\n3 - Назад");
                    int case6 = 0;
                    case6 = getOption(case6);
                    switch(case6) {
                        case 1:
                            university.printStudentByFacSortedByPIB();
                            break;
                        case 2:
                            university.printTeachersByFacSortedByPIB();
                            break;
                        case 3:
                            break;
                        default:
                            System.out.println("Неправильне введення!");
                            break;
                    }
                    break;

                /* Студенти кафедри, відсортовані за курсом */ /* DONE */
                case 7:
                    university.printSortedStudentsByDepartmentByCourse();
                    break;

                /* Студенти та викладачі кафедри, відсортовані за алфавітом */
                case 8:
                    System.out.println("\nВивести студентів чи викладачів?" +
                            "\n1 - Студентів" +
                            "\n2 - Викладачів");
                    int case8 = 0;
                    case8 = getOption(case8);
                    switch(case8) {
                        // Студенти
                        case 1:
                            university.printSortedStudentsByDepartmentByPIB();
                            break;
                        // Викладачі
                        case 2:
                            university.printSortedTeachersByDepartmentByPIB();
                            break;
                    }
                    break;
                /* Вивести всіх студентів кафедри вказаного курсу */
                case 9:
                    university.printStudentsByDepartmentByCertainCourse();
                    break;

                /* Вивести всіх студентів кафедри вказаного курсу за алфавітом*/
                case 10:
                    university.printStudentsByDepartmentByCertainCourseSortedByPIB();
                    break;

                default:
            }
        }
    }




    private static int getOption(int optionNum) {
        int a = 0;
        while(true) {
            try {
                a = DataInput.getInt("\nВиберіть опцію: ");
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введіть число!");
            }
        }
        return a;
    }

    private static String getFacName() {
        String str = "";
        try {
            System.out.print("Введіть назву факультету: ");
            str = DataInput.getString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    private static void printFacultyList(Faculty[] faculty) {
        System.out.println("\nСписок факультетів");
        for(Faculty f : faculty) {
            System.out.println(f);
        }
    }
}