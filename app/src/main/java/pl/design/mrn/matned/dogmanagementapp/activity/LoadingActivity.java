package pl.design.mrn.matned.dogmanagementapp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.TextStrings_PL;
import pl.design.mrn.matned.dogmanagementapp.dataBase.app.Configuration;
import pl.design.mrn.matned.dogmanagementapp.dataBase.app.ConfigurationDao;
import pl.design.mrn.matned.dogmanagementapp.dataBase.app.MessageSubject;
import pl.design.mrn.matned.dogmanagementapp.dataBase.app.MessagesService;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.DogDao;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.DogModel;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.InjectionOther;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.InjectionOtherDao;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.InjectionRabid;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.InjectionRabidDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.PositionListener;

import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;
import static pl.design.mrn.matned.dogmanagementapp.TextStrings_PL.*;

public class LoadingActivity extends AppCompatActivity {

    private AnimationDrawable runningDog;
    private DogDao dao;
    private ConfigurationDao cDao;
    private Configuration configuration;
    private MessagesService messagesService;

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat("ddMM");


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        dao = new DogDao(this);
        dao.findById(1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_app);
        messagesService = new MessagesService(this);
        ImageView imageView = findViewById(R.id.running_dog);
        imageView.setBackgroundResource(R.drawable.dog_anim);
        PositionListener.getInstance().setSelectedDogId(dao.findFirstRecordId());
        runningDog = (AnimationDrawable) imageView.getBackground();
        messages();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void messages() {
        cDao = new ConfigurationDao(this);
        configuration = cDao.get();
        createWelcomeMessage();
        checkVaccines();
        checkBirthday();
        checkDeworming();
        checkOtherNotifications();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void checkVaccines() {
        if (configuration.getIsVaccineNotificationOn() == 1) {
            DogDao dDao = new DogDao(this);
            for (DogModel dog : dDao.findAll()) {
                if (configuration.getIsVaccineNotificationOn() == 1) {
                    rabiesVaccineCheck(dog);
                    otherVaccineCheck(dog);
                }
                checkBirthday();
            }
        }
    }

    private void rabiesVaccineCheck(DogModel dog) {
        InjectionRabidDao ird = new InjectionRabidDao(this);
        boolean setter = false;
        List<InjectionRabid> vaccines = ird.getListByMasterId(dog.getId());
        if (vaccines.size() > 0) {
            for (InjectionRabid ir : vaccines) {
                if (ir.getNextTreatment().after(new Date())) {
                    setter = true;
                }
            }
            if (!setter) {
                String msg = getVaccineMessage(dog, RABIES_VACCINE);
                messagesService.sendMessage(MessageSubject.RABIES_VACCINE_ALERT, msg);
            }
        }
    }

    private void otherVaccineCheck(DogModel dog) {
        InjectionOtherDao iod = new InjectionOtherDao(this);
        boolean setter = false;
        for (InjectionOther ir : iod.getListByMasterId(dog.getId())) {
            if (ir.getNextTreatment().before(new Date())) {
                setter = true;
            }
        }
        if (!setter) {
            String msg = getVaccineMessage(dog, OTHER_VACCINE);
            messagesService.sendMessage(MessageSubject.RABIES_VACCINE_ALERT, msg);
        }
    }

    private String getVaccineMessage(DogModel dog, String otherVaccine) {
        return VACCINE_OUTDATE_1 +
                otherVaccine +
                OUTDATE_2 +
                dog.getName() +
                OUTDATE_3;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void checkBirthday() {
        if (configuration.getIsBirthdayNotificationOn() == 1) {
            DogDao dDao = new DogDao(this);
            for (DogModel dog : dDao.findAll()) {
                if (dateFormat.format(dog.getBirthDate()).equals(dateFormat.format(new Date()))) {
                    String msg = BIRTHDAY_MESSAGE1 + dog.getName() + BIRTHDAY_MESSAGE2;
                    messagesService.sendMessage(MessageSubject.DOG_BIRTHDAY, msg);
                }
            }
        }
    }

    private void checkDeworming() {
        if (configuration.getIsDewormingNotificationOn() == 1) {
            DogDao dDao = new DogDao(this);

        }
    }

    private void checkOtherNotifications() {
        if (configuration.getIsOtherNotificationOn() == 1) {
            DogDao dDao = new DogDao(this);

        }
    }

    private void createWelcomeMessage() {
        if (configuration.getIsWelcomeMessageSent() == 0) {
            messagesService.sendMessage(MessageSubject.WELCOME, TextStrings_PL.WELCOME_MESSAGE);
            configuration.setIsWelcomeMessageSent((byte) 1);
            cDao.update(configuration);
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        runningDog.start();
    }
}