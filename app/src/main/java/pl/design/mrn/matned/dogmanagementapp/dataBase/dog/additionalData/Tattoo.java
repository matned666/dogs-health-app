package pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData;

import java.io.Serializable;

public class Tattoo  implements Serializable {

    private int tattooId;
    private String description;

    public Tattoo() {
    }

    public Tattoo(int tattooId, String description) {
        this.tattooId = tattooId;
        this.description = description;
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
