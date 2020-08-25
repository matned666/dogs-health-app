package pl.design.mrn.matned.dogmanagementapp.dataBase.app;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface ConfigureMessage<E> extends Configure<E> {

    E get(MessageSubject subject, Date date, int id) throws ParseException;
    List<E> findAll() throws ParseException;
    boolean removeAll();
    boolean removeByDate(Date date);
}
