package code;

import java.util.Scanner;
import java.io.*;
import dataStructures.*;

public class Registration {
    private Chain studentList;
    private Chain subjectList;
    private ArrayLinearList majorList;

    public Registration() {
        studentList = new Chain();
        subjectList = new Chain();
        majorList = new ArrayLinearList();
    }
    public Chain getStudentList() {
        return studentList;
    }

    public Chain getSubjectList() {
        return subjectList;
    }

    public ArrayLinearList getMajorList() {
        return majorList;
    }

    public void loadSubjects(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split("/");
                subjectList.add(subjectList.size(),
                    new Subject(values[0], values[1], Float.parseFloat(values[2])));
            }
        } catch (Exception e) {
            System.out.println("Алдаа: " + e.getMessage());
        }
    }

    public void loadMajors(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split("/");
                majorList.add(majorList.size(), new Major(values[0], values[1]));
            }
        } catch (Exception e) {
            System.out.println("Алдаа: " + e.getMessage());
        }
    }

    public void loadExams(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split("/");
                String studentCode = values[0];
                String subjectCode = values[1];
                int score = Integer.parseInt(values[2]);
                String majorCode = values.length > 3 ? values[3] : null;

                Subject subj = findSubjectByCode(subjectCode);
                if (subj == null) continue;

                Student student = findOrCreateStudent(studentCode);
                student.addLesson(new Lessons(subj, score));

                if (majorCode != null && student.getMajor() == null) {
                    Major m = findMajorByCode(majorCode);
                    if (m != null) student.setMajor(m);
                }
            }
        } catch (Exception e) {
            System.out.println("Алдаа: " + e.getMessage());
        }
    }

    private Subject findSubjectByCode(String code) {
        for (int i = 0; i < subjectList.size(); i++) {
            Subject s = (Subject) subjectList.get(i);
            if (s.getSubjectCode().equals(code)) return s;
        }
        return null;
    }

    private Student findOrCreateStudent(String code) {
        for (int i = 0; i < studentList.size(); i++) {
            Student s = (Student) studentList.get(i);
            if (s.getStudentCode().equals(code)) return s;
        }
        Student newStudent = new Student(code);
        studentList.add(studentList.size(), newStudent);
        return newStudent;
    }

    private Major findMajorByCode(String code) {
        for (int i = 0; i < majorList.size(); i++) {
            Major m = (Major) majorList.get(i);
            if (m.getMajorCode().equals(code)) return m;
        }
        return null;
    }

    public void printAllSubjects() {
        System.out.println("==== Нийт хичээлүүд ====");
        for (int i = 0; i < subjectList.size(); i++)
            System.out.println(subjectList.get(i));
    }

    public void printAllMajors() {
        System.out.println("==== Нийт мэргэжлүүд ====");
        for (int i = 0; i < majorList.size(); i++)
            System.out.println(majorList.get(i));
    }

    public void printAverageGPA() {
        if (studentList.size() == 0) return;

        float totalGPA = 0;
        for (int i = 0; i < studentList.size(); i++) {
            Student s = (Student) studentList.get(i);
            System.out.printf("%s - Голч дүн: %.2f\n", s.getStudentCode(), s.getGPA());
            totalGPA += s.getGPA();
        }
        System.out.printf("Нийт оюутнуудын дундаж голч дүн: %.2f\n", totalGPA / studentList.size());
    }

    public void printStudentsWithMoreThan3Fs() {
        System.out.println("==== 3-аас дээш F авсан оюутнууд ====");
        for (int i = 0; i < studentList.size(); i++) {
            Student s = (Student) studentList.get(i);
            if (s.countFs() >= 3)
                System.out.println(s);
        }
    }
    
    public void printScoresBySubject() {
        System.out.println("==== Хичээл бүрийн оюутнуудын дүн ====");
        // subject бүрийг нэг нэгээр авч үзнэ
        for (int i = 0; i < subjectList.size(); i++) {
            Subject subj = (Subject) subjectList.get(i);
            System.out.println("\n📘 " + subj.getSubjectCode() + " - " + subj.getSubjectName());

            boolean found = false;
            // оюутан бүрийг шалгах
            for (int j = 0; j < studentList.size(); j++) {
                Student stu = (Student) studentList.get(j);
                // тухайн оюутны үзсэн хичээлүүдийг шалгах
                Chain lessons = stu.getLessons();
                for (int k = 0; k < lessons.size(); k++) {
                    Lessons l = (Lessons) lessons.get(k);
                    if (l.getLearned().equals(subj)) {
                        System.out.printf("   %s → %d оноо\n", stu.getStudentCode(), l.getScore());
                        found = true;
                    }
                }
            }
            if (!found) {
                System.out.println("   ⚠️ Энэ хичээлийг үзсэн оюутан алга.");
            }
        }
    }
    
    public void printScoresByMajor() {
        System.out.println("==== Мэргэжил бүрийн оюутнуудын дүн ====");
        // major бүрийг шалгах
        for (int i = 0; i < majorList.size(); i++) {
            Major m = (Major) majorList.get(i);
            System.out.println("\n🏫 " + m);

            boolean found = false;
            // оюутан бүрийг шалгах
            for (int j = 0; j < studentList.size(); j++) {
                Student s = (Student) studentList.get(j);
                if (s.getMajor() != null && s.getMajor().equals(m)) {
                    System.out.printf("   %s → Голч дүн: %.2f\n", s.getStudentCode(), s.getGPA());
                    found = true;
                }
            }
            if (!found) {
                System.out.println("   ⚠️ Энэ мэргэжлээр оюутан бүртгэгдээгүй байна.");
            }
        }
    }


    // ---------- Main ----------
    public static void main(String[] args) {
        Registration reg = new Registration();
        reg.loadSubjects("subjects.txt");
        reg.loadMajors("Professions.txt");
        reg.loadExams("Exams.txt");

        @SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n===== Цэс =====");
            System.out.println("1. Хичээлүүдийн жагсаалт");
            System.out.println("2. Мэргэжлийн жагсаалт");
            System.out.println("3. Нийт оюутнуудын дундаж голч дүн");
            System.out.println("4. 3-аас дээш F авсан оюутнууд");
            System.out.println("5. Хичээл бүрээр оюутнуудын дүн");
            System.out.println("6. Мэргэжил бүрээр оюутнуудын дүн");
            System.out.println("0. Гарах");
            System.out.print("Сонголт: ");

            int ch = sc.nextInt();
            switch (ch) {
                case 1 -> reg.printAllSubjects();
                case 2 -> reg.printAllMajors();
                case 3 -> reg.printAverageGPA();
                case 4 -> reg.printStudentsWithMoreThan3Fs();
                case 5 -> reg.printScoresBySubject();   
                case 6 -> reg.printScoresByMajor();   
                case 0 -> { System.out.println("Програм дууслаа."); return; }
                default -> System.out.println("Буруу сонголт.");
            }
        }
    }
}
