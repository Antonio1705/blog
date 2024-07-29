package de.example.blog.util;

import de.example.blog.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityUtils {
    private static final Logger logger = LoggerFactory.getLogger(SecurityUtils.class);

    public static User getCurrentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        logger.debug("Principal: >>>>" + principal );
        logger.debug("Authentication: >>>>" + SecurityContextHolder.getContext().getAuthentication());
        if (principal instanceof User){
            return (User) principal;
        }

        throw new RuntimeException("No authenticated user found");
    }
}
