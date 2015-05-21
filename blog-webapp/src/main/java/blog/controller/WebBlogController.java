package blog.controller;

import blog.entity.Post;
import blog.service.RESTService;
import blog.entity.Comment;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import java.text.SimpleDateFormat;


@Controller
@RequestMapping("/")
public class WebBlogController {

    private String dateFormat = "dd.MM.yyyy";
    private final String authorizedUser = "ROLE_USER";
    private final String admin = "ROLE_ADMIN";

    @RequestMapping(method = RequestMethod.GET)
    public String getPosts(Model model) {
        model.addAttribute("posts", RESTService.retrievePosts());
        model.addAttribute("dateFormatter", new SimpleDateFormat(dateFormat));
        return "posts";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/post/{id}")
    public String getPost(Model model, @PathVariable int id) {
        model.addAttribute("post", RESTService.retrievePost(id));
        Comment[] comments = Comment.orderComments(RESTService.retrieveComments(id));
        model.addAttribute("comments", comments);
        model.addAttribute("comment", new Comment());
        model.addAttribute("dateFormatter", new SimpleDateFormat(dateFormat));
        return "post";
    }

    @Secured({authorizedUser, admin})
    @RequestMapping(value = "/createComment", method = RequestMethod.POST)
    public RedirectView createComment(Comment comment, Model model) {
        RESTService.createComment(comment);
        RedirectView rv = new RedirectView("/post/" + comment.getPost_id());
        rv.setContextRelative(true);
        rv.setExposeModelAttributes(false);
        return rv;
    }

    @Secured({authorizedUser, admin})
    @RequestMapping(method = RequestMethod.GET, value = "/new")
    public String createPostPage(Model model) {
        Post post = new Post();
        model.addAttribute("post", post);
        return "newPost";
    }

    @Secured({authorizedUser, admin})
    @RequestMapping(method = RequestMethod.POST, value = "/createPost")
    public RedirectView createPost(Post post, Model model) {
        RESTService.createPost(post);
        RedirectView rv = new RedirectView("/");
        rv.setContextRelative(true);
        rv.setExposeModelAttributes(false);
        return rv;
    }

    @Secured({authorizedUser, admin})
    @RequestMapping(value = "/deletePost/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String deletePost(@PathVariable int id) {
        RESTService.deletePost(id);
        return "deleted";
    }

    @Secured({authorizedUser, admin})
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String updatePostPage(@PathVariable int id, Model model) {
        model.addAttribute("post", RESTService.retrievePost(id));
        return "update";


    }

    @Secured({authorizedUser, admin})
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public RedirectView updatePost(Post post, Model model) {
        RESTService.updatePost(post);
        RedirectView rv = new RedirectView("/");
        rv.setContextRelative(true);
        rv.setExposeModelAttributes(false);
        return rv;
    }

}