package final_exam.ezechiel_jolie.GalacTicket.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "residence")
    private String residence;

    @OneToMany(mappedBy = "customer")
    private List<Reservation> heldReservations;

    @ManyToMany(mappedBy = "customers")
    private List<Event> attendedEvents;

    @OneToMany(mappedBy = "customer")
    private List<Ticket> purchasedTickets;

    public Customer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public List<Reservation> getHeldReservations() {
        return heldReservations;
    }

    public void setHeldReservations(List<Reservation> heldReservations) {
        this.heldReservations = heldReservations;
    }

    public List<Event> getAttendedEvents() {
        return attendedEvents;
    }

    public void setAttendedEvents(List<Event> attendedEvents) {
        this.attendedEvents = attendedEvents;
    }

    public List<Ticket> getPurchasedTickets() {
        return purchasedTickets;
    }

    public void setPurchasedTickets(List<Ticket> purchasedTickets) {
        this.purchasedTickets = purchasedTickets;
    }
}

