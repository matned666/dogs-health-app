package pl.design.mrn.matned.dogmanagementapp;

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

    private PositionListener() {
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
}
