package pl.design.mrn.matned.dogmanagementapp.dataBase;

public enum Sex {
    PIES,
    SUKA;

    public static int getPosition(Sex sex) {
        if(sex == PIES) return 1;
        else return 2;
    }

    public static String[] sexValues() {
        return new String[] {PIES.name(), SUKA.name()};
    }
}
