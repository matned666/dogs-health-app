package pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData;

import java.io.Serializable;
import java.util.Date;


public class Chip  implements Serializable {

    private int chipId;
    private String chipNumber;
    private Date putDate;
    private Date extDate;
    private String chipDescription;
    private int dogId;

    public Chip() {
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

    public Date getExtDate() {
        return extDate;
    }

    public void setExtDate(Date extDate) {
        this.extDate = extDate;
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
                ", extDate=" + extDate +
                ", chipDescription='" + chipDescription + '\'' +
                ", dogId=" + dogId +
                '}';
    }
}
