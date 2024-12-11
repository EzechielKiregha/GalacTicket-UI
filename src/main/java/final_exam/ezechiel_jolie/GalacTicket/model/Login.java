package final_exam.ezechiel_jolie.GalacTicket.model;


public class Login {
    private Authentication auth;
    private User user;

    public Login(Authentication auth, User user) {
        this.auth = auth;
        this.user = user;
    }
    
    public Authentication getAuthentication() {
        return auth;
    }

    public void setAuthentication(Authentication auth) {
        this.auth = auth;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    
}
