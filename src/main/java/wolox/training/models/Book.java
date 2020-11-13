package wolox.training.models;

import static com.google.common.base.Preconditions.checkArgument;

import org.apache.commons.lang3.StringUtils;
import com.google.common.base.Preconditions;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import wolox.training.constants.ErrorConstants;

/**
 * Entity  for table Book
 */
@Entity
@Table(name = "books")
@ApiModel("Model Book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOOK_SEQ")
    @SequenceGenerator(name = "BOOK_SEQ", sequenceName ="BOOK_SEQ")
    @ApiModelProperty(notes ="The book id")
    private long id;
    @Column
    @ApiModelProperty(notes ="The book genre: could be horror, comedy,drama,etc.")
    private String genre;
    @NotNull
    @Column(nullable = false)
    @ApiModelProperty(notes ="The book's author", required = true)
    private String author;
    /**
     * Field that save visual design of a book
     */
    @NotNull
    @Column(nullable = false)
    @ApiModelProperty(notes ="The book's image", required = true)
    private String image;
    @NotNull
    @Column(nullable = false)
    @ApiModelProperty(notes ="The book's title", required = true)
    private String title;
    @NotNull
    @Column(nullable = false)
    @ApiModelProperty(notes ="The book's subtitle", required = true)
    private String subtitle;
    @NotNull
    @Column(nullable = false)
    @ApiModelProperty(notes ="The book's publisher", required = true)
    private String publisher;
    @NotNull
    @Column(nullable = false)
    @ApiModelProperty(notes ="The book's year", required = true)
    private String year;
    @NotNull
    @Column(nullable = false)
    @ApiModelProperty(notes ="The book's pages", required = true)
    private Integer pages;
    /**
     * Identity unique for a book include for the same tittle
     */
    @NotNull
    @Column(nullable = false, unique =true)
    @ApiModelProperty(notes ="The book's isbn", required = true)
    private String isbn;

    @ManyToMany(mappedBy = "books")
    private List <User> users = new ArrayList<>();

    public Book() {
        super();
    }
    public Book(String genre, String author, String image, String title, String subtitle, String publisher, String year, Integer pages, String isbn) {
        this.genre = genre;
        this.author = author;
        this.image = image;
        this.title = title;
        this.subtitle = subtitle;
        this.publisher = publisher;
        this.year = year;
        this.pages = pages;
        this.isbn = isbn;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        Preconditions.checkNotNull(genre, ErrorConstants.NULL_FIELD_GENRE);
        checkArgument(!genre.isEmpty(), ErrorConstants.EMPTY_FIELD_GENRE);
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        Preconditions.checkNotNull(author, ErrorConstants.NULL_FIELD_AUTHOR);
        checkArgument(!author.isEmpty(), ErrorConstants.EMPTY_FIELD_AUTHOR);
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        Preconditions.checkNotNull(image);
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        Preconditions.checkNotNull(title, ErrorConstants.NULL_FIELD_TITLE);
        checkArgument(!title.isEmpty(), ErrorConstants.EMPTY_FIELD_TITLE);
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        Preconditions.checkNotNull(subtitle, ErrorConstants.NULL_FIELD_SUBTITLE);
        checkArgument(!subtitle.isEmpty(), ErrorConstants.EMPTY_FIELD_SUBTITLE);
        this.subtitle = subtitle;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        Preconditions.checkNotNull(publisher, ErrorConstants.NULL_FIELD_PUBLISHER);
        checkArgument(!publisher.isEmpty(), ErrorConstants.EMPTY_FIELD_PUBLISHER);
        this.publisher = publisher;
    }

    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        Preconditions.checkNotNull(year, ErrorConstants.NULL_FIELD_YEAR);
        Preconditions.checkArgument(!year.isEmpty(), ErrorConstants.EMPTY_FIELD_YEAR);
        Preconditions.checkArgument(StringUtils.isNumeric(year), ErrorConstants.NUMERIC_FIELD_YEAR);
        this.year = year;
    }
    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        Preconditions.checkNotNull(pages, ErrorConstants.NULL_FIELD_PAGES);
        Preconditions.checkArgument(pages <= 0, ErrorConstants.EMPTY_FIELD_PAGES);

        this.pages = pages;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        Preconditions.checkNotNull(isbn, ErrorConstants.NULL_FIELD_ISBN);
        Preconditions.checkArgument(! isbn.isEmpty(), ErrorConstants.EMPTY_FIELD_ISBN);
        Preconditions.checkArgument(StringUtils.isNumeric(isbn), ErrorConstants.NUMERIC_FIELD_ISBN);
        this.isbn = isbn;
    }

    public long getId() {
        return id;
    }
}
