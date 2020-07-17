package pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData;

import java.io.Serializable;

public class Breeding  implements Serializable {

    private int breedingId;
    private String name;
    private String address;
    private String description;

    public Breeding() {
    }

    public Breeding(int breedingId, String name, String address, String description) {
        this.breedingId = breedingId;
        this.name = name;
        this.address = address;
        this.description = description;
    }

    public int getBreedingId() {
        return breedingId;
    }

    public void setBreedingId(int breedingId) {
        this.breedingId = breedingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Breeding{" +
                "breedingId=" + breedingId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
