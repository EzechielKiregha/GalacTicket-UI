package final_exam.ezechiel_jolie.GalacTicket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import final_exam.ezechiel_jolie.GalacTicket.feedback.Feedback;
import final_exam.ezechiel_jolie.GalacTicket.model.Customer;
import final_exam.ezechiel_jolie.GalacTicket.model.Event;
import final_exam.ezechiel_jolie.GalacTicket.model.Ticket;
import final_exam.ezechiel_jolie.GalacTicket.repository.GalacTicketRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GalacTicketService {

    @Autowired
    private GalacTicketRepository ticketRepository;

    public Feedback saveTicket(Ticket ticket) {
        if (ticket == null) {
            return new Feedback("Invalid ticket data");
        }

        if (ticket.getTicketId() != null && ticketRepository.existsByTicketId(ticket.getTicketId())) {
            return new Feedback("Ticket with ID " + ticket.getTicketId() + " already exists");
        }

        Ticket savedTicket = ticketRepository.save(ticket);
        if (savedTicket != null) {
            return new Feedback("Ticket saved successfully");
        } else {
            return new Feedback("Failed to save ticket");
        }
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Feedback updateTicket(Long id, Ticket updatedTicket) {
        if (updatedTicket == null) {
            return new Feedback("Invalid ticket data");
        }

        Optional<Ticket> optionalTicket = ticketRepository.findById(id);
        if (optionalTicket.isEmpty()) {
            return new Feedback("Ticket not found");
        }

        Ticket existingTicket = optionalTicket.get();
        existingTicket.setCategory(updatedTicket.getCategory());
        existingTicket.setCost(updatedTicket.getCost());
        existingTicket.setCurrentStatus(updatedTicket.getCurrentStatus());

        ticketRepository.save(existingTicket);
        return new Feedback("Ticket updated successfully");
    }

    public Ticket getTicketByTicketId(Long id) {
        Optional<Ticket> optionalTicket = ticketRepository.findById(id);
        return optionalTicket.orElse(null);
    }

    public List<Ticket> getTicketsByRelatedEvent(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }
        return ticketRepository.findByRelatedEvent(event);
    }

    public List<Ticket> getTicketsByPurchaser(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        return ticketRepository.findByPurchaser(customer);
    }

    public List<Ticket> getTicketsByRelatedEventAndCustomer(Event event, Customer customer) {
        if (event == null || customer == null) {
            throw new IllegalArgumentException("Event and Customer cannot be null");
        }
        return ticketRepository.findByRelatedEventAndPurchaser(event, customer);
    }

    public List<Ticket> getTicketsByCurrentStatus(String status) {
        if (status == null || status.isEmpty()) {
            throw new IllegalArgumentException("Status cannot be null or empty");
        }
        return ticketRepository.findByCurrentStatus(status);
    }

    public boolean deleteTicket(Long id) {
        if (!ticketRepository.existsById(id)) {
            return false;
        }
        ticketRepository.deleteById(id);
        return true;
    }
}
