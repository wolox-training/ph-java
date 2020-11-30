package wolox.training.entitiesTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import wolox.training.models.Book;
import wolox.training.models.User;

public class EntitiesTest {

    public static User mockOneUser() {
        User user = new User();
        user.setUsername("pablohincapie");
        user.setName("pablo");
        user.setBirthdate(LocalDate.of(2020, 02, 27));
        user.setPassword("123");
        return user;
    }

    public static User mockUserPersist() {
        User SecondUser = new User();
        SecondUser.setUsername("pablo.hincapie");
        SecondUser.setName("Pablo hincapie");
        SecondUser.setBirthdate(LocalDate.of(2019, 02, 27));
        SecondUser.setPassword("123");
        return SecondUser;
    }

    public static Book mockBook() {
        Book book = new Book();
        book.setGenre("Romanticismo");
        book.setAuthor("Pablo Neruda");
        book.setImage("prueba image");
        book.setTitle("Siglo 21");
        book.setSubtitle("the best way");
        book.setPublisher("colombia publicacion");
        book.setYear("2020");
        book.setPages(120);
        book.setIsbn("1234674");
        return book;
    }

    public static Book mockBookPersist() {
        Book secondBook = new Book();
        secondBook.setGenre("Accion");
        secondBook.setAuthor("Benedeti");
        secondBook.setImage("imagen book");
        secondBook.setTitle("particularidad del siglo");
        secondBook.setSubtitle("curiosidades");
        secondBook.setPublisher("colombia publicacion");
        secondBook.setYear("2021");
        secondBook.setPages(450);
        secondBook.setIsbn("45465624");
        return secondBook;
    }

    public static List<User> mockManyUsers() {
        List<User> manyUsersTest = new ArrayList<>();
        manyUsersTest.add(mockOneUser());
        return manyUsersTest;
    }

    public static List<Book> mockManyBooks() {
        List<Book> manyBooksTest = new ArrayList<>();
        manyBooksTest.add(mockBook());
        return manyBooksTest;
    }

    public static User mockSecondUser() {
        User secondUserTest = new User();
        secondUserTest.setUsername("pablohincapie");
        secondUserTest.setName("Pablo Hincapie");
        secondUserTest.setBirthdate(LocalDate.of(1991, 02, 27));
        secondUserTest.setPassword("123");
        return secondUserTest;
    }

}
