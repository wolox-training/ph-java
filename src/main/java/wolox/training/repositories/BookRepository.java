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





}
