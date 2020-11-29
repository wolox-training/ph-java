package wolox.training.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import wolox.training.model.dto.BookDTO;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;
import wolox.training.service.OpenLibraryService;

@RestController
@RequestMapping("/api/books")
@Api
public class BookController {

    @Autowired
    private OpenLibraryService openLibraryService;

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

    /**
     * Method to  allow search a book by isbn paran
     * @param isbn param to search book in external api
     * @return
     */
    @ApiOperation(value = "Method to search a book by isbn", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The Book found in this present searching"),
            @ApiResponse(code = 201, message = "The Book created"),
            @ApiResponse(code = 404, message = "The Book not found")
    })
    @GetMapping("/findByIsbn")
    public ResponseEntity<Book> findBookByIsbn(@RequestParam String isbn) {
        return bookRepository.findBookByIsbn(isbn)
                .map(book -> new ResponseEntity<>(book, HttpStatus.OK))
                .orElseGet(() -> {
                    BookDTO bookDTO = openLibraryService.bookInfo(isbn);
                    Book book = bookRepository.save(bookDTO.setBook());
                    return new ResponseEntity<>(book, HttpStatus.CREATED);
                });
    }

    /**
     This Method to allow find a book by publisher, genre and year
     *
     * @param publisher param  to execute query
     * @param genre     param  to execute query
     * @param year      param  to execute query
     * @return return a book with specified parameters
     */
    @ApiOperation(value = "Method to find a book by (publisher,genre and year)", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Book found successfully"),
            @ApiResponse(code = 404, message = "Book not found")
    })
    @GetMapping("/findByParams")
    public List<Book> findByPublisherAndGenreAndYear(
            @RequestParam(required = false) String publisher,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String year) {

        List<Book>listBook = bookRepository.findByPublisherAndGenreAndYearQuery(publisher, genre, year);
        return listBook;
    }

    /**
     * Method to search a book by all parameters
     *
     * @param genre     param to find genre in entity
     * @param author    param to find  author in entity
     * @param image     param to find  image in entity
     * @param title     param to find  title in entity
     * @param subtitle  param to find  subtitle in entity
     * @param publisher param to find  publisher in entity
     * @param initialYear param to find initialYear in entity
     * @param finalYear   param to find  finalYear in entity
     * @param pages     param to find  pages in entity
     * @param isbn      param to find isbn in entity
     * @return book depending on the parameters
     */
    @ApiOperation(value = "Method to search a book by all parameters", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Book found successfully"),
            @ApiResponse(code = 404, message = "Book not found")
    })
    @GetMapping
    public ResponseEntity<List<Book>> findByParameters(
            @RequestParam(required = false, defaultValue = "") String genre,
            @RequestParam(required = false, defaultValue = "") String author,
            @RequestParam(required = false, defaultValue = "") String image,
            @RequestParam(required = false, defaultValue = "") String title,
            @RequestParam(required = false, defaultValue = "") String subtitle,
            @RequestParam(required = false, defaultValue = "") String publisher,
            @RequestParam(required = false, defaultValue = "") String initialYear,
            @RequestParam(required = false, defaultValue = "") String finalYear,
            @RequestParam(required = false, defaultValue = "") int pages,
            @RequestParam(required = false, defaultValue = "") String isbn,
            Pageable pageable) {
        List<Book> books = bookRepository
                .findByAllParameters(genre, author, image, title, subtitle, publisher, initialYear, finalYear, pages, isbn);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }


}
