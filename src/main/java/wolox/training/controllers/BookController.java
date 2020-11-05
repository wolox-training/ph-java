package wolox.training.controllers;

import java.util.List;
import java.util.Optional;
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
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@Controller
@RestController
@RequestMapping("/api/books")
public class BookController {

    /**
     * This method show a greeting on the browser
     * @param name : name of param GET in the URL
     * @param model: Class uses for print the value on the screen
     * @return
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
     * @return
     */
    @GetMapping
    public Iterable findAll() {
        return bookRepository.findAll();
    }

    /**
     * Method that find a registry by booktittle
     * @param bookTitle : Param send with the tittle book
     * @return
     */
    @GetMapping("/title/{bookTitle}")
    public List findByTitle(@PathVariable String bookTitle) {
        return bookRepository.findByTitle(bookTitle);
    }

    /**
     * Method that find information by primary key
     * @param id : primary key of one table
     * @return
     */
    @GetMapping("/{id}")
    public Optional<Book> findOne(@PathVariable Long id) {
        return bookRepository.findById(id);
                //.orElseThrow(ActorNotFoundException::new);
    }

    /**
     * Method that save the fields from an entity.
     * @param book object of entity book
     * @return
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    /**
     * Method that delete a registry of one entity by primary key
     * @param id :primary key of one table
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookRepository.findById(id);
                //.orElseThrow(ActorNotFoundException::new);
        bookRepository.deleteById(id);
    }

    /**
     * method that update a registry by primary key
     * @param book : object of entity book
     * @param id : primary key of one table
     * @return
     */
    @PutMapping("/{id}")
    public Book updateBook(@RequestBody Book book, @PathVariable Long id) {
        if (book.getId() != id) {
            //throw new ActorNotFoundException();
        }
        bookRepository.findById(id);
              //  .orElseThrow(ActorNotFoundException::new);
        return bookRepository.save(book);
    }
}
