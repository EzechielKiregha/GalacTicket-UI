package final_exam.ezechiel_jolie.GalacTicket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import final_exam.ezechiel_jolie.GalacTicket.model.Customer;
import final_exam.ezechiel_jolie.GalacTicket.model.Event;
import final_exam.ezechiel_jolie.GalacTicket.model.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // Method to find Reservation  by its ID
    // Reservation findByReservationId(Long id);
    // Method to find reservations by customer
    List<Reservation> findByCustomer(Customer customer);
    
    boolean existsById(Long id);

    // Method to find reservations by customer and event
    List<Reservation> findByCustomerAndEvent(Customer customer, Event event);

    // Method to find reservations by status
    List<Reservation> findByStatus(String status);

    // Method to find reservations by customer and status
    List<Reservation> findByCustomerAndStatus(Customer customer, String status);

    // Method to find reservations by event
    List<Reservation> findByEvent(Event event);

    // Method to find reservations by event and status
    List<Reservation> findByEventAndStatus(Event event, String status);

}

