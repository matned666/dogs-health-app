package pl.design.mrn.matned.dogmanagementapp.dataBase.app;

public class Configuration {

    private byte id;
    private byte isVaccineNotificationOn;
    private byte isBirthdayNotificationOn;
    private byte isDewormingNotificationOn;
    private byte isOtherNotificationOn;
    private byte isWelcomeMessageSent;

    public Configuration() {
    }

    public byte getId() {
        return id;
    }

    public void setId(byte id) {
        this.id = id;
    }

    public byte getIsVaccineNotificationOn() {
        return isVaccineNotificationOn;
    }

    public void setIsVaccineNotificationOn(byte isVaccineNotificationOn) {
        this.isVaccineNotificationOn = isVaccineNotificationOn;
    }

    public byte getIsBirthdayNotificationOn() {
        return isBirthdayNotificationOn;
    }

    public void setIsBirthdayNotificationOn(byte isBirthdayNotificationOn) {
        this.isBirthdayNotificationOn = isBirthdayNotificationOn;
    }

    public byte getIsDewormingNotificationOn() {
        return isDewormingNotificationOn;
    }

    public void setIsDewormingNotificationOn(byte isDewormingNotificationOn) {
        this.isDewormingNotificationOn = isDewormingNotificationOn;
    }

    public byte getIsOtherNotificationOn() {
        return isOtherNotificationOn;
    }

    public void setIsOtherNotificationOn(byte isOtherNotificationOn) {
        this.isOtherNotificationOn = isOtherNotificationOn;
    }

    public byte getIsWelcomeMessageSent() {
        return isWelcomeMessageSent;
    }

    public void setIsWelcomeMessageSent(byte isWelcomeMessageSent) {
        this.isWelcomeMessageSent = isWelcomeMessageSent;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "isVaccineNotificationOn=" + isVaccineNotificationOn +
                ", isBirthdayNotificationOn=" + isBirthdayNotificationOn +
                ", isDewormingNotificationOn=" + isDewormingNotificationOn +
                ", isOtherNotificationOn=" + isOtherNotificationOn +
                '}';
    }
}
