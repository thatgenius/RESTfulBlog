package blog.controller;

import blog.entity.Post;
import blog.service.RESTService;
import blog.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;


@Controller
@RequestMapping("/")
public class WebBlogController {

    private String dateFormat = "dd.MM.yyyy";
    private final String authorizedUser = "ROLE_USER";
    private final String admin = "ROLE_ADMIN";

    @Autowired
    RESTService restService;

    @RequestMapping(method = RequestMethod.GET)
    public String getPosts(Model model) {
        model.addAttribute("posts", restService.retrievePosts());
        model.addAttribute("dateFormatter", new SimpleDateFormat(dateFormat));
        return "posts";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/post/{id}")
    public String getPost(Model model, @PathVariable int id) {
        model.addAttribute("post", restService.retrievePost(id));
        Comment[] comments = Comment.orderComments(restService.retrieveComments(id));
        model.addAttribute("comments", comments);
        model.addAttribute("comment", new Comment());
        model.addAttribute("dateFormatter", new SimpleDateFormat(dateFormat));
        return "post";
    }

    @Secured({authorizedUser, admin})
    @RequestMapping(value = "/createComment", method = RequestMethod.POST)
    public RedirectView createComment(Comment comment) {
        restService.createComment(comment);
        RedirectView rv = new RedirectView("/post/" + comment.getPostId());
        rv.setContextRelative(true);
        rv.setExposeModelAttributes(false);
        return rv;
    }

    @Secured(admin)
    @RequestMapping(method = RequestMethod.GET, value = "/new")
    public String createPostPage(Model model) {
        Post post = new Post();
        model.addAttribute("post", post);
        return "newPost";
    }

    @Secured(admin)
    @RequestMapping(method = RequestMethod.POST, value = "/createPost")
    public RedirectView createPost(Post post) {
        restService.createPost(post);
        RedirectView rv = new RedirectView("/");
        rv.setContextRelative(true);
        rv.setExposeModelAttributes(false);
        return rv;
    }

    @Secured(admin)
    @RequestMapping(value = "/deletePost/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String deletePost(@PathVariable int id) {
        restService.deletePost(id);
        return "deleted";
    }

    @Secured(admin)
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String updatePostPage(@PathVariable int id, Model model) {
        model.addAttribute("post", restService.retrievePost(id));
        return "update";
    }

    @Secured(admin)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public RedirectView updatePost(Post post) {
        restService.updatePost(post);
        RedirectView rv = new RedirectView("/");
        rv.setContextRelative(true);
        rv.setExposeModelAttributes(false);
        return rv;
    }

}