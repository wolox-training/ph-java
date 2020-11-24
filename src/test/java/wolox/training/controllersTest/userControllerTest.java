package wolox.training.controllersTest;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.controllers.UserController;
import wolox.training.entitiesTest.EntitiesTest;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

@WebMvcTest(controllers = UserController.class)
public class userControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    private static User userTest;
    private static User secondUserTest;
    private static Book bookTest;
    private static List<User> userTests;
    private static List<Book> bookTests;
    private static final String PATH = "/api/users";
    private static final String URL = (PATH + "/password/1");
    private static final String URL_REMOVE = (PATH + "/1/remove-books/1");
    private static final String URL_ADD = (PATH + "/1/add-books/1");
    private static final String URL_PARAM = (PATH + "/1");

    @BeforeEach
    static void setUp() {
        userTest = EntitiesTest.mockOneUser();
        userTests = EntitiesTest.mockManyUsers();
        bookTest = EntitiesTest.mockBook();
        secondUserTest = EntitiesTest.mockSecondUser();
        bookTests = new ArrayList<>();
        bookTests.add(bookTest);
    }


    @WithMockUser(value = "pablo")
    @Test
    @DisplayName("Test find all user ,return status OK")
    void whenFindUserByIdThenReturnStatusOK() throws Exception {
        given(userRepository.findById(1L)).willReturn(Optional.of(userTest));
        mvc.perform(get(URL_PARAM)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(userTest.getName()));
    }

    @WithMockUser(value = "pablo")
    @Test
    @DisplayName("Test,user is searched for its id,it return status not found")
    void whenUserThatNotExistsThenReturnNotFound() throws Exception {
        given(userRepository.findById(1L)).willReturn(Optional.empty());
        mvc.perform(get(URL_PARAM)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test , user is created , it return status Created")
    void whenCreateUserThenReturnStatusCreated() throws Exception {
        String json = new ObjectMapper().writeValueAsString(userTest);
        mvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @WithMockUser(value = "pablo")
    @Test
    @DisplayName("Test, user is updated , it return status OK")
    void whenUpdateUserThenReturnStatusCreated() throws Exception {
        given(userRepository.findById(1L)).willReturn(Optional.of(userTest));
        String json = new ObjectMapper().writeValueAsString(userTest);
        mvc.perform(put(URL_PARAM)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(value = "pablo")
    @Test
    @DisplayName("Test, user is updated , it return status No Found")
    void whenUpdateUserThenReturnStatusNoFound() throws Exception {
        given(userRepository.findById(1L)).willReturn(Optional.empty());
        String json = new ObjectMapper().writeValueAsString(userTest);
        mvc.perform(put(URL_PARAM)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @WithMockUser(value = "pablo")
    @Test
    @DisplayName("Test,  user is deleted , it return status No Content")
    void whenDeleteUserThenReturnStatusNoContent() throws Exception {
        given(userRepository.findById(1L)).willReturn(Optional.of(userTest));
        mvc.perform(delete(URL_PARAM)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @WithMockUser(value = "pablo")
    @Test
    @DisplayName("Test, user is deleted , it return status No Found")
    void whenDeleteUserThenReturnStatusNoFound() throws Exception {
        given(userRepository.findById(1L)).willReturn(Optional.empty());
        mvc.perform(delete(URL_PARAM)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @WithMockUser(value = "pablo")
    @Test
    @DisplayName("Test,  book is added , it return status Created")
    void whenAddBookThenReturnStatusCreated() throws Exception {
        given(userRepository.findById(1L)).willReturn(Optional.of(userTest));
        given(bookRepository.findById(1L)).willReturn(Optional.of(bookTest));
        mvc.perform(patch(URL_ADD)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @WithMockUser(value = "pablo")
    @Test
    @DisplayName("Test, book is added and its exists , it return status Conflict")
    void whenAddBookThenReturnStatusConflict() throws Exception {
        given(bookRepository.findById(1L)).willReturn(Optional.of(bookTest));
        mvc.perform(patch(URL_ADD)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @WithMockUser(value = "pablo")
    @Test
    @DisplayName("test,  book is remove , it return status No Content")
    void whenRemoveBookThenReturnStatusNoContent() throws Exception {
        given(userRepository.findById(1L)).willReturn(Optional.of(secondUserTest));
        given(bookRepository.findById(1L)).willReturn(Optional.of(bookTest));
        mvc.perform(patch(URL_REMOVE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @WithMockUser(value = "pablo")
    @Test
    @DisplayName("test,  password is update , it return status OK")
    void whenPasswordIsUpdateThenReturnStatusOK() throws Exception {
        given(userRepository.findById(1L)).willReturn(Optional.of(userTest));
        String json = new ObjectMapper().writeValueAsString(userTest);
        mvc.perform(put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(value = "pablo")
    @Test
    @DisplayName("test, password is update , it return status No Found")
    void whenPasswordIsUpdateThenReturnStatusNoFound() throws Exception {
        given(userRepository.findById(1L)).willReturn(Optional.empty());
        String json = new ObjectMapper().writeValueAsString(userTest);
        mvc.perform(put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @WithMockUser(value = "pablo")
    @Test
    @DisplayName("test, find user by birthdate and name, it return status OK")
    void whenFindUserBirthdateThenReturnStatusOK() throws Exception {
        LocalDate initialDate = LocalDate.of(2015, 5, 13);
        LocalDate finalDate = LocalDate.of(2019, 2, 22);

        List<User> listUsers = new ArrayList<>(); {
        };
        given(userRepository.findByBirthdateDatesAndName(initialDate, finalDate, "pablo")).willReturn(listUsers);
        String url = (PATH + "?initialDate=2015-05-13&finalDate=2019-02-22&name=pablo");
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
