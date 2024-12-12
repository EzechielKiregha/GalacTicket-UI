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
    List<Customer> findByNameContainingIgnoreCase(String name);

    // Find customers by phone number
    Customer findByPhone(String phone);

    // Check if a customer exists by phone
    boolean existsByPhone(String phone);

    // Find customers who have made reservations (potentially link to Reservation entity later)
    List<Customer> findByReservationsIsNotEmpty();

    // Find customers by address (useful for filtering in specific locations)
    List<Customer> findByAddressContainingIgnoreCase(String address);
}
