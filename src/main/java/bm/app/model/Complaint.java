package bm.app.model;

public class Complaint {

    private int id;
    private String type;
    private String content;

    public Complaint(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Complaint{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
