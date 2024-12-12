package final_exam.ezechiel_jolie.GalacTicket.model;

public class Auth {
    private String accesskey;
    private String message;

    public Auth(String accesskey, String message) {
        this.accesskey = accesskey;
        this.message = message;
    }

    public String getToken() {
        return accesskey;
    }

    public String getMessage() {
        return message;
    }

    public void setToken(String accesskey) {
        this.accesskey = accesskey;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
