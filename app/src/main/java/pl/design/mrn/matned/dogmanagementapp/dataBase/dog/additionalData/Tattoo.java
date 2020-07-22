package pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData;

import java.io.Serializable;
import java.util.Date;

public class Tattoo  implements Serializable {

    private int tattooId;
    private Date tattooDate;
    private String description;
    private int dogId;

    public Tattoo() {
    }

    public int getDogId() {
        return dogId;
    }

    public void setDogId(int dogId) {
        this.dogId = dogId;
    }

    public Date getTattooDate() {
        return tattooDate;
    }

    public void setTattooDate(Date tattooDate) {
        this.tattooDate = tattooDate;
    }

    public int getTattooId() {
        return tattooId;
    }

    public void setTattooId(int tattooId) {
        this.tattooId = tattooId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Tattoo{" +
                "tattooId=" + tattooId +
                ", description='" + description + '\'' +
                '}';
    }
}
