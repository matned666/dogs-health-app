package pl.design.mrn.matned.dogmanagementapp.dataBase.dog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.design.mrn.matned.dogmanagementapp.dataBase.Sex;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.Breeding;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.Chip;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.Note;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.SpecialSign;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.Tattoo;
import pl.design.mrn.matned.dogmanagementapp.dataBase.owner.Owner;

public class DogModel {

    private int id;
    private String name;
    private String race;
    private Date birthDate;
    private String color;
    private Sex sex;
    private String dogImage;
    private List<Chip> chipList;
    private List<Tattoo> tattoos;
    private List<SpecialSign> specialSigns;
    private List<Note> notes;
    private List<Owner> owners;
    private Breeding breeding;

    public DogModel() {
        chipList = new ArrayList<>();
        tattoos = new ArrayList<>();
        specialSigns = new ArrayList<>();
        notes = new ArrayList<>();
        owners = new ArrayList<>();
        breeding = new Breeding();
    }

    public DogModel(String name, String race, Date birthDate, String color, Sex sex) {
        this.name = name;
        this.race = race;
        this.birthDate = birthDate;
        this.color = color;
        this.sex = sex;
        chipList = new ArrayList<>();
        tattoos = new ArrayList<>();
        specialSigns = new ArrayList<>();
        notes = new ArrayList<>();
        owners = new ArrayList<>();
        breeding = new Breeding();
    }

    public DogModel(int id, String name, String race, Date birthDate, String color, Sex sex) {
        this.id = id;
        this.name = name;
        this.race = race;
        this.birthDate = birthDate;
        this.color = color;
        this.sex = sex;
        chipList = new ArrayList<>();
        tattoos = new ArrayList<>();
        specialSigns = new ArrayList<>();
        notes = new ArrayList<>();
        owners = new ArrayList<>();
        breeding = new Breeding();
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getRace() {
        return race;
    }
    public void setRace(String race) {
        this.race = race;
    }

    public Date getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }

    public Sex getSex() {
        return sex;
    }
    public void setSex(Sex sex) {
        this.sex = sex;
    }

//         NOT ESSENTIAL VALUES:

    public String getDogImage() {
        return dogImage;
    }
    public void setDogImage(String dogImage) {
        this.dogImage = dogImage;
    }

    public List<Chip> getChipList() {
        return chipList;
    }
    public void setChipList(List<Chip> chipList){
        this.chipList.clear();
        this.chipList.addAll(chipList);
    }

    public List<Tattoo> getTattoos() {
        return tattoos;
    }
    public void setTattoos(List<Tattoo> tattoos){
        this.tattoos.clear();
        this.tattoos.addAll(tattoos);
    }

    public List<SpecialSign> getSpecialSigns() {
        return specialSigns;
    }
    public void setSpecialSigns(List<SpecialSign> signs) {
        this.specialSigns.clear();
        this.specialSigns.addAll(signs);
    }

    public List<Note> getNotes() {
        return notes;
    }
    public void setNotes(List<Note> notes) {
        this.notes.clear();
        this.notes.addAll(notes);
    }

    public List<Owner> getOwners() {
        return owners;
    }
    public void setOwners(List<Owner> owners) {
        this.owners.clear();
        this.owners.addAll(owners);
    }

    public Breeding getBreeding() {
        return breeding;
    }
    public void setBreeding(Breeding breeding) {
        this.breeding = breeding;
    }

    @Override
    public String toString() {
        return "DogModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", race='" + race + '\'' +
                ", birthDate=" + birthDate +
                ", color='" + color + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }

}
