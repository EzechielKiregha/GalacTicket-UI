package final_exam.ezechiel_jolie.GalacTicket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import final_exam.ezechiel_jolie.GalacTicket.feedback.Feedback;
import final_exam.ezechiel_jolie.GalacTicket.model.Venue;
import final_exam.ezechiel_jolie.GalacTicket.repository.VenueRepository;

import java.util.List;
import java.util.Optional;

@Service
public class VenueService {

    @Autowired
    private VenueRepository venueRepository;

    public Feedback saveVenue(Venue venue) {
        if (venue == null) {
            return new Feedback("Invalid venue data");
        }

        if (venueRepository.existsByName(venue.getName())) {
            return new Feedback("Venue with the same name already exists");
        }

        Venue savedVenue = venueRepository.save(venue);
        return new Feedback(savedVenue != null ? "Venue saved successfully" : "Failed to save venue");
    }

    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

    public Venue getVenueById(Long id) {
        return venueRepository.findVenueById(id);
    }

    public List<Venue> findVenuesByLocation(String location) {
        return venueRepository.findByLocation(location);
    }

    public List<Venue> findVenuesWithCapacityGreaterThanOrEqual(int capacity) {
        return venueRepository.findByCapacityGreaterThanEqual(capacity);
    }

    public List<Venue> findVenuesWithCapacityLessThan(int capacity) {
        return venueRepository.findByCapacityLessThan(capacity);
    }

    public Feedback updateVenue(Long id, Venue updatedVenue) {
        Optional<Venue> optionalVenue = venueRepository.findById(id);
        if (optionalVenue.isEmpty()) {
            return new Feedback("Venue not found");
        }

        Venue existingVenue = optionalVenue.get();
        existingVenue.setName(updatedVenue.getName());
        existingVenue.setLocation(updatedVenue.getLocation());
        existingVenue.setCapacity(updatedVenue.getCapacity());
        existingVenue.setDescription(updatedVenue.getDescription());

        venueRepository.save(existingVenue);
        return new Feedback("Venue updated successfully");
    }

    public Feedback deleteVenue(Long id) {
        if (!venueRepository.existsById(id)) {
            return new Feedback("Venue not found");
        }

        venueRepository.deleteById(id);
        return new Feedback("Venue deleted successfully");
    }
}
