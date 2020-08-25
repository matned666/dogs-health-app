package pl.design.mrn.matned.dogmanagementapp.dataBase;

import pl.design.mrn.matned.dogmanagementapp.dataBase.DaoInterface;

public interface DogDaoInterface<E> extends DaoInterface<E> {

    int findFirstRecordId();
}
