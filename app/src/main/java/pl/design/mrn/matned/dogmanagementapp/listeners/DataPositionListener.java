package pl.design.mrn.matned.dogmanagementapp.listeners;

import java.io.Serializable;

public class DataPositionListener implements Serializable {

    private static DataPositionListener instance;

    public static DataPositionListener getInstance(){
        if(instance == null) {
            instance = new DataPositionListener();
            instance.setPosition(0);
        }
        return instance;
    }


    private int position;
    private int selectedItemId;

    private DataPositionListener() {
        if (instance != null) {
            throw new IllegalStateException("Cannot create new instance, please use getInstance method instead.");
        }
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getSelectedItemId() {
        return selectedItemId;
    }

    public void setSelectedItemId(int selectedItemId) {
        this.selectedItemId = selectedItemId;
    }
}
