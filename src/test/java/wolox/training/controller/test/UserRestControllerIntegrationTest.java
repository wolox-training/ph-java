package wolox.training.controller.test;

import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import static java.util.Collections.get;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sun.nio.ch.Reflect.get;


import com.sun.javafx.tools.ant.Application;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.controllers.UserController;
import wolox.training.models.User;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)

@SpringBootTest(
        SpringBootTest.WebEnvironment.MOCK,
        classes = Application.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class UserRestControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void givenUser_whenGetUser_thenReturnJsonArray()
            throws Exception {

        User pablo = new User("pablo");

        List<User> allEmployees = Arrays.asList(pablo);

        mvc.perform(get("/api/user")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(pablo.getName())));
    }

    @Test
    public void givenUser_whenGetUser_thenStatus200()
            throws Exception {

       // createTestUser("pablo");

        mvc.perform(get("/api/user")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("pablo")));
    }
}
