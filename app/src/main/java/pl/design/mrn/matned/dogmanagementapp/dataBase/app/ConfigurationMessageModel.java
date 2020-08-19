package pl.design.mrn.matned.dogmanagementapp.dataBase.app;

import java.util.Date;

public class ConfigurationMessageModel {

    private int id;
    private MessageSubject subject;
    private Date autidDate;
    private int masterId;

    public ConfigurationMessageModel() {
    }

    public ConfigurationMessageModel(MessageSubject subject, int masterId) {
        this.subject = subject;
        this.autidDate = new Date();
        this.masterId = masterId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public MessageSubject getSubject() {
        return subject;
    }

    public void setSubject(MessageSubject subject) {
        this.subject = subject;
    }

    public Date getAutidDate() {
        return autidDate;
    }

    public void setAutidDate(Date autidDate) {
        this.autidDate = autidDate;
    }

    public int getMasterId() {
        return masterId;
    }

    public void setMasterId(int masterId) {
        this.masterId = masterId;
    }

    @Override
    public String toString() {
        return "ConfigurationMessageModel{" +
                "id=" + id +
                ", subject=" + subject +
                ", autidDate=" + autidDate +
                ", masterId=" + masterId +
                '}';
    }
}
