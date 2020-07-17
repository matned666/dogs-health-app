package pl.design.mrn.matned.dogmanagementapp.dataBase;

import java.util.List;

public interface DaoInterface<T> {
    boolean add(T t);
    List<T> findAll();
    boolean remove(T t);
    T findById(int id);
    boolean update(int id_toUpdate, T updated_T_Data);
}
