package blog.DAO;

import blog.entity.Post;
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
public class PostDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    @SuppressWarnings("unchecked")
    public List<Post> findAll() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Post.class).addOrder(Order.desc("timeCreated"));
        List<Post> posts = criteria.list();
        return posts;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public Post getById(Integer id) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Post.class)
                .add(Restrictions.eq("id", id));
        return (criteria.list().size() >= 1) ? (Post) criteria.list().get(0) : null;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public void save(Post post) {
        if (post != null) {
            post.setTimeCreated(new Date());
        }
        sessionFactory.getCurrentSession().save(post);
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public Post delete(Integer id) {
        Post post = getById(id);
        if (post == null) {
            return null;
        }
        else {
            sessionFactory.getCurrentSession().delete(post);
        }
        return post;
    }

    @Transactional
    public void update(Post updatedPost) {
        Post oldPost = getById(updatedPost.getId());
        oldPost.setTitle(updatedPost.getTitle());
        oldPost.setContent(updatedPost.getContent());
        sessionFactory.getCurrentSession().update(oldPost);
    }

}
