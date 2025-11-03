package code;

public class Major {
    private String majorCode;
    private String majorName;

    public Major(String majorCode, String majorName) {
        this.majorCode = majorCode;
        this.majorName = majorName;
    }

    public String getMajorCode() { return majorCode; }

    @Override
    public String toString() {
        return majorCode + " - " + majorName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Major other = (Major) obj;
        return majorCode.equals(other.majorCode);
    }
}
