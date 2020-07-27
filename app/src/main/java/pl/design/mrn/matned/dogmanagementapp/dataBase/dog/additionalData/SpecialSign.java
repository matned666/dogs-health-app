package pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData;

import java.io.Serializable;
import java.util.Date;

public class SpecialSign  implements Serializable {

    private int signId;
    private String description;
    private int dogId;
    private Date date;

    public SpecialSign() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public SpecialSign(int signId) {
        this.signId = signId;
    }

    public int getDogId() {
        return dogId;
    }

    public void setDogId(int dogId) {
        this.dogId = dogId;
    }

    public int getSignId() {
        return signId;
    }

    public void setSignId(int signId) {
        this.signId = signId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
