package final_exam.ezechiel_jolie.GalacTicket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import final_exam.ezechiel_jolie.GalacTicket.feedback.Feedback;
import final_exam.ezechiel_jolie.GalacTicket.model.Reservation;
import final_exam.ezechiel_jolie.GalacTicket.service.ReservationService;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping(value = "/save")
    public ResponseEntity<Feedback> saveReservation(@RequestBody Reservation reservation) {
        Feedback responseMessage = reservationService.saveReservation(reservation);
        return ResponseEntity.ok(responseMessage);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Reservation>> getAllHeldReservations() {
        List<Reservation> reservationList = reservationService.getAllHeldReservations();
        return ResponseEntity.ok(reservationList);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Feedback> updateReservation(@PathVariable Long id, @RequestBody Reservation updatedReservation) {
        Feedback responseMessage = reservationService.updateReservation(id, updatedReservation);
        return ResponseEntity.ok(responseMessage);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Feedback> deleteReservation(@PathVariable Long id) {
        Feedback responseMessage = reservationService.deleteReservation(id);
        return ResponseEntity.ok(responseMessage);
    }
}

