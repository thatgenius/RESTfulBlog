package blog.service;

import blog.DAO.UserDAO;
import blog.entity.User;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CryptService {

    @Autowired
    private UserDAO userDAO;

    @Transactional
    public String decrypt(String encryptedToken) {
        String[] credentials = encryptedToken.split("%");
        String username = new String(Base64.decodeBase64(credentials[0].getBytes()));
        User user = userDAO.getByUsername(username);
        String password = credentials[1];
        if (password.equals(user.getPassword())) {
            return username;
        }
        return null;
    }
}
