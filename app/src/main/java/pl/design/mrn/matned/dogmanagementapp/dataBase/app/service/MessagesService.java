package pl.design.mrn.matned.dogmanagementapp.dataBase.app.service;

import android.content.Context;

import pl.design.mrn.matned.dogmanagementapp.dataBase.app.Message;
import pl.design.mrn.matned.dogmanagementapp.dataBase.app.MessageSubject;
import pl.design.mrn.matned.dogmanagementapp.dataBase.app.MessagesDao;

@DogService
public class MessagesService {

    private MessagesDao dao;


    public MessagesService(Context context) {
        dao = new MessagesDao(context);
    }

    public void sendMessage(MessageSubject subject, String message){
        dao.add(new Message(subject, message));
    }
}
