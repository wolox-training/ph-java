package wolox.training.controllersTest;


import static org.assertj.core.api.BDDAssumptions.given;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.constants.ErrorConstants;
import wolox.training.controllers.BookController;
import wolox.training.entitiesTest.EntitiesTest;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;


import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookRepository bookRepository;

    private static Book bookTest;
    private static List<Book> bookTests;
    private static final String PATH = "/api/books";

    @BeforeAll
    static void setUp() {
        bookTests = EntitiesTest.mockManyBooks();
        bookTest = EntitiesTest.mockBook();

    }

    @WithMockUser(value = "pablo")
    @Test
    @DisplayName("Test find all book ,return status OK")
    void whenFindBookByIdThenReturnStatusOK() throws Exception {
        given(bookRepository.findById(1L)).withFailMessage(ErrorConstants.SUCCESS_CORRECT);
        String url = (PATH + "/1");
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithMockUser(value = "pablo")
    @Test
    @DisplayName("Test, book is searched for its id,it return status not found")
    void whenBookThatNotExistsThenReturnNotFound() throws Exception {
        given(bookRepository.findById(1L)).withFailMessage(ErrorConstants.NOT_EXIST_ID);
        String url = (PATH + "/1");
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test , book is created , it return status Created")
    void whenCreateBookThenReturnStatusCreated() throws Exception {
        String json = new ObjectMapper().writeValueAsString(bookTest);
        String url = PATH;
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @WithMockUser(value = "pablo")
    @Test
    @DisplayName("Test, book is updated , it return status OK")
    void whenUpdateBookThenReturnStatusCreated() throws Exception {
        given(bookRepository.findById(1L)).withFailMessage(ErrorConstants.SUCCESS_CORRECT);
        String json = new ObjectMapper().writeValueAsString(bookTest);
        String url = (PATH + "/1");
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(value = "pablo")
    @Test
    @DisplayName("Test, book is updated , it return status No Found")
    void whenUpdateBookThenReturnStatusNoFound() throws Exception {
        given(bookRepository.findById(1L)).withFailMessage(ErrorConstants.NOT_EXIST_ID);
        String json = new ObjectMapper().writeValueAsString(bookTest);
        String url = (PATH + "/1");
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @WithMockUser(value = "pablo")
    @Test
    @DisplayName("Test, book is deleted , it return status No Content")
    void whenDeleteBookThenReturnStatusNoContent() throws Exception {
        given(bookRepository.findById(1L)).withFailMessage(ErrorConstants.NOT_EXIST_ID);
        String url = (PATH + "/1");
        mvc.perform(delete(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @WithMockUser(value = "pablo")
    @Test
    @DisplayName("Test, When a book is deleted , it return status No Found")
    void whenDeleteBookThenReturnStatusNoFound() throws Exception {
        given(bookRepository.findById(1L)).withFailMessage(ErrorConstants.NOT_EXIST_ID);
        String url = (PATH + "/1");
        mvc.perform(delete(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @WithMockUser(value = "pablo")
    @Test
    @DisplayName("Test , find info by different param ,it return status OK")
    void whenFindByAllParametersThenReturnStatusOK() throws Exception {
        Pageable pageable = PageRequest.of(1, 4);
        List<Book> books = new ArrayList<>();
        books.add(bookTest);
        Page<Book> bookPage = new PageImpl<>(books);
        given(bookRepository.findByAllParameters("genre", "author", "image", "title", "subtitle", "publisher", "startYear", "endYear", 450, "2020", pageable)).withFailMessage(ErrorConstants.NOT_EXIST_ID);
        String url = (PATH + "?genre=genre&author=author&image=image&title=title&subtitle=subtitle&publisher=publisher&startYear=10&endYear=2019&pages=22&isbn=22&page=1&size=4");
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithMockUser(value = "pablo")
    @Test
    @DisplayName("Test , When a book is seached by publisher , genre and year ,it return status OK")
    void whenFindByPublisherGenreAndYearThenReturnStatusOK() throws Exception {
        List<Book> listBooks = new ArrayList<>();
        given(bookRepository.findByPublisherAndGenreAndYear(bookTest.getPublisher(), bookTest.getGenre(), bookTest.getYear())).withFailMessage(ErrorConstants.NOT_EXIST_ID);
        String url = (PATH + "/findByParams?publisher=publisher&genre=genre&year=year");
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}

