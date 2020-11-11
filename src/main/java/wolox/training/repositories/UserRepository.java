package wolox.training.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wolox.training.models.User;

/**
 * Interface that contain the definition of methods for User Model
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * this method finds an user by username like param
     * @param username the user to find
     * @return an user with its information.
     */
    Optional<User> findUserByUsername(String username);
}
