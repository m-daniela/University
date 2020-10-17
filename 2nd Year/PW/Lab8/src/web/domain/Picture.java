package web.domain;

public class Picture {
    private int id;
    private String path;
    private int userId;
    private int score;

    public Picture(int id, String path, int userId, int score) {
        this.id = id;
        this.path = path;
        this.userId = userId;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
