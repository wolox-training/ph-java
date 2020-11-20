package wolox.training.model;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.User;
import wolox.training.repositories.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserModelIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;


    @Test
    public void whenFindByName_thenReturnUser() {
        // given
        User pablo = new User("pablo");
        entityManager.persist(pablo);
        entityManager.flush();

        // when
        User found = userRepository.findByName(pablo.getName());

        // then
        assertThat(found.getName())
                .isEqualTo(pablo.getName());
    }

    @BeforeAll
    public void setUp() {
        User pablo = new User("pablo");

        Mockito.when(userRepository.findByName(pablo.getName()))
                .thenReturn(pablo);
    }

    @Test
    @Rollback(false)
    public void testUpdateUser() {
        User user = userRepository.findByName("pablo");
        user.setName("Andres");

        userRepository.save(user);

        User updatedProduct = userRepository.findByName("pablo");

        assertThat(updatedProduct.getName()).isEqualTo("Pablo");
    }

    @Test
    @Rollback(false)
    public void testDeleteUser() {
        User user = userRepository.findByName("pablo");

        userRepository.deleteById(user.getId());

        User deletedUser = userRepository.findByName("pablo");

        assertThat(deletedUser).isNull();

    }

}
