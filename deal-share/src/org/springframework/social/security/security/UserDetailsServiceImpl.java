package org.springframework.social.security.security;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.model.User;
import org.springframework.social.security.service.UserService;

import java.util.List;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
    	Logger.getLogger(UserDetailsServiceImpl.class).debug("Inside loadUserByUsername()");
        User domainUser = userService.findByLogin(username);
        if (domainUser == null) {
            throw new UsernameNotFoundException("Could not find user with name '" + username + "'");
        }
        List<GrantedAuthority> roles = SecurityUtil.getRoles(domainUser);
        return new UserDetailsImpl(domainUser, roles);
    }
}
