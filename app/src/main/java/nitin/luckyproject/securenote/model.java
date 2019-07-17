package nitin.luckyproject.securenote;

public class model {

    String title;
    String note;
    String color;
    String id;
    String timestamp;

    public model() {
    }

    public model(String title, String note, String color,String id,String timestamp) {
        this.title = title;
        this.note = note;
        this.color = color;
        this.id =id;
        this.timestamp =timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
