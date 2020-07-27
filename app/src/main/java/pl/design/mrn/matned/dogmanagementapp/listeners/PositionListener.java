package pl.design.mrn.matned.dogmanagementapp.listeners;

import java.io.Serializable;

public class PositionListener implements Serializable {

    private static PositionListener instance;

    public static PositionListener getInstance(){
        if(instance == null) {
            instance = new PositionListener();
            instance.setPosition(0);
        }
        return instance;
    }


    private int position;
    private int selectedDogId;

    private PositionListener() {
        if (instance != null) {
            throw new IllegalStateException("Cannot create new instance, please use getInstance method instead.");
        }
    }

    public int getPosition() {
        return position;
    }

    public int getSelectedDogId() {
        return selectedDogId;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setSelectedDogId(int id) {
        this.selectedDogId = id;
    }
}
