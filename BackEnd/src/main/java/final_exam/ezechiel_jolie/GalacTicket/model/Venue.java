package final_exam.ezechiel_jolie.GalacTicket.model;


import jakarta.persistence.*;
import java.util.List;
@Entity
@Table(name = "venue")
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "venue_id")
    private Long id;

    @Column(name = "venue_name")
    private String name;

    @Column(name = "venue_location")
    private String location;

    @Column(name = "venue_capacity")
    private int capacity;

    @Column(name = "venue_description")
    private String description;

    @OneToMany(mappedBy = "venue", fetch = FetchType.LAZY)
    private List<Event> events;

    // Constructors
    public Venue() {}

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

