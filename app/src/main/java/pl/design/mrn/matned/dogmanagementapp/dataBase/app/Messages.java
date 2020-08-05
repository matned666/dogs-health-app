package pl.design.mrn.matned.dogmanagementapp.dataBase.app;

public class Messages {

    private int id;
    private MessageSubject subject;
    private String message;
    private MessageStatus status;


    public Messages(MessageSubject subject, String message) {
        this.subject = subject;
        this.message = message;
        status = MessageStatus.NOT_READ;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MessageSubject getSubject() {
        return subject;
    }

    public void setSubject(MessageSubject subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Messages{" +
                "id=" + id +
                ", subject=" + subject +
                ", message='" + message + '\'' +
                '}';
    }
}
