package blog.DAO;


import blog.entity.User;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class UserDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    @SuppressWarnings("unchecked")
    public User getByUsername(String username) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class)
                .add(Restrictions.eq("username", username));
        return (criteria.list().size() == 1) ? (User) criteria.list().get(0) : null;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public String getUserRole(String username) {
        String sqlQuery = "select role from user_roles where username=:username";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery)
                .setParameter("username", username);
        return (String)query.list().get(0);
    }
}
