package pl.design.mrn.matned.dogmanagementapp.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.dataBase.app.Message;
import pl.design.mrn.matned.dogmanagementapp.dataBase.app.MessageSubject;
import pl.design.mrn.matned.dogmanagementapp.dataBase.app.MessagesDao;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.DogDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.PositionListener;

public class LoadingActivity  extends AppCompatActivity {

    AnimationDrawable runningDog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_app);
        ImageView imageView = findViewById(R.id.running_dog);
        imageView.setBackgroundResource(R.drawable.dog_anim);
        DogDao dao = new DogDao(this);
        PositionListener.getInstance().setSelectedDogId(dao.findFirstRecordId());
        runningDog = (AnimationDrawable) imageView.getBackground();
        reateFirstMessage();
        Thread welcomeThread = new Thread() {
            @Override
            public void run() {
                try {
                    super.run();
                    sleep(3000);
                } catch (Exception ignored) {
                } finally {
                    Intent i = new Intent(LoadingActivity.this,
                            StartActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        welcomeThread.start();

    }

    private void reateFirstMessage() {
        MessagesDao dao = new MessagesDao(this);
        if (!dao.isWelcomeMessageSent()) {
            Message message = new Message();
            message.setSubject(MessageSubject.WELCOME);
            message.setMessage("" +
                    "Witamy w naszej aplikacji \n" +
                    "Mam nadzieje, ze pieski maja sie dobrze \n" +
                    "i ze aplikacja posluzy na wiele pokolen \n" +
                    "Pozdrawiam, \n" +
                    "Mateusz Niedbal - tworca \n" +
                    "MRN-Design.eu");
            dao.add(message);
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        runningDog.start();
    }
}