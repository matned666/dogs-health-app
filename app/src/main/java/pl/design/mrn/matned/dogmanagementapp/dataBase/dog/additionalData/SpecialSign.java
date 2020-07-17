package pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData;

import java.io.Serializable;

public class SpecialSign  implements Serializable {

    private int signId;
    private String description;

    public SpecialSign() {
    }

    public SpecialSign(int signId, String description) {
        this.signId = signId;
        this.description = description;
    }

    public int getSignId() {
        return signId;
    }

    public void setSignId(int signId) {
        this.signId = signId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "SpecialSign{" +
                "signId=" + signId +
                ", description='" + description + '\'' +
                '}';
    }
}
