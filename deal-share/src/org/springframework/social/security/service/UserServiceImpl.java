package org.springframework.social.security.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.social.security.dao.UserDAO;
import org.springframework.social.security.model.User;
import org.springframework.social.security.security.SecurityUtil;
import org.springframework.social.security.security.UserDetailsImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private SaltSource saltSource;
    
    public User findByLogin(String login) {
    	Logger.getLogger(getClass()).debug("Inside findByLogin()");
        return userDAO.findByUsername(login);
    }

    @Transactional(readOnly = false)
    public void registerUser(User user) {
    	
    	Logger.getLogger(UserServiceImpl.class).debug("Inside registerUser()");
    	String password = user.getPassword();
    	List<GrantedAuthority> roles = SecurityUtil.getRoles(user);
        Object salt = saltSource.getSalt(new UserDetailsImpl(user, roles));
    	
        if (password == null) {
            String generatedPassword = generatePassword();
            user.setPassword(passwordEncoder.encodePassword(generatedPassword, salt));
        }else{    
        	user.setPassword(passwordEncoder.encodePassword(password, salt));
        }
        
        userDAO.save(user);
    }

    private static final String RANDOM_PASSWORD_CHARS =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890-_!$*";

    private static final int RANDOM_PASSWORD_LENGTH = 12;

    private String generatePassword() {
    	Logger.getLogger(getClass()).debug("Inside generatePassword()");
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < RANDOM_PASSWORD_LENGTH; i++) {
            int charIndex = (int) (Math.random() * RANDOM_PASSWORD_CHARS.length());
            char randomChar = RANDOM_PASSWORD_CHARS.charAt(charIndex);
            password.append(randomChar);
        }
        return password.toString();
    }
}
