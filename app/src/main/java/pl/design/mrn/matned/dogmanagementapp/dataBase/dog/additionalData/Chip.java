package pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;


public class Chip implements Serializable {

    private int chipId;
    private String chipNumber;
    private Date putDate;
    private Date expDate;
    private String chipDescription;
    private int dogId;
    private boolean isActive;

    public Chip() {

    }

    public boolean isActive() {
        Date today = new Date();
        if(putDate != null && expDate != null) return expDate.after(today) && putDate.before(today);
        else if(expDate != null ) return expDate.after(today);
        else return false;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Chip(int chipId) {
        this.chipId = chipId;
    }

    public String getChipNumber() {
        return chipNumber;
    }

    public void setChipNumber(String chipNumber) {
        this.chipNumber = chipNumber;
    }

    public Date getPutDate() {
        return putDate;
    }

    public void setPutDate(Date putDate) {
        this.putDate = putDate;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public int getChipId() {
        return chipId;
    }

    public void setChipId(int chipId) {
        this.chipId = chipId;
    }

    public String getChipDescription() {
        return chipDescription;
    }

    public void setChipDescription(String chipDescription) {
        this.chipDescription = chipDescription;
    }

    public int getDogId() {
        return dogId;
    }

    public void setDogId(int dogId) {
        this.dogId = dogId;
    }

    @Override
    public String toString() {
        return "Chip{" +
                "chipId=" + chipId +
                ", chipNumber=" + chipNumber +
                ", putDate=" + putDate +
                ", extDate=" + expDate +
                ", chipDescription='" + chipDescription + '\'' +
                ", dogId=" + dogId +
                '}';
    }
}
