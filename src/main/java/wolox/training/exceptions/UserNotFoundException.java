package wolox.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Class to control exception when you not found a resource.
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User Not Found")
public class UserNotFoundException extends RuntimeException  {
    private static final long serialVersionUID = 1L;
    public UserNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
