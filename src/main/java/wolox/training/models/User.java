package wolox.training.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.Preconditions;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import wolox.training.constants.ErrorConstants;
import wolox.training.exceptions.BookAlreadyOwnedException;
/**
 * Entity  for table User
 */
@Entity
@Table(name = "users")
@ApiModel("Model User")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
    @SequenceGenerator(name = "USER_SEQ", sequenceName ="USER_SEQ")
    @ApiModelProperty(notes ="The user id")
    private long id;
    @NotNull
    @Column(nullable = false)
    @ApiModelProperty(notes ="The user's username", required = true)
    private String username;
    @NotNull
    @Column(nullable = false)
    @ApiModelProperty(notes ="The user's name", required = true)
    private String name;
    @NotNull
    @Column(nullable = false)
    @ApiModelProperty(notes ="The user's birthdate", required = true)
    private LocalDate birthdate;

    /**
     * Field for relation user-book
     */
    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    private List<Book> books;

    public User() {
        this.books = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        Preconditions.checkNotNull(username, ErrorConstants.NULL_FIELD_USERNAME);
        Preconditions.checkArgument(!username.isEmpty(), ErrorConstants.EMPTY_FIELD_USERNAME);
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Preconditions.checkNotNull(name, ErrorConstants.NULL_FIELD_NAME);
        Preconditions.checkArgument(!name.isEmpty(), ErrorConstants.EMPTY_FIELD_NAME);
        this.name = name;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        Preconditions.checkNotNull(birthdate, ErrorConstants.NULL_FIELD_BIRTHDAY);
        Preconditions.checkArgument(!birthdate.isAfter(LocalDate.now()),ErrorConstants.NOW_FIELD_BIRTHDAY);
        this.birthdate = birthdate;
    }

    public List<Book> getBooks() {
        return (List<Book>) Collections.unmodifiableList(books);
    }

    public void addBook(Book book) throws BookAlreadyOwnedException {
        if (books.contains(book)) {
            throw new BookAlreadyOwnedException(ErrorConstants.EXIST_BOOK);
        }
        books.add(book);
    }

    public void removeBook(Book book) {
        if(!books.contains(book)){
            throw new BookAlreadyOwnedException(ErrorConstants.NOT_EXIST_BOOK_DELETE);
        }else{
            books.remove(book);
        }
    }
}
