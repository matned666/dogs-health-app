package pl.design.mrn.matned.dogmanagementapp.dataBase.health;

import java.util.Date;

public class Deworming  implements HealthInterface{

    private int id;
    private String medicine;
    private String description;
    private String note;
    private Date treatmentDate;
    private Date nextTreatment;
    private String photo;
    private int dogId;

    public Deworming() {
    }

    public boolean isActive() {
        Date today = new Date();
        if(treatmentDate != null && nextTreatment != null) return nextTreatment.after(today) && treatmentDate.before(today);
        else if(nextTreatment != null ) return nextTreatment.after(today);
        else return false;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public int getDogId() {
        return dogId;
    }

    public void setDogId(int dogId) {
        this.dogId = dogId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Deworming{" +
                "id=" + id +
                ", medicine='" + medicine + '\'' +
                ", description='" + description + '\'' +
                ", note='" + note + '\'' +
                ", treatmentDate=" + treatmentDate +
                ", nextTreatment=" + nextTreatment +
                ", photo='" + photo + '\'' +
                ", dogId=" + dogId +
                '}';
    }
}
