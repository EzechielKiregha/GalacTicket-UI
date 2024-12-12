package final_exam.ezechiel_jolie.GalacTicket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import final_exam.ezechiel_jolie.GalacTicket.model.Customer;
import final_exam.ezechiel_jolie.GalacTicket.model.Event;
import final_exam.ezechiel_jolie.GalacTicket.model.Ticket;

// import java.util.List;

@Repository
public interface GalacTicketRepository extends JpaRepository<Ticket, Long> {
    // Method to find a ticket by its ID
    Ticket findTicketByTicketId(Long id);
    
    boolean existsByTicketId(Long id);
    // Method to find tickets by event
    List<Ticket> findByRelatedEvent(Event event);
    // Method to find tickets by customer
    List<Ticket> findByPurchaser(Customer customer);

    // Method to find tickets by event and customer
    List<Ticket> findByRelatedEventAndPurchaser(Event event, Customer customer);

    // Method to find tickets by status
    List<Ticket> findByCurrentStatus(String status);

    // Method to find tickets by event and status
    List<Ticket> findByRelatedEventAndCurrentStatus(Event event, String status);

    // Method to find tickets by customer and status
    List<Ticket> findByPurchaserAndCurrentStatus(Customer customer, String status);

    // Method to find tickets by event, customer, and status
    List<Ticket> findByRelatedEventAndPurchaserAndCurrentStatus(Event event, Customer customer, String status);

    // Method to check if a ticket already exists for a specific event and customer
    boolean existsByRelatedEventAndPurchaser(Event event, Customer customer);

    // You can add more custom query methods as needed

}

