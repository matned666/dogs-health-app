package pl.design.mrn.matned.dogmanagementapp.dataBase.app;

import android.content.Context;

public class MessagesService {

    private MessagesDao dao;

    public MessagesService(Context context) {
        dao = new MessagesDao(context);
    }

    public void sendMessage(MessageSubject subject, String message){
        dao.add(new Message(subject, message));
    }
}
