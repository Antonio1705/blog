package de.example.blog.util;

import de.example.blog.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class SecurityUtils {
    private static final Logger logger = LoggerFactory.getLogger(SecurityUtils.class);

    public static org.springframework.security.core.userdetails.User getCurrentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        logger.debug("Principal: >>>>" + principal );
        logger.debug("Authentication: >>>>" + SecurityContextHolder.getContext().getAuthentication());
        if (principal instanceof User){
            return (org.springframework.security.core.userdetails.User) principal;
        }

        throw new RuntimeException("No authenticated user found");
    }

    public static String getRole(){
        org.springframework.security.core.userdetails.User user = getCurrentUser();

        Collection<GrantedAuthority> authorities = user.getAuthorities();
        for (GrantedAuthority authority: authorities){
            return authority.getAuthority();
        }
        return null;
    }
}
