package wolox.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

@Controller
@RestController
@RequestMapping("/api/books")
public class BookController {
    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }


    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public Iterable findAll() {
        return bookRepository.findAll();
    }

    @GetMapping("/title/{bookTitle}")
    public Book findByTitle(@PathVariable String bookTitle) throws BookNotFoundException{
        return bookRepository.findByTitle(bookTitle).orElseThrow(() -> new BookNotFoundException(
                ErrorConstants.NOT_EXIST_TITTLE));

    }

    @GetMapping("/{id}")
    public Book findOne(@PathVariable Long id) throws BookNotFoundException{
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(
                ErrorConstants.NOT_EXIST_ID));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(
                ErrorConstants.NOT_EXIST_ID));
        bookRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Book updateBook(@RequestBody Book book, @PathVariable Long id) throws BookIdMismatchException {
        if (book.getId() != id) {
            throw new BookIdMismatchException(ErrorConstants.NOT_MATCH_UPDATE);
        }
        bookRepository.findById(id);
        return bookRepository.save(book);
    }
}
