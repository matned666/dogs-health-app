package pl.design.mrn.matned.dogmanagementapp.dataBase.health;

import java.util.Date;

public class TeethControl  implements HealthInterface{

    private int id;
    private String description;
    private Date dateOfControl;
    private Date dateOfNextControl;
    private String note;
    private String photo;
    private int dogId;

    public TeethControl() {
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateOfControl() {
        return dateOfControl;
    }

    public void setDateOfControl(Date dateOfControl) {
        this.dateOfControl = dateOfControl;
    }


    public int getDogId() {
        return dogId;
    }

    public void setDogId(int dogId) {
        this.dogId = dogId;
    }

    public Date getDateOfNextControl() {
        return dateOfNextControl;
    }

    public void setDateOfNextControl(Date dateOfNextControl) {
        this.dateOfNextControl = dateOfNextControl;
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
        return "TeethControl{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", dateOfControl=" + dateOfControl +
                ", dateOfNextControl=" + dateOfNextControl +
                ", note='" + note + '\'' +
                ", photo='" + photo + '\'' +
                ", dogId=" + dogId +
                '}';
    }
}
