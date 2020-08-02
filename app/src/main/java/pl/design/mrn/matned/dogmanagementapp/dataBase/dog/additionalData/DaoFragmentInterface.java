package pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData;

import java.util.List;

import pl.design.mrn.matned.dogmanagementapp.dataBase.DaoInterface;

public interface DaoFragmentInterface<E> extends DaoInterface<E> {

    List<E> getListByMasterId(int id);
    boolean remove(int id);

}
