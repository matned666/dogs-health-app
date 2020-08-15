package pl.design.mrn.matned.dogmanagementapp.service;

import android.content.Context;

import pl.design.mrn.matned.dogmanagementapp.dataBase.app.Message;
import pl.design.mrn.matned.dogmanagementapp.dataBase.app.MessagesDao;

public class MessageService {

    public static boolean sendMessage(Context context, Message message){
        MessagesDao dao = new MessagesDao(context);
        return dao.add(message);
    }

    public static Message getMessage(int id, Context context){
        MessagesDao dao = new MessagesDao(context);
        return dao.findById(id);
    }

    public static boolean deleteMessage(Message message, Context context){
        MessagesDao dao = new MessagesDao(context);
        return dao.remove(message);
    }


}
