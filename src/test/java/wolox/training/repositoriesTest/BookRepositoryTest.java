package wolox.training.repositoriesTest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;
import wolox.training.entitiesTest.EntitiesTest;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager entityManager;


    private static Book secondTestBook;


    private static Book bookTest;
    private static List<Book> bookTests;

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
    void whenCallfindByAllParametersWithSomeParametersEmptyThenRetunrAListBook() {
        bookRepository.save(bookTest);
        Page<Book> books = bookRepository.findByAllParameters("", "", "", "", "", bookTest.getPublisher(), "20", "24", bookTest.getPages(), bookTest.getIsbn(), null);
        assertEquals(books.getContent().iterator().next().getAuthor(), bookTest.getAuthor());
    }

    @Test
    void whenCallfindByAllParametersWithAllParametersEmptyThenRetunrAListBook() {
        bookRepository.save(bookTest);
        Page<Book> books = bookRepository.findByAllParameters("", "", "", "", "", "", "", "", 0, "", null);
        assertEquals(books.getContent().iterator().next().getAuthor(), bookTest.getAuthor());
    }

}
