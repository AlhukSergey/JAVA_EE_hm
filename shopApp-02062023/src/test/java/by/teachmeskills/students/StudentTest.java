package by.teachmeskills.students;

import by.teachmeskills.testExample.Sex;
import by.teachmeskills.testExample.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StudentTest {
    private static Student student1;
    private static Student student2;
    private static Student student3;
    private static Student student4;
    private static Student student5;
    private static Student student6;
    private static List<Student> actual;
    private static List<Student> actualBySex_Male;
    private static int actualStudentCount_Female;
    private static int actualSumAgeOfAllStudent;
    private static int actualAverageAge;
    private static int actualAverageAgeBySex_Female;

    @BeforeAll
    public static void setUp() {
        student1 = new Student("Ivan", 17, Sex.MALE);
        student2 = new Student("Anna", 19, Sex.FEMALE);
        student3 = new Student("Polina", 18, Sex.FEMALE);
        student4 = new Student("Anna", 20, Sex.FEMALE);
        student5 = new Student("Andrey", 21, Sex.MALE);
        student6 = new Student("John", 18, Sex.MALE);

        actual = new ArrayList<>();
        actual.add(student1);
        actual.add(student2);
        actual.add(student3);
        actual.add(student4);
        actual.add(student5);
        actual.add(student6);

        actualBySex_Male = actual.stream().filter(student -> student.getSex().equals(Sex.MALE)).collect(Collectors.toList());

        actualStudentCount_Female = (int) actual.stream().filter(student -> student.getSex().equals(Sex.FEMALE)).count();

        actualSumAgeOfAllStudent = actual.stream().mapToInt(Student::getAge).sum();

        actualAverageAge = (int) actual.stream().mapToInt(Student::getAge).average().orElse(0);

        actualAverageAgeBySex_Female = (int) actual.stream().filter(student -> student.getSex().equals(Sex.FEMALE)).mapToInt(Student::getAge).average().orElse(0);
    }

    @Test
    public void checkAllStudentsReturned() {
        List<Student> expected = Student.getAllStudents();
        assertEquals(expected, actual);
    }

    @Test
    public void checkAllStudentsReturned_NotNull() {
        List<Student> expected = Student.getAllStudents();
        assertNotNull(expected);
    }

    @Test
    public void getAllUsers_MALE() {
        List<Student> expected = Student.getAllStudentsBySex(Sex.MALE);
        assertEquals(expected, actualBySex_Male);
    }

    @Test
    public void checkStudentsCount_Female() {
        assertEquals(actualStudentCount_Female, Student.getStudentsCountWithSex(Sex.FEMALE));
    }

    @Test
    public void checkStudentsCount() {
        int expectedCount = actual.size();
        assertEquals(expectedCount, Student.getStudentsCount());
    }

    @Test
    public void checkSumOfAllStudents() {
        assertEquals(actualSumAgeOfAllStudent, Student.getSumOfAllStudentsAge());
    }

    @Test
    public void checkSumOfAllStudentAgeBySex_Male() {
        int expectedSum = actual.stream().filter(student -> student.getSex().equals(Sex.MALE)).mapToInt(Student::getAge).sum();
        assertEquals(expectedSum, Student.getSumOfAllStudentsAgeBySex(Sex.MALE));
    }

    @Test
    public void checkSumOfAllStudentAgeBySex_Female() {
        int expectedSum = actual.stream().filter(student -> student.getSex().equals(Sex.FEMALE)).mapToInt(Student::getAge).sum();
        assertEquals(expectedSum, Student.getSumOfAllStudentsAgeBySex(Sex.FEMALE));
    }

    @Test
    public void checkAverageOfAllStudentsAge() {
        assertEquals(actualAverageAge, Student.getAverageAgeOfAllStudents());
    }

    @Test
    public void checkAverageOfAllStudentsAgeBySex_Female() {
        assertEquals(actualAverageAgeBySex_Female, Student.getAverageOfAllStudentsBySex(Sex.FEMALE));
    }
}

