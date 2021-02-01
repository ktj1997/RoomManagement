package com.room.manage.core.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static Long getUserIdFromToken() {
        return Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
