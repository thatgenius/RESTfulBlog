package blog.controller;

import blog.DAO.CommentDAO;
import blog.DAO.PostDAO;
import blog.entity.Comment;
import blog.entity.Post;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.net.BindException;
import java.util.List;

@RestController
@RequestMapping("/rest/posts")
public class RestBlogController {

    private PostDAO postDAO;
    private CommentDAO commentDAO;

    @Inject
    public RestBlogController(PostDAO postDAO, CommentDAO commentDAO) {
        this.postDAO = postDAO;
        this.commentDAO = commentDAO;
    }

    // getPosts (xml, json) READY
    @RequestMapping(method = RequestMethod.GET, headers = "Accept=text/xml, application/json")
    public Post[] getPosts() {
        List<Post> posts = postDAO.findAll();
        return posts.toArray(new Post[posts.size()]);
    }

    @RequestMapping(method = RequestMethod.GET, headers = "Accept=text/xml, application/json", value = "/{id}/comments")
    public Comment[] getComments(@PathVariable int id) {
        List<Comment> comments = commentDAO.findAllByPostId(id);
        return comments.toArray(new Comment[comments.size()]);
    }

    // create READY
    @RequestMapping(method = RequestMethod.POST, headers = "Accept=text/xml, application/json", value = "/{id}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Comment createComment(@RequestBody Comment comment, BindingResult result, HttpServletResponse response) throws BindException {
        if (result.hasErrors()) {
            throw new BindException();
        }
        commentDAO.save(comment);
//        response.setHeader("Location", "/rest/posts/" + comment.getId() + "/comments/" + comment.getId());
        return comment;
    }

    // get (xml, json) READY
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=text/xml, application/json")
    public @ResponseBody Post getPost(@PathVariable int id) {
        return postDAO.getById(id);
    }

    // delete READY
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public Post deletePost(@PathVariable int id) {
        return postDAO.delete(id);
    }


    // update READY
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePost(@PathVariable int id, @RequestBody Post post) {
        postDAO.update(post);
    }


    // create READY
    @RequestMapping(method = RequestMethod.POST, headers = "Accept=text/xml, application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Post createPost(@RequestBody Post post, BindingResult result, HttpServletResponse response) throws BindException {
        if (result.hasErrors()) {
            throw new BindException();
        }
        postDAO.save(post);
        response.setHeader("Location", "/rest/posts/" + post.getId());
        return post;
    }
}
