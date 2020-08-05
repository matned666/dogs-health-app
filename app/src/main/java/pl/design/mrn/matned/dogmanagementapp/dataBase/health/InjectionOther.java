package pl.design.mrn.matned.dogmanagementapp.dataBase.health;

import java.util.Date;

public class InjectionOther {

    private int id;
    private String medicine;
    private String description;
    private Date treatmentDate;
    private Date nextTreatment;
    private String note;
    private String photo;
    private int dogId;

    public InjectionOther() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTreatmentDate() {
        return treatmentDate;
    }

    public void setTreatmentDate(Date treatmentDate) {
        this.treatmentDate = treatmentDate;
    }

    public Date getNextTreatment() {
        return nextTreatment;
    }

    public void setNextTreatment(Date nextTreatment) {
        this.nextTreatment = nextTreatment;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getDogId() {
        return dogId;
    }

    public void setDogId(int dogId) {
        this.dogId = dogId;
    }

    @Override
    public String toString() {
        return "InjectionOther{" +
                "id=" + id +
                ", medicine='" + medicine + '\'' +
                ", description='" + description + '\'' +
                ", treatmentDate=" + treatmentDate +
                ", nextTreatment=" + nextTreatment +
                ", note='" + note + '\'' +
                ", photo='" + photo + '\'' +
                ", dogId=" + dogId +
                '}';
    }
}
