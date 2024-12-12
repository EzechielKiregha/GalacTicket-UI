package final_exam.ezechiel_jolie.GalacTicket.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import final_exam.ezechiel_jolie.GalacTicket.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsById(Long id);
    User findUserById(Long id);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    User findByEmailAndPassword(String email, String password);
}
