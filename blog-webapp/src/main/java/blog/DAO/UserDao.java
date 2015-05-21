package blog.DAO;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public void createNewUser(String username, String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);

        String user_role = "ROLE_USER";
        String sqlQuery1 = "insert into user (username, password, enabled) values (:username, :password, true) ";
        String sqlQuery2 = "insert into user_roles (username, role) values (:username, :role)";

        Query query1 = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery1)
                .setParameter("username", username)
                .setParameter("password", hashedPassword);
        query1.executeUpdate();

        Query query2 = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery2)
                .setParameter("username", username)
                .setParameter("role", user_role);
        query2.executeUpdate();
    }
}

