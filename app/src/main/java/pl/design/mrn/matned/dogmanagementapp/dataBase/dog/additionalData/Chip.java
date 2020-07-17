package pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData;

import java.io.Serializable;

import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.DogModel;

public class Chip  implements Serializable {

    private int chipId;
    private String chipDescription;
    private DogModel dog;

    public Chip() {
    }

    public Chip(int chipId, String chipDescription, DogModel dog) {
        this.chipId = chipId;
        this.chipDescription = chipDescription;
        this.dog = dog;
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

    public DogModel getDog() {
        return dog;
    }

    public void setDog(DogModel dog) {
        this.dog = dog;
    }

    @Override
    public String toString() {
        return "Chip{" +
                "chipId=" + chipId +
                ", chipDescription='" + chipDescription + '\'' +
                ", dog=" + dog +
                '}';
    }
}
