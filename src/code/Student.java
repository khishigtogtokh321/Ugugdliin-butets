package code;
import dataStructures.Chain;



public class Student {
    private String studentCode;
    private Major major;
    private float GPA;
    private Chain lessons;

    public Student(String studentCode) {
        this.studentCode = studentCode;
        this.GPA = 0;
        this.lessons = new Chain();
    }

    public void setMajor(Major major) { this.major = major; }
    public Major getMajor() { return major; }

    public void addLesson(Lessons lesson) {
        lessons.add(lessons.size(), lesson);
        calculateGPA();
    }

    private void calculateGPA() {
        if (lessons.size() == 0) { GPA = 0; return; }
        float totalCredit = 0;
        float totalScore = 0;
        for (int i = 0; i < lessons.size(); i++) {
            Lessons l = (Lessons) lessons.get(i);
            float credit = l.getLearned().getCredit();
            totalCredit += credit;
            totalScore += convertScoreToGPA(l.getScore()) * credit;
        }
        GPA = totalCredit == 0 ? 0 : totalScore / totalCredit;
    }

    private float convertScoreToGPA(int score) {
        if (score >= 90) return 4.0f;
        if (score >= 80) return 3.0f;
        if (score >= 70) return 2.0f;
        if (score >= 60) return 1.0f;
        return 0.0f;
    }

    public String getStudentCode() { return studentCode; }
    public float getGPA() { return GPA; }
    public Chain getLessons() { return lessons; }

    public int countFs() {
        int count = 0;
        for (int i = 0; i < lessons.size(); i++) {
            Lessons l = (Lessons) lessons.get(i);
            if (l.isF()) count++;
        }
        return count;
    }

    @Override
    public String toString() {
        return studentCode + (major != null ? " (" + major + ")" : "") + 
               " - Golch dun: " + String.format("%.2f", GPA);
    }
}




