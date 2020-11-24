package wolox.training.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    /**
     * this method finds an user by name like param
     * @param name the name  user to find.
     * @return
     */
    User findByName(String name);

    /**
     * Method to find users by Initial range, final range and  name
     *
     * @param initialDate param to find  by Initial birthdate day
     * @param finalDate   param to find  by final birthdate day
     * @param name      param to find by name
     * @return return a user with specified parameters
     */
    @Query("SELECT a.name FROM User a"
            + " WHERE ( a.birthdate >= :initialDate OR cast(:initialDate as date) is null)"
            + " OR ( a.birthdate <= :finalDate OR cast(:finalDate as date) is null)"
            + " OR (:name = '' OR UPPER(a.name) LIKE UPPER(:name))")
    List<User> findByBirthdateDatesAndName(
            @Param("initialDate") LocalDate initialDate,
            @Param("finalDate") LocalDate finalDate,
            @Param("name") String name);
}
