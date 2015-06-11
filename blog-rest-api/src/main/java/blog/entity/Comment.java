package blog.entity;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Comment")
public class Comment {
    @Id
    @GeneratedValue
    private int id;
    @Column(name = "time_created")
    private Date timeCreated;
    private String content;
    @Column(name = "post_id")
    private int postId;
    @Column(name = "parent_id")
    private int parentId;
    private int level;

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
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

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

}

