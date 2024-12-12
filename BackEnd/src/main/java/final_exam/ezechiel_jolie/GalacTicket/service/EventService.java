package final_exam.ezechiel_jolie.GalacTicket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import final_exam.ezechiel_jolie.GalacTicket.feedback.Feedback;
import final_exam.ezechiel_jolie.GalacTicket.model.Event;
import final_exam.ezechiel_jolie.GalacTicket.repository.EventRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Feedback saveEvent(Event event) {
        if (event == null) {
            return new Feedback("Invalid event data");
        }

        Event savedEvent = eventRepository.save(event);
        if (savedEvent != null) {
            return new Feedback("Event saved successfully");
        } else {
            return new Feedback("Failed to save event");
        }
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Feedback updateEvent(Long id, Event updatedEvent) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isEmpty()) {
            return new Feedback("Event not found");
        }

        Event existingEvent = optionalEvent.get();
        existingEvent.setName(updatedEvent.getName());
        existingEvent.setDate(updatedEvent.getDate());
        existingEvent.setVenue(updatedEvent.getVenue());
        existingEvent.setDescription(updatedEvent.getDescription());
        existingEvent.setCustomers(updatedEvent.getCustomers());
        existingEvent.setTickets(updatedEvent.getTickets());
        existingEvent.setHeldReservations(updatedEvent.getHeldReservations());

        eventRepository.save(existingEvent);
        return new Feedback("Event updated successfully");
    }

    public Feedback deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            return new Feedback("Event not found");
        }

        eventRepository.deleteById(id);
        return new Feedback("Event deleted successfully");
    }
}
