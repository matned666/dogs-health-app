package pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData;

import java.io.Serializable;

public class SpecialSign  implements Serializable {

    private int signId;
    private String description;
    private int dogId;

    public SpecialSign() {
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
