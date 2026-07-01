package com.goodsoft.security;

import com.goodsoft.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static User getCurrentUser() {
        AppUserPrincipal principal = (AppUserPrincipal) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return principal.getUser();
    }
}
