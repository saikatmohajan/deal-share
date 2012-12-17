package org.springframework.social.security.security;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.security.model.User;
import org.springframework.social.security.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility methods for working with Spring Security.
 */
public class SecurityUtil {

    public static User getLoggedInUser() {
    	Logger.getLogger(SecurityUtil.class).debug("Inside getLoggeInUser()");
        User user = null;
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication auth;
        if (securityContext != null) {
            auth = securityContext.getAuthentication();
            if (auth != null) {
                Object principal = auth.getPrincipal();
                if (principal instanceof UserDetailsImpl) {
                    UserDetailsImpl authUser = (UserDetailsImpl) principal;
                    user = authUser.getUser();
                }
            }
        }
        return user;
    }

    public static Authentication signInUser(User user) {
    	Logger.getLogger(SecurityUtil.class).debug("Inside signInUser()");
        List<GrantedAuthority> roles = getRoles(user);
        UserDetailsImpl springSecurityUser = new UserDetailsImpl(user, roles);
        Authentication authentication = new UsernamePasswordAuthenticationToken(springSecurityUser, user.getPassword(), roles);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    public static List<GrantedAuthority> getRoles(User user) {
    	Logger.getLogger(SecurityUtil.class).debug("Inside getRoles()");
        List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
        roles.add(new GrantedAuthorityImpl("ROLE_USER"));
        return roles;
    }
}
