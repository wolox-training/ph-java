package wolox.training.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@RestController
@RequestMapping("/api/books")
@Api
public class BookController {

    /**
     * This method show a greeting on the browser
     * @param name : name of param GET in the URL
     * @param model: Class uses for print the value on the screen
     * @return : the greetinng with the name you put in the URL
     */
    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }


    @Autowired
    private BookRepository bookRepository;

    /**
     * Method that find all the information of one entity.
     * @return: a collection type list with the information of book table
     */
    @GetMapping
    @ApiOperation(value ="Find all book", notes ="Return all  books")
    public Iterable findAll() {
        return bookRepository.findAll();
    }

    /**
     * Method that find a registry by booktittle
     * @param bookTitle : Param send with the tittle book
     * @return the information of book with the tittle you send.
     */
    @GetMapping("/title/{bookTitle}")
    @ApiOperation(value ="Find a book", notes ="Find a book by Tittle")
    public Book findByTitle(@PathVariable String bookTitle) throws BookNotFoundException{
        return bookRepository.findByTitle(bookTitle).orElseThrow(() -> new BookNotFoundException(
                ErrorConstants.NOT_EXIST_TITTLE));

    }
    @GetMapping("/{id}")
    @ApiOperation(value = "Giving an id, return the book", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Succesfully retrived book"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing th resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    /**
     * Method that find information by primary key
     * @param id : primary key of one table
     * @return: the information of with with id you send.
     */

    public Book findOne(@ApiParam(value = "id to find the book", required  = true) @PathVariable Long id) throws BookNotFoundException{
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(
                ErrorConstants.NOT_EXIST_ID));
    }

    /**
     * Method that save the fields from an entity.
     * @param book object of entity book
     * @return: null(message of success or warning )
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value ="Create a book", notes ="Create  a new book")
    public Book create(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    /**
     * Method that delete a registry of one entity by primary key
     * @param id :primary key of one table
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value ="Remove a book", notes ="Remove a book by id")
    public void delete(@PathVariable Long id) {
        bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(
                ErrorConstants.NOT_EXIST_ID));
        bookRepository.deleteById(id);
    }

    /**
     * method that update a registry by primary key
     * @param book : object of entity book
     * @param id : primary key of one table
     * @return null(message of success or warning )
     */
    @PutMapping("/{id}")
    @ApiOperation(value ="Update a book", notes ="Update a book by id")
    public Book updateBook(@RequestBody Book book, @PathVariable Long id) throws BookIdMismatchException {
        if (book.getId() != id) {
            throw new BookIdMismatchException();
        }
        bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(
                ErrorConstants.NOT_EXIST_ID));
        return bookRepository.save(book);
    }
}
