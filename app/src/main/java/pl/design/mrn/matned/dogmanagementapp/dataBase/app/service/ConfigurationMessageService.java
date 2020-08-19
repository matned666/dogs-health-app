package pl.design.mrn.matned.dogmanagementapp.dataBase.app.service;

import android.content.Context;

import java.util.Calendar;
import java.util.Date;

import pl.design.mrn.matned.dogmanagementapp.dataBase.app.ConfigurationMessageDao;
import pl.design.mrn.matned.dogmanagementapp.dataBase.app.ConfigurationMessageModel;
import pl.design.mrn.matned.dogmanagementapp.dataBase.app.Message;

public class ConfigurationMessageService {

    private static final Integer AUDIT_DAYS = 14;
    private ConfigurationMessageDao dao;

    public ConfigurationMessageService(Context context) {
        dao = new ConfigurationMessageDao(context);
    }

    public void audit(int masterId, Message message){
        ConfigurationMessageModel model = new ConfigurationMessageModel();
        model.setSubject(message.getSubject());
        model.setMasterId(masterId);
        dao.create(model);
    }

    public void rmOutdated(){
        dao.removeByDate(getDatePast());
    }

    private Date getDatePast() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_YEAR, - (ConfigurationMessageService.AUDIT_DAYS));
        return c.getTime();
    }
}
