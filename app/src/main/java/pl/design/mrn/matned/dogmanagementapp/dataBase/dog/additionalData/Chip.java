package pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData;

import java.io.Serializable;
import java.util.Date;

import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.DogModel;

public class Chip  implements Serializable {

    private int chipId;
    private long chipNumber;
    private Date putDate;
    private Date extDate;
    private String chipDescription;
    private DogModel dog;

    public Chip() {
    }


    public long getChipNumber() {
        return chipNumber;
    }

    public void setChipNumber(long chipNumber) {
        this.chipNumber = chipNumber;
    }

    public Date getPutDate() {
        return putDate;
    }

    public void setPutDate(Date putDate) {
        this.putDate = putDate;
    }

    public Date getExtDate() {
        return extDate;
    }

    public void setExtDate(Date extDate) {
        this.extDate = extDate;
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
