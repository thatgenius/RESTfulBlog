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
    private Date timeCreated;
    private String content;
    private int postId;
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
            if (current.getParentId() == parentComment.getId()) {
                result[currentPosition++] = current;
                addChildrenComments(result, current, allPostComments);
            }
        }
    }

}



