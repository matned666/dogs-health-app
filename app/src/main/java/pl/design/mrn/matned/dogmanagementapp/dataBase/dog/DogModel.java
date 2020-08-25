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
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.Owner;

public class DogModel {

    private int id;
    private String name;
    private String race;
    private Date birthDate;
    private String color;
    private Sex sex;
    private String dogImage;

    public DogModel() {
    }

    public DogModel(String name,
                    String race,
                    Date birthDate,
                    String color,
                    Sex sex) {
        this.name = name;
        this.race = race;
        this.birthDate = birthDate;
        this.color = color;
        this.sex = sex;

    }

    public DogModel(int id,
                    String name,
                    String race,
                    Date birthDate,
                    String color,
                    Sex sex) {
        this.id = id;
        this.name = name;
        this.race = race;
        this.birthDate = birthDate;
        this.color = color;
        this.sex = sex;

    }

    public void setTo(DogModel dog){
        this.id = dog.getId();
        this.name = dog.getName();
        this.race = dog.getRace();
        this.birthDate = dog.getBirthDate();
        this.color = dog.getColor();
        this.sex = dog.getSex();
        this.dogImage = dog.getDogImage();

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
