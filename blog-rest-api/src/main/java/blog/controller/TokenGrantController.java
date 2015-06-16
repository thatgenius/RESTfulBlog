package blog.controller;

import blog.DAO.UserDAO;
import blog.entity.User;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/token")
public class TokenGrantController {

    @Autowired
    private UserDAO userDAO;
    private BCryptPasswordEncoder bCryptInstance = new BCryptPasswordEncoder();

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody String getToken(@RequestBody User userRequested) {
        // get user by username from db
        User user = userDAO.getByUsername(userRequested.getUsername());
        if (user != null) {
            // if exists then compare passwords
            if (bCryptInstance.matches(userRequested.getPassword(), user.getPassword())) {
                // if match then grant fresh token
                String usernameBase64Encoded = new String(Base64.encodeBase64(userRequested.getUsername().getBytes()));
                return usernameBase64Encoded + "%" + user.getPassword();
            }
        }
        return null;
    }
}
