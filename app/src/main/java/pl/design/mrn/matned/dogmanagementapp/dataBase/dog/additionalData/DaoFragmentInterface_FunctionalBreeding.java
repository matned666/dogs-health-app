package pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData;

public interface DaoFragmentInterface_FunctionalBreeding<B> extends DaoFragmentInterface<B>{

    B findByDogId(int id);
}
