package wolox.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
public class UserController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Method that find all the information of user entity
     * @return: a collection type list with the information of user table
     */
    @GetMapping
    public Iterable findAll() {
        return userRepository.findAll();
    }

    /**
     * Method that find information by primary key
     * @param id : primary key of one table
     * @return: the information of with  id you send.
     */
    @GetMapping("/{id}")
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
    public User create(@RequestBody User user) {
        return userRepository.save(user);
    }

    /**
     * Method that delete a registry of one entity by primary key
     * @param id :primary key of one table
     */
    @DeleteMapping()
    public void delete(@RequestParam("id") Long id) {
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
     * @param book: object book to save
     * @return null(message of success or warning )
     * @throws UserNotFoundException
     */
    
    @PostMapping("/books")
    @ResponseStatus(HttpStatus.CREATED)
    public User addBook(@RequestBody Book book)
            throws UserNotFoundException {
        User user = userRepository.findById(book.getId()).orElseThrow(() -> new UserNotFoundException(
                ErrorConstants.NOT_EXIST_USER_ID));
        user.addBook(book);
        return userRepository.save(user);
    }

    /**
     * method removes a registry from book table
     * @param id: user_id from user table
     * @param book: object book to remove
     * @return
     * @throws UserNotFoundException
     */
    @PutMapping("/{id}/booksUpdate")
    public User deleteBook(@PathVariable Long id, @RequestBody Book book)
            throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(
                ErrorConstants.NOT_EXIST_USER_ID));
        user.removeBook(book);
        return userRepository.save(user);
    }

}
