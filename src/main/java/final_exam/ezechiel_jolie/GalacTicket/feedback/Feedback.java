package final_exam.ezechiel_jolie.GalacTicket.feedback;

public class Feedback {
    private String feedbackMessage;
    private String to;

    public Feedback(String feedbackMessage) {
        this.feedbackMessage = feedbackMessage;
    }

    public Feedback(String feedbackMessage, String to) {
        this.feedbackMessage = feedbackMessage;
        this.to = to;
    }

    public String getMessage() {
        return feedbackMessage;
    }

    public void setMessage(String feedbackMessage) {
        this.feedbackMessage = feedbackMessage;
    }

    public String getRedirectTo() {
        return to;
    }

    public void setRedirectTo(String to) {
        this.to = to;
    }
}
