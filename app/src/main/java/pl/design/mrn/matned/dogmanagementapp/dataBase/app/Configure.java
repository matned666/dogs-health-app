package pl.design.mrn.matned.dogmanagementapp.dataBase.app;

public interface Configure<E> {

    boolean create(E e);
    boolean update(E e);
}
