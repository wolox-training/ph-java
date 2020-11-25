package wolox.training.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wolox.training.constants.ErrorConstants;
import wolox.training.exceptions.BookIdMismatchException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.exceptions.UserIdMismatchException;
import wolox.training.exceptions.UserNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

/**
 * class controller of one user
 */
@RestController
@RequestMapping("/api/user")
@Api
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    /**
     * Method that find all the information of user entity
     * @return: a collection type list with the information of user table
     */
    @GetMapping
    @ApiOperation(value ="Find all users", notes ="Return all  users")
    public Iterable findAll() {
        return userRepository.findAll();
    }

    /**
     * Method that find information by primary key
     * @param id : primary key of one table
     * @return: the information of with  id you send.
     */
    @GetMapping("/{id}")
    @ApiOperation(value ="Find a user", notes ="Find a user by id")
    public User findOne(@PathVariable Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(
                ErrorConstants.NOT_EXIST_USER_ID));
    }

    /**
     * Method that save the fields from an entity.
     * @param user object of entity user
     * @return: null(message of success or warning )
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value ="Create a user", notes ="Create  a new user")
    public User create(@RequestBody User user) {
        return userRepository.save(user);
    }

    /**
     * Method that delete a registry of one entity by primary key
     * @param id :primary key of one table
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value ="Remove a book", notes ="Remove a book by id")
    public void delete(@PathVariable Long id) {
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(
                ErrorConstants.NOT_EXIST_USER_ID));
        userRepository.deleteById(id);
    }

    /**
     * method that update a registry by primary key
     * @param user : object of entity user
     * @param id : primary key of one table
     * @return null(message of success or warning )
     */
    @PutMapping("/{id}")
    @ApiOperation(value ="Update a user", notes ="Update a user by id")
    public User updateBook(@RequestBody User user, @PathVariable Long id) throws UserIdMismatchException {
        if (user.getId() != id) {
            throw new UserIdMismatchException();
        }
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(
                ErrorConstants.NOT_EXIST_USER_ID));
        return userRepository.save(user);
    }
    /**
     * method that save a registry a book
     * @param book_id: book_id from book table
     * @param user_id: user_id from user table
     * @return null(message of success or warning )
     * @throws UserNotFoundException
     */
    @PutMapping("/{user_id}/add-book/{book_id}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value ="Create a book", notes ="Create  a new book by user")
        public User addBook(@PathVariable Long user_id, @PathVariable Long book_id)
            throws UserNotFoundException {
        User user = userRepository.findById(user_id).orElseThrow(() -> new UserNotFoundException(
                ErrorConstants.NOT_EXIST_USER_ID));
        Book book = bookRepository.findById(book_id).orElseThrow(() -> new BookNotFoundException(
                ErrorConstants.NOT_EXIST_ID));
        user.addBook(book);
        return userRepository.save(user);
    }

    /**
     * method removes a registry from book table
     * @param book_id: book_id from book table
     * @param user_id: user_id from user table
     * @return
     * @throws UserNotFoundException
     */

    @PutMapping("/{user_id}/remove-book/{book_id}")
    public User deleteBook(@PathVariable Long user_id, @PathVariable Long book_id)
            throws UserNotFoundException {
        User user = userRepository.findById(user_id).orElseThrow(() -> new UserNotFoundException(
                ErrorConstants.NOT_EXIST_USER_ID));
        Book book = bookRepository.findById(book_id).orElseThrow(() -> new BookNotFoundException(
                ErrorConstants.NOT_EXIST_ID));
        user.removeBook(book);
        return userRepository.save(user);
    }

}
