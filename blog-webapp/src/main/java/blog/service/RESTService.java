package blog.service;

import blog.entity.Post;
import blog.entity.Comment;
import blog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;

@Service
public class RESTService {

    private String RESTServiceAddress = "http://localhost:8080/rest";
    private String headerName = "My-Rest-Token";

    @Autowired
    private HttpSession httpSession;

    private String getToken() {
        return (String)httpSession.getAttribute("token");
    }


    public Post retrievePost(int id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = RESTServiceAddress + "/rest/posts/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.add(headerName, getToken());
        HttpEntity<String> request = new HttpEntity<String>(headers);
        Post post = restTemplate.exchange(url, HttpMethod.GET, request, Post.class).getBody();
        return post;
    }

    public Comment[] retrieveComments(int id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = RESTServiceAddress + "/rest/posts/" + id + "/comments";
        HttpHeaders headers = new HttpHeaders();
        headers.add(headerName, getToken());
        HttpEntity<String> request = new HttpEntity<String>(headers);
        Comment[] comments = restTemplate.exchange(url, HttpMethod.GET, request, Comment[].class).getBody();
        return comments;
    }

    public void createComment(Comment comment) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add(headerName, getToken());
        HttpEntity request = new HttpEntity(comment, headers);
        String url = RESTServiceAddress + "/rest/posts/" +comment.getPostId() + "/comments";
        restTemplate.exchange(url, HttpMethod.POST, request, Comment.class).getBody();
    }

    public Post[] retrievePosts() {
        RestTemplate restTemplate = new RestTemplate();
        String url = RESTServiceAddress + "/rest/posts/";
        HttpHeaders headers = new HttpHeaders();
        headers.add(headerName, getToken());
        HttpEntity<String> request = new HttpEntity<String>(headers);
        Post[] posts = restTemplate.exchange(url, HttpMethod.GET, request, Post[].class).getBody();
        return posts;
    }

    public void createPost(Post post) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add(headerName, getToken());
        HttpEntity request = new HttpEntity(post, headers);
        String url = RESTServiceAddress + "/rest/posts/";
        restTemplate.exchange(url, HttpMethod.POST, request, Post.class).getBody();
    }

    public void deletePost(int id) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add(headerName, getToken());
        HttpEntity request = new HttpEntity(headers);
        String url = RESTServiceAddress + "/rest/posts/" + id;
        restTemplate.exchange(url, HttpMethod.DELETE, request, Void.class);
    }

    public void updatePost(Post post) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add(headerName, getToken());
        HttpEntity request = new HttpEntity(post, headers);
        String url = RESTServiceAddress + "/rest/posts/" + post.getId();
        restTemplate.exchange(url, HttpMethod.PUT, request, Void.class);
    }

    public String getToken(User user) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity request = new HttpEntity(user);
        String url = RESTServiceAddress + "/rest/token/";
        return (String)restTemplate.exchange(url, HttpMethod.POST, request, String.class).getBody();
    }

}
