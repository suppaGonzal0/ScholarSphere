package net.therap.scholarsphere.repository;

import net.therap.scholarsphere.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    List<User> findByFollowings_Id(long id);

    List<User> findByFollowers_Id(long id);

    @Query(value = "SELECT u.* FROM users u JOIN authorities a ON u.email = a.email " +
            "WHERE a.authority = 'ROLE_REGULAR'", nativeQuery = true)
    List<User> findAllRegularUsers();

    @Modifying
    @Query(value = "INSERT INTO Authorities (email, authority) VALUES (?1, 'ROLE_REGULAR')", nativeQuery = true)
    void createRegularRole(String email);
}
