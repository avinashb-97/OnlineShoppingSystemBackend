package com.sreihaan.SreihaanFood.utils;

import com.sreihaan.SreihaanFood.model.persistence.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.List;

public class AuthUtil {

    private static Authentication getLoggedInUser()
    {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static String getLoggedInUserName()
    {
        return getLoggedInUser().getName();
    }

    private static Role getCurrentUserRole()
    {
        Collection roles = getLoggedInUser().getAuthorities();
        if(roles.contains(Role.ADMIN))
        {
            return Role.ADMIN;
        }
        if(roles.contains(Role.MODERATOR))
        {
            return Role.MODERATOR;
        }
        if (roles.contains(Role.USER))
        {
            return Role.USER;
        }
        return null;
    }

    public static boolean isCurrentUserIsAdmin()
    {
        return getCurrentUserRole() == Role.ADMIN;
    }

    public static boolean isCurrentUserIsModerator()
    {
        return getCurrentUserRole() == Role.MODERATOR;
    }

    public static boolean isCurrentUserIsUser()
    {
        return getCurrentUserRole() == Role.USER;
    }

}
