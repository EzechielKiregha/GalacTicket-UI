package final_exam.ezechiel_jolie.GalacTicket.model;


public class Login {
    private Auth auth;
    private User user;

    public Login(Auth auth, User user) {
        this.auth = auth;
        this.user = user;
    }
    
    public Auth getAuthentication() {
        return auth;
    }

    public void setAuthentication(Auth auth) {
        this.auth = auth;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    
}
