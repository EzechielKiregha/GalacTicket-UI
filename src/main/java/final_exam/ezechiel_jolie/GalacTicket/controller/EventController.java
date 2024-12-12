package final_exam.ezechiel_jolie.GalacTicket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import final_exam.ezechiel_jolie.GalacTicket.feedback.Feedback;
import final_exam.ezechiel_jolie.GalacTicket.model.Event;
import final_exam.ezechiel_jolie.GalacTicket.service.EventService;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping(value = "/save")
    public ResponseEntity<Feedback> saveEvent(@RequestBody Event event) {
        Feedback responseMessage = eventService.saveEvent(event);
        return ResponseEntity.ok(responseMessage);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> eventList = eventService.getAllEvents();
        return ResponseEntity.ok(eventList);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Feedback> updateEvent(@PathVariable Long id, @RequestBody Event updatedEvent) {
        Feedback responseMessage = eventService.updateEvent(id, updatedEvent);
        return ResponseEntity.ok(responseMessage);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Feedback> deleteEvent(@PathVariable Long id) {
        Feedback responseMessage = eventService.deleteEvent(id);
        return ResponseEntity.ok(responseMessage);
    }
}

