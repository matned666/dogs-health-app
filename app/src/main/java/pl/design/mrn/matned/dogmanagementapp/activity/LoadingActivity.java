package pl.design.mrn.matned.dogmanagementapp.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import pl.design.mrn.matned.dogmanagementapp.R;
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


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        runningDog.start();
    }
}