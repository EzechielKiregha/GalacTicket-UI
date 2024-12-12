package final_exam.ezechiel_jolie.GalacTicket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import final_exam.ezechiel_jolie.GalacTicket.model.Ticket;
import final_exam.ezechiel_jolie.GalacTicket.feedback.Feedback;
import final_exam.ezechiel_jolie.GalacTicket.model.Customer;
import final_exam.ezechiel_jolie.GalacTicket.model.Event;
import final_exam.ezechiel_jolie.GalacTicket.service.GalacTicketService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/tickets")
public class GalacTicketController {

    @Autowired
    private GalacTicketService ticketService;

    @PostMapping("/save")
    public ResponseEntity<Feedback> saveTicket(@RequestBody Ticket ticket) {
        return ResponseEntity.ok(ticketService.saveTicket(ticket));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        Ticket ticket = ticketService.getTicketByTicketId(id);
        return ticket != null ? ResponseEntity.ok(ticket) : ResponseEntity.notFound().build();
    }

    @GetMapping("/by-event")
    public ResponseEntity<List<Ticket>> getTicketsByRelatedEvent(@RequestBody Event event) {
        return ResponseEntity.ok(ticketService.getTicketsByRelatedEvent(event));
    }

    @GetMapping("/by-customer")
    public ResponseEntity<List<Ticket>> getTicketsByPurchaser(@RequestBody Customer customer) {
        return ResponseEntity.ok(ticketService.getTicketsByPurchaser(customer));
    }

    @GetMapping("/by-event-customer")
    public ResponseEntity<List<Ticket>> getTicketsByRelatedEventAndCustomer(@RequestBody Event event, @RequestBody Customer customer) {
        return ResponseEntity.ok(ticketService.getTicketsByRelatedEventAndCustomer(event, customer));
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<Ticket>> getTicketsByCurrentStatus(@RequestParam String status) {
        return ResponseEntity.ok(ticketService.getTicketsByCurrentStatus(status));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTicket(@PathVariable Long id) {
        return ticketService.deleteTicket(id) ? ResponseEntity.ok("Ticket deleted") : ResponseEntity.notFound().build();
    }
}
