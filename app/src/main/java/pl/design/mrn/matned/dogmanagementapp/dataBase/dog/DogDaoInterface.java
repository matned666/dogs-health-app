package pl.design.mrn.matned.dogmanagementapp.dataBase.dog;

import pl.design.mrn.matned.dogmanagementapp.dataBase.DaoInterface;

public interface DogDaoInterface<E> extends DaoInterface<E> {

    int findFirstRecordId();
}
