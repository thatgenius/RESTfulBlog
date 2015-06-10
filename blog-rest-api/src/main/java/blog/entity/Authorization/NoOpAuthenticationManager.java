package blog.entity.Authorization;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.*;


public class NoOpAuthenticationManager implements AuthenticationManager {

        public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        // TODO Auto-generated method stub
        return authentication;
    }

}
