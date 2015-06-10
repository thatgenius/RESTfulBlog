package blog.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "Comment")
public class Comment {
    private static int currentPosition = 0;
    @Id
    @GeneratedValue
    private int id;
    private Date time_created;
    private String content;
    private int post_id;
    private int parent_id;
    private int level;

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Date getTime_created() {
        return time_created;
    }

    public void setTime_created(Date time_created) {
        this.time_created = time_created;
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

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public static Comment[] orderComments(Comment[] allPostComments) {
        Comment[] result = new Comment[allPostComments.length];
        currentPosition = 0;
        for (Comment comment : allPostComments) {
            if (comment.getLevel() == 1) {
                result[currentPosition++] = comment;
                addChildrenComments(result, comment, allPostComments);
            }
        }
        return result;
    }

    private static void addChildrenComments(Comment[] result, Comment parentComment, Comment[] allPostComments) {
        for (Comment current : allPostComments) {
            if (current.getParent_id() == parentComment.getId()) {
                result[currentPosition++] = current;
                addChildrenComments(result, current, allPostComments);
            }
        }
    }

}



