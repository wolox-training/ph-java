package wolox.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Book Not Found")
public class BookNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;
        public BookNotFoundException(String errorMessage) {
            super(errorMessage);
        }
}
