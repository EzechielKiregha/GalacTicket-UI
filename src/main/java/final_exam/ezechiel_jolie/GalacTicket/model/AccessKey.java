package final_exam.ezechiel_jolie.GalacTicket.model;

import jakarta.persistence.*;

@Entity
@Table(name = "access_key")
public class AccessKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "key_id")
    private Integer keyId;

    @Column(name = "key_value")
    private String keyValue;

    @Column(name = "is_deactivated")
    private boolean deactivated;

    @ManyToOne
    @JoinColumn(name = "galactic_id")
    private User user;

    public Integer getKeyId() {
        return keyId;
    }

    public void setKeyId(Integer keyId) {
        this.keyId = keyId;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public boolean isDeactivated() {
        return deactivated;
    }

    public void setDeactivated(boolean deactivated) {
        this.deactivated = deactivated;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

