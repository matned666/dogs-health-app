package pl.design.mrn.matned.dogmanagementapp.dataBase.health;

import java.util.Date;

public class Allergies {

    private int id;
    private String allergen;
    private String description;
    private Date dateOfDetection;
    private boolean wasTreated;
    private Date dateOfTreatment;
    private Date dateOfNextTreatment;
    private String note;
    private String photo;
    private int dogId;

    public Allergies() {
    }

    public int getDogId() {
        return dogId;
    }

    public void setDogId(int dogId) {
        this.dogId = dogId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAllergen() {
        return allergen;
    }

    public void setAllergen(String allergen) {
        this.allergen = allergen;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateOfDetection() {
        return dateOfDetection;
    }

    public void setDateOfDetection(Date dateOfDetection) {
        this.dateOfDetection = dateOfDetection;
    }

    public boolean isWasTreated() {
        return wasTreated;
    }

    public void setWasTreated(boolean wasTreated) {
        this.wasTreated = wasTreated;
    }

    public Date getDateOfTreatment() {
        return dateOfTreatment;
    }

    public void setDateOfTreatment(Date dateOfTreatment) {
        this.dateOfTreatment = dateOfTreatment;
    }

    public Date getDateOfNextTreatment() {
        return dateOfNextTreatment;
    }

    public void setDateOfNextTreatment(Date dateOfNextTreatment) {
        this.dateOfNextTreatment = dateOfNextTreatment;
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

    @Override
    public String toString() {
        return "Allergies{" +
                "id=" + id +
                ", allergen='" + allergen + '\'' +
                ", description='" + description + '\'' +
                ", dateOfDetection=" + dateOfDetection +
                ", wasTreated=" + wasTreated +
                ", dateOfTreatment=" + dateOfTreatment +
                ", dateOfNextTreatment=" + dateOfNextTreatment +
                ", note='" + note + '\'' +
                ", photo='" + photo + '\'' +
                ", dogId=" + dogId +
                '}';
    }
}
