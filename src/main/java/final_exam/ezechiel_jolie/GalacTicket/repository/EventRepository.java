package final_exam.ezechiel_jolie.GalacTicket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import final_exam.ezechiel_jolie.GalacTicket.model.Event;
@Repository
public interface EventRepository  extends JpaRepository <Event, Long>{
 // Method to find an event by its ID
   //event findById(Long id);
    
    // Method to check if an event exists by its ID
    boolean existsById(Long id);
}
