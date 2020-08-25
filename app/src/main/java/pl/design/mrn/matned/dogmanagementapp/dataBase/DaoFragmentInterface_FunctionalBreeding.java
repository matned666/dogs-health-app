package pl.design.mrn.matned.dogmanagementapp.dataBase;

import pl.design.mrn.matned.dogmanagementapp.dataBase.DaoFragmentInterface;

public interface DaoFragmentInterface_FunctionalBreeding<B> extends DaoFragmentInterface<B> {

    B findByDogId(int id);
}
