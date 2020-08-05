package pl.design.mrn.matned.dogmanagementapp.dataBase.health;

import java.util.Date;

public class Treatment {

    private int id;
    private String illness;
    private String description;
    private Date dateOfTreatment;
    private Date dateOfNextTreatment;
    private String photo;
    private int dogId;

    public Treatment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIllness() {
        return illness;
    }

    public void setIllness(String illness) {
        this.illness = illness;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return "Treatment{" +
                "id=" + id +
                ", illness='" + illness + '\'' +
                ", description='" + description + '\'' +
                ", dateOfTreatment=" + dateOfTreatment +
                ", dateOfNextTreatment=" + dateOfNextTreatment +
                ", photo='" + photo + '\'' +
                ", dogId=" + dogId +
                '}';
    }
}
