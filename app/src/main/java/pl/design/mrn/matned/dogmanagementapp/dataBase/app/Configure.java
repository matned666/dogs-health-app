package pl.design.mrn.matned.dogmanagementapp.dataBase.app;

public interface Configure {

    boolean create(Configuration configuration);
    Configuration get();
    boolean update(Configuration configuration);
}
