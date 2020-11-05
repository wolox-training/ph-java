package wolox.training.models;

import com.sun.istack.NotNull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private String genre;
    @NotNull
    @Column(nullable = false)
    private String autor;
    @NotNull
    @Column(nullable = false)
    private String image;
    @NotNull
    @Column(nullable = false)
    private String title;
    @NotNull
    @Column(nullable = false)
    private String subtitle;
    @NotNull
    @Column(nullable = false)
    private String publisher;
    @NotNull
    @Column(nullable = false)
    private String year;
    @NotNull
    @Column(nullable = false)
    private Integer pages;
    @NotNull
    @Column(nullable = false, unique =true)
    private String isbn;

    public Book() {

    }

    public Book(String genre, String autor, String image, String title, String subtitle, String publisher, String year, Integer pages, String isbn) {
this.genre = genre;
        this.autor = autor;
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
        genre = genre;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
