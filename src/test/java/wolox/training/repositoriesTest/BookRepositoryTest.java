package wolox.training.repositoriesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
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
        bookRepository.save(bookTest);
        List<Book> listBooks = bookRepository.findByPublisherAndGenreAndYear(bookTest.getPublisher(), bookTest.getGenre(), bookTest.getYear());
        assertEquals(listBooks.iterator().next().getPublisher(), bookTest.getPublisher());
    }


}
