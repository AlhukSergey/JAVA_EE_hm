package by.teachmeskills.testExample;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Data
public class Student {
    private int id;
    private String name;
    private int age;
    private Sex sex;
    private static Map<Integer, Student> allStudent;
    private static int countId = 0;

    public Student(String name, int age, Sex sex) {
        if (Optional.ofNullable(allStudent).isEmpty()) {
            allStudent = new HashMap<>();
        }

        this.name = name;
        this.age = age;
        this.sex = sex;

        if (!hasStudent()) {
            countId++;
            this.id = countId;
            allStudent.put(id, this);
        }
    }

    private boolean hasStudent() {
        for (Student student : allStudent.values()) {
            if (student.equals(this) && student.hashCode() == this.hashCode()) {
                return true;
            }
        }
        return false;
    }

    public static List<Student> getAllStudents() {
        return new ArrayList<>(allStudent.values());
    }

    public static List<Student> getAllStudentsBySex(Sex sex) {
        List<Student> studentList = new ArrayList<>();
        for (Student student : allStudent.values()) {
            if (student.sex == sex) {
                studentList.add(student);
            }
        }
        return studentList;
    }

    public static int getStudentsCountWithSex(Sex sex) {
        return getAllStudentsBySex(sex).size();
    }

    public static int getStudentsCount() {
        return allStudent.values().size();
    }

    public static int getSumOfAllStudentsAge() {
        int age = 0;
        for (Student student : allStudent.values()) {
            age += student.age;
        }
        return age;
    }

    public static int getSumOfAllStudentsAgeBySex(Sex sex) {
        int age = 0;
        for (Student student : getAllStudentsBySex(sex)) {
            age += student.getAge();
        }
        return age;
    }

    public static int getAverageAgeOfAllStudents() {
        return getSumOfAllStudentsAge() / getStudentsCount();
    }

    public static int getAverageOfAllStudentsBySex(Sex sex) {
        return getSumOfAllStudentsAgeBySex(sex) / getStudentsCountWithSex(sex);
    }
}
