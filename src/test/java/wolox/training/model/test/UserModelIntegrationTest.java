package wolox.training.model.test;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
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
    public void whenFindByName_thenReturnBook() {
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

    @Before
    public void setUp() {
        User pablo = new User("pablo");

        Mockito.when(userRepository.findByName(pablo.getName()))
                .thenReturn(pablo);
    }
}
