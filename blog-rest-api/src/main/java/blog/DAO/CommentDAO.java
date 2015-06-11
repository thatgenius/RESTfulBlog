package blog.DAO;

import blog.entity.Comment;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public class CommentDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    @SuppressWarnings("unchecked")
    public List<Comment> findAllByPostId(int postId) {

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Comment.class)
                .add(Restrictions.eq("postId", postId)).addOrder(Order.desc("timeCreated"));
        List<Comment> comments = criteria.list();
        return comments;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public void save(Comment comment) {
        if (comment != null) {
            comment.setTimeCreated(new Date());
        }
        sessionFactory.getCurrentSession().save(comment);
    }

}
