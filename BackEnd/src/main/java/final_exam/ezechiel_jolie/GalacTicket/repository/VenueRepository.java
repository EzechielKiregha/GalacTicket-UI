package final_exam.ezechiel_jolie.GalacTicket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import final_exam.ezechiel_jolie.GalacTicket.model.Venue;

import java.util.List;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {

    // Method to find a venue by its ID
    Venue findVenueById(Long id);

    // Method to check if a venue exists by name
    boolean existsByName(String name);

    // Method to check if a venue exists by ID (already covered by JpaRepository)
    boolean existsById(Long id);

    // Method to find venues by location
    List<Venue> findByLocation(String location);

    // Method to find venues with capacity greater than or equal to the given capacity
    List<Venue> findByCapacityGreaterThanEqual(int capacity);

    // Method to find venues with capacity less than the given capacity
    List<Venue> findByCapacityLessThan(int capacity);

    // Method to find venues hosting events
    // Assuming there’s a relationship with an `Event` entity
    List<Venue> findByEventsIsNotEmpty();

    // Method to find venues by name and location
    List<Venue> findByNameAndLocation(String name, String location);
}

