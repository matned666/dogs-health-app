package pl.design.mrn.matned.dogmanagementapp;

import java.io.Serializable;

public class PositionHolder implements Serializable {

    private int position;

    public PositionHolder(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
