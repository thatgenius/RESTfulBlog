package blog.service;

import blog.entity.Post;
import blog.entity.Comment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class RESTService {

    static final String RESTServiceAddress = "http://localhost:8080/rest";

    //login and pass as login:pass
    static final String RESTServicePassword = "Basic dXNlcjpwYXNz";

    public static Post retrievePost(int id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = RESTServiceAddress + "/rest/posts/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", RESTServicePassword);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        Post post = restTemplate.exchange(url, HttpMethod.GET, request, Post.class).getBody();
        return post;
    }

    public static Comment[] retrieveComments(int id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = RESTServiceAddress + "/rest/posts/" + id + "/comments";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", RESTServicePassword);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        Comment[] comments = restTemplate.exchange(url, HttpMethod.GET, request, Comment[].class).getBody();
        return comments;
    }

    public static void createComment(Comment comment) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", RESTServicePassword);
        HttpEntity request = new HttpEntity(comment, headers);
        String url = RESTServiceAddress + "/rest/posts/" +comment.getPost_id() + "/comments";
        restTemplate.exchange(url, HttpMethod.POST, request, Comment.class).getBody();
    }

    public static Post[] retrievePosts() {
        RestTemplate restTemplate = new RestTemplate();
        String url = RESTServiceAddress + "/rest/posts/";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", RESTServicePassword);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        Post[] posts = restTemplate.exchange(url, HttpMethod.GET, request, Post[].class).getBody();
        return posts;
    }

    public static void createPost(Post post) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", RESTServicePassword);
        HttpEntity request = new HttpEntity(post, headers);
        String url = RESTServiceAddress + "/rest/posts/";
        restTemplate.exchange(url, HttpMethod.POST, request, Post.class).getBody();
    }

    public static void deletePost(int id) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", RESTServicePassword);
        HttpEntity request = new HttpEntity(headers);
        String url = RESTServiceAddress + "/rest/posts/" + id;
        restTemplate.exchange(url, HttpMethod.DELETE, request, Void.class);
    }

    public static void updatePost(Post post) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", RESTServicePassword);
        HttpEntity request = new HttpEntity(post, headers);
        String url = RESTServiceAddress + "/rest/posts/" + post.getId();
        restTemplate.exchange(url, HttpMethod.PUT, request, Void.class);
    }


}
