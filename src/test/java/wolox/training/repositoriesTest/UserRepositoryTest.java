package wolox.training.repositoriesTest;



import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import wolox.training.entitiesTest.EntitiesTest;
import wolox.training.models.User;
import wolox.training.repositories.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private static User userTest;
    private static List<User> userTests;

    @BeforeAll
    static void setUp() {
        userTest = EntitiesTest.mockOneUser();
        userTests = EntitiesTest.mockManyUsers();
    }

    @Test
    void whenCallFindAllUserThenReturnAllList() {
        userRepository.saveAll(userTests);
        List<User> userList = userRepository.findAll();
        assertEquals(userList.get(0).getName(), userTests.get(0).getName());
        assertEquals(userList.get(0).getId(), userTests.get(0).getId());
    }

    @Test
    void whenUpdateUserThenReturnUserUpdated() {
        userRepository.save(userTest);
        userTest.setName("pablo");
        userRepository.save(userTest);
        Optional<User> persistedUser = userRepository.findById(userTest.getId());
        assertEquals(userTest.getName(), persistedUser.get().getName());
    }

    @Test
    void whenCallFindAllByBirthdayAndNameThenReturnListUser() {
        LocalDate initialDate = LocalDate.of(1991, 02, 27);
        LocalDate finalDate = LocalDate.of(2020, 11, 23);
        userRepository.save(userTest);
        List<User> listUsers = userRepository.findByBirthdateDatesAndName(initialDate, finalDate, userTest.getName());
        assertEquals(listUsers.iterator().next().getBirthdate(), userTest.getBirthdate());
    }

}
