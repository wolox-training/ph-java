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
     * Method to search book by some params of theses
     *
     * @param genre     variable to search object
     * @param author    variable to search object
     * @param image     variable to search object
     * @param title     variable to search object
     * @param subtitle  variable to search object
     * @param publisher variable to search object
     * @param startYear variable to search object
     * @param endYear   variable to search object
     * @param pages     variable to search object
     * @param isbn      variable to search object
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
            + "AND  ((b.year BETWEEN :startYear AND :endYear) "
            + "       OR ( b.year >= :startYear AND :endYear = '') "
            + "       OR ( b.year <= :endYear AND :startYear = '')) "
            + "AND (b.pages = :pages  OR :pages = '' ) ")
    Page<Book> findByAllParameters(
            @Param("genre") String genre,
            @Param("author") String author,
            @Param("image") String image,
            @Param("title") String title,
            @Param("subtitle") String subtitle,
            @Param("publisher") String publisher,
            @Param("startYear") String startYear,
            @Param("endYear") String endYear,
            @Param("pages") int pages,
            @Param("isbn") String isbn,
            Pageable pageable);
}
