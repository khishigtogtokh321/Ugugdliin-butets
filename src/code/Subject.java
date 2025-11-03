package code;

public class Subject {
    private String subjectCode;
    private String subjectName;
    private float credit;

    public Subject(String subjectCode, String subjectName, float credit) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.credit = credit;
    }

    public String getSubjectCode() { return subjectCode; }
    public String getSubjectName() { return subjectName; }
    public float getCredit() { return credit; }

    @Override
    public String toString() {
        return subjectCode + " - " + subjectName + " (" + credit + " credit)";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Subject other = (Subject) obj;
        return subjectCode.equals(other.subjectCode);
    }
}
