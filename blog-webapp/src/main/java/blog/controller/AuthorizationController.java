package blog.controller;


import blog.DAO.UserDao;
import blog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class AuthorizationController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public String signUpPage(ModelMap model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public String signUp(@ModelAttribute User user) {
        userDao.createNewUser(user.getUsername(), user.getPassword());
        return "signIn";
    }

    @RequestMapping(value = "/signIn", method = RequestMethod.GET)
    public String signInPage() {
        return "signIn";
    }
}