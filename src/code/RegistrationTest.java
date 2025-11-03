package code;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.io.*;

public class RegistrationTest {

    Registration reg;

    @BeforeEach
    void setUp() {
        reg = new Registration();
    }

    @Test
    void testLoadSubjects() throws IOException {
        File temp = File.createTempFile("subjects", ".txt");
        try (PrintWriter out = new PrintWriter(temp)) {
            out.println("CS101/Програмчлалын үндэс/3");
            out.println("MA102/Математик/4");
        }

        reg.loadSubjects(temp.getAbsolutePath());
        assertEquals(2, reg.getSubjectList().size());
        Subject s1 = (Subject) reg.getSubjectList().get(0);
        assertEquals("CS101", s1.getSubjectCode());
        assertEquals("Програмчлалын үндэс", s1.getSubjectName());
        assertEquals(3.0f, s1.getCredit());
    }

    @Test
    void testLoadMajors() throws IOException {
        File temp = File.createTempFile("majors", ".txt");
        try (PrintWriter out = new PrintWriter(temp)) {
            out.println("AI/Хиймэл оюун ухаан");
            out.println("CS/Програм хангамж");
        }

        reg.loadMajors(temp.getAbsolutePath());

        assertEquals(2, reg.getMajorList().size());
        Major m = (Major) reg.getMajorList().get(0);
        assertEquals("AI", m.getMajorCode());
        assertEquals("Хиймэл оюун ухаан", m.getMajorCode());
    }

    @Test
    void testLoadExamsAndGPA() throws IOException {
        File sub = File.createTempFile("subjects", ".txt");
        try (PrintWriter out = new PrintWriter(sub)) {
            out.println("CS101/Програмчлал/3");
            out.println("MA102/Математик/4");
        }

        File maj = File.createTempFile("majors", ".txt");
        try (PrintWriter out = new PrintWriter(maj)) {
            out.println("AI/Хиймэл оюун ухаан");
        }

        File ex = File.createTempFile("exams", ".txt");
        try (PrintWriter out = new PrintWriter(ex)) {
            out.println("ST001/CS101/90/AI");
            out.println("ST001/MA102/80");
            out.println("ST002/CS101/45/AI");
        }

        reg.loadSubjects(sub.getAbsolutePath());
        reg.loadMajors(maj.getAbsolutePath());
        reg.loadExams(ex.getAbsolutePath());

        // ST001 = 90,80 → A,B → голч ~3.5
        Student s1 = (Student) reg.getStudentList().get(0);
        assertTrue(s1.getGPA() >= 3.0 && s1.getGPA() <= 4.0);

        assertNotNull(s1.getMajor());
        assertEquals("AI", s1.getMajor().getMajorCode());
    }

    @Test
    void testFindStudentWithFs() {
        Student st = new Student("ST001");
        Subject s = new Subject("CS101", "Програмчлал", 3);
        st.addLesson(new Lessons(s, 45)); // F
        st.addLesson(new Lessons(s, 40)); // F
        st.addLesson(new Lessons(s, 35)); // F
        reg.getStudentList().add(0, st);

        // Test
        assertEquals(3, st.countFs());
    }
}
