package blog.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Post")
public class Post {
    @Id
    @GeneratedValue
    private int id;
    private String title;
    private String content;
    @Column(name = "time_created")
    private  Date timeCreated;

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String toString() {
        return "id: " + id + " title: " + title + " content: " + content;
    }
}

