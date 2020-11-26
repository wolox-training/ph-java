package wolox.training.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wolox.training.models.Book;

@Repository
public interface BookRepository  extends JpaRepository<Book, Long> {
    Optional<Book> findBookByAuthor(String author);

     Optional<Book> findByTitle(String bookTitle);


     /**
     * This Method to allow find a book by publisher, genre and year
     *
     * @param publisher param  to execute query
     * @param genre     param  to execute query
     * @param year      param  to execute query
     * @return return a book with specified parameters
     */
    List<Book> findByPublisherAndGenreAndYear(String publisher,
            String genre,
            String year);

    /**
     * This Method to allow find a book by publisher, genre and year
     *
     * @param publisher param  to execute query
     * @param genre     param  to execute query
     * @param year      param  to execute query
     * @return return a book with specified parameters
     */
    @Query("SELECT a.publisher, a.genre, a.year from Book a "
            + " WHERE (a.publisher = :publisher OR :publisher is null)"
            + " AND (a.genre = :genre OR :genre is null)"
            + " AND (a.year = :year OR :year is null)")
    List<Book> findByPublisherAndGenreAndYearQuery(
            @Param("publisher") String publisher,
            @Param("genre") String genre,
            @Param("year") String year);

    /**
     * Method to  allow search a book by isbn paran
     * @param isbn param to search book in external api
     * @return
     */
    Optional<Book> findBookByIsbn(String isbn);

    /**
     * Method to search book by some params of theses
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
     * @return return a book with specified parameters
     */
    @Query("SELECT b FROM Book b "
            + "WHERE  (UPPER(b.isbn) LIKE UPPER(:isbn) OR :isbn = '')"
            + "AND (UPPER(b.genre) LIKE UPPER(:genre) OR :genre = '') "
            + "AND (UPPER(b.author) LIKE UPPER(:author) OR :author = '') "
            + "AND (UPPER(b.image) LIKE UPPER(:image) OR :image = '') "
            + "AND (UPPER(b.title) LIKE UPPER(:title) OR :title = '') "
            + "AND (UPPER(b.subtitle) LIKE UPPER(:subtitle) OR :subtitle = '') "
            + "AND (UPPER(b.publisher) LIKE UPPER(:publisher) OR :publisher = '') "
            + "AND  ((b.year BETWEEN :initialYear AND :finalYear) "
            + "       OR ( b.year >= :initialYear AND :finalYear = '') "
            + "       OR ( b.year <= :finalYear AND :initialYear = '')) "
            + "AND (b.pages = :pages  OR :pages = '' ) ")
    List<Book> findByAllParameters(
            @Param("genre") String genre,
            @Param("author") String author,
            @Param("image") String image,
            @Param("title") String title,
            @Param("subtitle") String subtitle,
            @Param("publisher") String publisher,
            @Param("initialYear") String initialYear,
            @Param("finalYear") String finalYear,
            @Param("pages") int pages,
            @Param("isbn") String isbn);



}
