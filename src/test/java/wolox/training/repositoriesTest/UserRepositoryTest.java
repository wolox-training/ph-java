package wolox.training.repositoriesTest;


import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import wolox.training.entitiesTest.EntitiesTest;
import wolox.training.models.User;
import wolox.training.repositories.UserRepository;


@DataJpaTest
class UserRepositoryTest {

    private static User userTest;
    private static List<User> userTests;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

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

}
