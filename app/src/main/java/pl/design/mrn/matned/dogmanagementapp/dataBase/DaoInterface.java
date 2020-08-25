package pl.design.mrn.matned.dogmanagementapp.dataBase;

import java.util.List;

public interface DaoInterface<T> {
    boolean add(T t);
    List<T> findAll();
    T findById(int id);
    boolean remove(T t);
    boolean removeAll();
    boolean update(T updated_T_Data);
}
