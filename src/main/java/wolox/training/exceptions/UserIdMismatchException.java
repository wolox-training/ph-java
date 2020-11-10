package wolox.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Class to control when client made a bad request on there is some bad information
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "IDs of users not match")
public class UserIdMismatchException extends RuntimeException{

}
