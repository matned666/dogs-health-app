package pl.design.mrn.matned.dogmanagementapp.dataBase.app;

import java.util.Date;

public class Message {

    private int id;
    private MessageSubject subject;
    private String message;
    private Date messageDateTime;
    private MessageStatus status;

    public Message() {
    }

    public Message(MessageSubject subject, String message) {
        this.subject = subject;
        this.message = message;
        status = MessageStatus.NOT_READ;
        messageDateTime = new Date();
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

    public Date getMessageDateTime() {
        return messageDateTime;
    }

    public void setMessageDateTime(Date messageDateTime) {
        this.messageDateTime = messageDateTime;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }



}
