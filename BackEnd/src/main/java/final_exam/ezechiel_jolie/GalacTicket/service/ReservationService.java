package final_exam.ezechiel_jolie.GalacTicket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import final_exam.ezechiel_jolie.GalacTicket.feedback.Feedback;
import final_exam.ezechiel_jolie.GalacTicket.model.Reservation;
import final_exam.ezechiel_jolie.GalacTicket.repository.ReservationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public Feedback saveReservation(Reservation reservation) {
        // Check if the reservation object is null
        if (reservation == null) {
            return new Feedback("Invalid reservation data");
        }

        // Save the reservation
        Reservation savedReservation = reservationRepository.save(reservation);
        if (savedReservation != null) {
            return new Feedback("Reservation saved successfully");
        } else {
            return new Feedback("Failed to save reservation");
        }
    }

    public List<Reservation> getAllHeldReservations() {
        return reservationRepository.findAll();
    }

    public Feedback updateReservation(Long id, Reservation updatedReservation) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if (optionalReservation.isEmpty()) {
            return new Feedback("Reservation not found");
        }

        Reservation existingReservation = optionalReservation.get();
        existingReservation.setEvent(updatedReservation.getEvent());
        existingReservation.setReservationDate(updatedReservation.getReservationDate());
        existingReservation.setQuantity(updatedReservation.getQuantity());
        existingReservation.setStatus(updatedReservation.getStatus());
        existingReservation.setCustomer(updatedReservation.getCustomer());

        reservationRepository.save(existingReservation);
        return new Feedback("Reservation updated successfully");
    }

    public Feedback deleteReservation(Long id) {
        if (!reservationRepository.existsById(id)) {
            return new Feedback("Reservation not found");
        }

        reservationRepository.deleteById(id);
        return new Feedback("Reservation deleted successfully");
    }
}
