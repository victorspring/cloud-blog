package life.maxima.blog.utils;

import life.maxima.blog.exception.NotFoundException;

import java.util.function.Supplier;

public class ExceptionUtils {

    public static final Supplier<NotFoundException> NOT_FOUND = () -> {
        throw new NotFoundException("Not found");
    };
}
