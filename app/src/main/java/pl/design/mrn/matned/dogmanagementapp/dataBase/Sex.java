package pl.design.mrn.matned.dogmanagementapp.dataBase;

public enum Sex {
    MALE,
    FEMALE;

    public static String value(Sex sex){
        if(sex == MALE) return "MALE";
        else return "FEMALE";
    }
}
