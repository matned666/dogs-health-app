package pl.design.mrn.matned.dogmanagementapp.activity;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import pl.design.mrn.matned.dogmanagementapp.PositionListener;
import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.DogDao;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.DogModel;

public class EditActivity extends AppCompatActivity {

    private DogModel dog;
    private DogDao dogDao;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_dog);
        dogDao = new DogDao(this);
        dog = dogDao.findById(PositionListener.getInstance().getPosition()+1);
        initFields();
    }

    private void initFields() {


    }


}
