package blog.entity;


public class Token {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public Token(int i) {
        username = Integer.toString(i);
        password = Integer.toString(i);
    }

}
