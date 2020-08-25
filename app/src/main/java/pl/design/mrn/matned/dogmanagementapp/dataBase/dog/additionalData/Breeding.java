package pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData;


import java.io.Serializable;
import java.util.Objects;

public class Breeding  implements Serializable  {

    private int breedingId;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String description;
    private int dogId;

    public Breeding(int breedingId) {
        this.breedingId = breedingId;
    }

    public Breeding() {
    }

    public int getDogId() {
        return dogId;
    }

    public void setDogId(int dogId) {
        this.dogId = dogId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Breeding breeding = (Breeding) o;
        return  Objects.equals(name, breeding.name) &&
                Objects.equals(address, breeding.address) &&
                Objects.equals(phone, breeding.phone) &&
                Objects.equals(email, breeding.email) &&
                Objects.equals(description, breeding.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, phone, email, description);
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
