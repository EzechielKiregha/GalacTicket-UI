package final_exam.ezechiel_jolie.GalacTicket.controller;

import final_exam.ezechiel_jolie.GalacTicket.feedback.Feedback;
import final_exam.ezechiel_jolie.GalacTicket.model.Venue;
import final_exam.ezechiel_jolie.GalacTicket.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/venues")
public class VenueController {

    @Autowired
    private VenueService venueService;

    // Create a new venue
    @PostMapping
    public ResponseEntity<Feedback> createVenue(@RequestBody Venue venue) {
        Feedback feedback = venueService.saveVenue(venue);
        return ResponseEntity.ok(feedback);
    }

    // Get all venues
    @GetMapping
    public ResponseEntity<List<Venue>> getAllVenues() {
        List<Venue> venues = venueService.getAllVenues();
        return ResponseEntity.ok(venues);
    }

    // Get a venue by ID
    @GetMapping("/{id}")
    public ResponseEntity<Venue> getVenueById(@PathVariable Long id) {
        Venue venue = venueService.getVenueById(id);
        return venue != null ? ResponseEntity.ok(venue) : ResponseEntity.notFound().build();
    }

    // Get venues by location
    @GetMapping("/location/{location}")
    public ResponseEntity<List<Venue>> getVenuesByLocation(@PathVariable String location) {
        List<Venue> venues = venueService.findVenuesByLocation(location);
        return ResponseEntity.ok(venues);
    }

    // Get venues with capacity greater than or equal to a specific value
    @GetMapping("/capacity/min/{capacity}")
    public ResponseEntity<List<Venue>> getVenuesByMinCapacity(@PathVariable int capacity) {
        List<Venue> venues = venueService.findVenuesWithCapacityGreaterThanOrEqual(capacity);
        return ResponseEntity.ok(venues);
    }

    // Get venues with capacity less than a specific value
    @GetMapping("/capacity/max/{capacity}")
    public ResponseEntity<List<Venue>> getVenuesByMaxCapacity(@PathVariable int capacity) {
        List<Venue> venues = venueService.findVenuesWithCapacityLessThan(capacity);
        return ResponseEntity.ok(venues);
    }

    // Update a venue
    @PutMapping("/{id}")
    public ResponseEntity<Feedback> updateVenue(@PathVariable Long id, @RequestBody Venue updatedVenue) {
        Feedback feedback = venueService.updateVenue(id, updatedVenue);
        return ResponseEntity.ok(feedback);
    }

    // Delete a venue
    @DeleteMapping("/{id}")
    public ResponseEntity<Feedback> deleteVenue(@PathVariable Long id) {
        Feedback feedback = venueService.deleteVenue(id);
        return ResponseEntity.ok(feedback);
    }
}
