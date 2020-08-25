package pl.design.mrn.matned.dogmanagementapp.dataBase.health;

import java.util.Date;

public class BirthControl  implements HealthInterface{

    private int id;
    private int numberOfChildren;
    private String description;
    private Date dateOfBirth;
    private String note;
    private String photo;
    private int dogId;

    public BirthControl() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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
        return "BirthControl{" +
                "id=" + id +
                ", numberOfChildren=" + numberOfChildren +
                ", description='" + description + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", photo='" + photo + '\'' +
                ", dogId=" + dogId +
                '}';
    }
}
