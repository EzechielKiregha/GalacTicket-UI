package final_exam.ezechiel_jolie.GalacTicket.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import final_exam.ezechiel_jolie.GalacTicket.model.AccessKey;

public interface AccessKeyRepository extends JpaRepository<AccessKey, Integer> {
    @Query("""
        select t from AccessKey t inner join User u on t.user.id = u.id
        where t.user.id = :userId and t.loggedOut = false
        """)
    List<AccessKey> findAllTokensByUser(Long userId);

    Optional<AccessKey> findByToken(String token);
}
