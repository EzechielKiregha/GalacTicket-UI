package final_exam.ezechiel_jolie.GalacTicket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import final_exam.ezechiel_jolie.GalacTicket.model.Customer;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Check if a customer exists by email (for duplicate prevention)
    boolean existsByEmail(String email);

    // Find a customer by email (useful for login or profile lookup)
    Customer findByEmail(String email);

    // Find customers by name (partial matching for search functionality)
    List<Customer> findByFullNameContainingIgnoreCase(String name);

    // Find customers by contactNumber number
    Customer findByContactNumber(String contactNumber);

    // Check if a customer exists by contactNumber
    boolean existsByContactNumber(String contactNumber);

    // Find customers who have made reservations (potentially link to Reservation entity later)
    List<Customer> findByHeldReservationsIsNotEmpty();

    // Find customers by email (useful for filtering in specific locations)
    List<Customer> findByEmailContainingIgnoreCase(String email);
}
