package hr.klobucaric.webshop.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {
    public Authentication getAuthenticatedUser(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
