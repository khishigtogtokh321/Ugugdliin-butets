package code;


public class Lessons {
    private Subject learned;
    private int score;

    public Lessons(Subject learned, int score) {
        this.learned = learned;
        this.score = score;
    }

    public Subject getLearned() { return learned; }
    public int getScore() { return score; }

    public boolean isF() { return score < 60; }

    @Override
    public String toString() {
        return learned.getSubjectCode() + " : " + score;
    }
}
