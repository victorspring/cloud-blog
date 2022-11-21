package life.maxima.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends NoSuchElementException {

    public NotFoundException(String message) {
        super(message);
    }
}
