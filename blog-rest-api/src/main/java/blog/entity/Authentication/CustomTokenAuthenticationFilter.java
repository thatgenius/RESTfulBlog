package blog.entity.Authentication;

import blog.DAO.UserDAO;
import blog.service.CryptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CustomTokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private AuthenticationManager authenticationManager;
    @Autowired
    private CryptService cryptService;
    @Autowired
    private UserDAO userDAO;

    private HttpServletRequest req = null;
    private Authentication userAuthenticationToken = null;
    private String postCreationUriRegEx = "/rest/posts(/?)";
    private String commentCreationUriRegEx = "/rest/posts/(\\d)/comments";
    public final String HEADER_SECURITY_TOKEN = "My-Rest-Token";

    public CustomTokenAuthenticationFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
        super(defaultFilterProcessesUrl);
        this.authenticationManager = authenticationManager;
        super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        setAuthenticationManager(new NoOpAuthenticationManager());
        setAuthenticationSuccessHandler(new TokenSimpleUrlAuthenticationSuccessHandler());
    }

    private boolean freeAccessResourceRequested(HttpServletRequest req) {
        return req.getMethod().toString().equals("GET");
    }

    private boolean userAuthenticationResourceRequested() {
        return (req.getServletPath().matches(commentCreationUriRegEx) && req.getMethod().toString().equals("POST"));
    }

    private boolean adminAuthenticationResourceRequested() {
        return checkHttpMethod("PUT") || checkHttpMethod("DELETE") || (req.getServletPath().matches(postCreationUriRegEx) && checkHttpMethod("POST"));
    }

    private boolean checkHttpMethod(String method) {
        return req.getMethod().equals(method);
    }


    private Authentication anonymousAuthentication() {
        return new UsernamePasswordAuthenticationToken("everyone", null, null);
    }

    private void setParsedToken() {
        String token = req.getHeader(HEADER_SECURITY_TOKEN);
        userAuthenticationToken = parseToken(token);
        if (userAuthenticationToken == null) {
            throw new AuthenticationServiceException("User's token is corrupted");
        }
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        req = request;
        if (freeAccessResourceRequested(request)) {
            return anonymousAuthentication();
        }
        setParsedToken();
        if (userAuthenticationResourceRequested()) {
            checkUserAuthority("ROLE_USER", "ROLE_ADMIN");
        }
        if (adminAuthenticationResourceRequested()) {
            checkUserAuthority("ROLE_ADMIN");
        }
        return userAuthenticationToken;
    }

    private void checkUserAuthority(String... authorities) {
        boolean hasRole = false;
        for (String authority: authorities) {
            if (userAuthenticationToken.getAuthorities().toString().equals("["+authority+"]")) {
                hasRole = true;
            }
        }
        if (!hasRole) {
            throw new AuthenticationServiceException("User has no authority to access the resource");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }

    private Authentication parseToken(String encryptedToken) {
        try {
            String username = cryptService.decrypt(encryptedToken);
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null,
                    AuthorityUtils.createAuthorityList(userDAO.getUserRole(username)));
            return authentication;
        } catch (Exception e) {
            return null;
        }
    }
}