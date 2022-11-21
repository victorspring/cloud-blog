package life.maxima.blog.utils;

import life.maxima.blog.entity.Comment;
import life.maxima.blog.entity.Post;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;
import java.util.stream.Collectors;


public class SecurityUtils {

    public static final String ADMIN = "ADMIN";

    private static final String ACCESS_DENIED = "Access Denied";

    public static Authentication getCurrentAuthentication() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication();

        if (principal instanceof Authentication authentication) {
            return authentication;
        } else {
            throw new AccessDeniedException(ACCESS_DENIED);
        }
    }

    public static void checkAuthorityOnPost(Post post) {
        if (!isAuthor(post)) {
            throw new AccessDeniedException(ACCESS_DENIED);
        }
    }

    public static void checkIsAuthorOrAdmin(Post post) {
        if (!isAuthor(post) && !isAdmin()){
            throw new AccessDeniedException(ACCESS_DENIED);
        }
    }

    public static boolean isAuthor(Post post) {
        return isAuthor(getCurrentAuthentication(), post);
    }

    private static boolean isAuthor(Authentication user, Post post) {
        return Objects.equals(user.getName(), post.getUserId());
    }

    private static boolean isAuthor(Authentication user, Comment comment) {
        return Objects.equals(user.getName(), comment.getUserId());
    }

    public static boolean isAuthor(Comment comment) {
        return isAuthor(getCurrentAuthentication(), comment);
    }

    public static void checkAuthorityOnComment(Comment comment) {
        if (!isAuthor(comment)) {
            throw new AccessDeniedException(ACCESS_DENIED);
        }
    }


    public static boolean isAdmin() {
        return isAdmin(getCurrentAuthentication());
    }

    private static boolean isAdmin(Authentication user) {
        return user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet())
                .contains("ROLE_" + ADMIN);
    }


}
