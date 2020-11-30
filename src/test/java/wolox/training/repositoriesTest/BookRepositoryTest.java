package wolox.training.repositoriesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import wolox.training.entitiesTest.EntitiesTest;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@DataJpaTest
class BookRepositoryTest {

    private static final String PARAM_NULL = " ";
    private static Book secondTestBook;
    private static Book bookTest;
    private static List<Book> bookTests;
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeAll
    static void setUp() {
        bookTest = EntitiesTest.mockBook();
        bookTests = EntitiesTest.mockManyBooks();
        secondTestBook = EntitiesTest.mockBookPersist();
    }

    @Test
    void whenCallFindAllBookThenReturnAllList() {
        bookRepository.saveAll(bookTests);
        List<Book> bookList = bookRepository.findAll();
        assertEquals(bookList.get(0).getAuthor(), bookTests.get(0).getAuthor());
        assertEquals(bookList.get(0).getId(), bookTests.get(0).getId());
    }

    @Test
    void whenUpdateBookThenReturnBookUpdated() {
        bookRepository.save(bookTest);
        bookTest.setAuthor("pablo");
        Book book = bookRepository.save(bookTest);
        assertEquals(book.getAuthor(), bookTest.getAuthor());
    }

    @Test
    void whenSaveBookThenJpaPersisted() {
        bookRepository.save(secondTestBook);
        Optional<Book> book = bookRepository.findById(secondTestBook.getId());
        assertEquals(book.get().getId(), secondTestBook.getId());
    }

    @Test
    void whenCallfindByPublisherAndGenreAndYearThenReturnListBook() {
        Pageable pageable = PageRequest.of(0,1, Sort.by("author"));
        bookRepository.save(bookTest);
        Page<Book> listBooks = bookRepository.findByPublisherAndGenreAndYear(bookTest.getPublisher(), bookTest.getGenre(), bookTest.getYear(), pageable);
        assertEquals(listBooks.iterator().next().getPublisher(), bookTest.getPublisher());
    }

    @Test
    void whenCallfindByAllParametersWithSomeParametersEmptyThenRetunrAListBook() {
        Pageable pageable = PageRequest.of(0,1, Sort.by("author"));
        bookRepository.save(bookTest);
        Page<Book> books = bookRepository.findByAllParameters(PARAM_NULL, PARAM_NULL, PARAM_NULL, PARAM_NULL, PARAM_NULL, bookTest.getPublisher(), "20", "24", bookTest.getPages(), bookTest.getIsbn(), pageable);
        assertEquals(books.iterator().next().getAuthor(), bookTest.getAuthor());
    }

    @Test
    void whenCallfindByAllParametersWithAllParametersEmptyThenRetunrAListBook() {
        Pageable pageable = PageRequest.of(0,1, Sort.by("author"));
        bookRepository.save(bookTest);
        Page<Book> books = bookRepository.findByAllParameters(PARAM_NULL, PARAM_NULL, PARAM_NULL, PARAM_NULL, PARAM_NULL, PARAM_NULL, PARAM_NULL, PARAM_NULL, 0, PARAM_NULL, pageable);
        assertEquals(books.iterator().next().getAuthor(), bookTest.getAuthor());
    }


}
