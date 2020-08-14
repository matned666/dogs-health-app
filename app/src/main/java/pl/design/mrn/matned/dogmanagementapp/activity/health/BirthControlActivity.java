package pl.design.mrn.matned.dogmanagementapp.activity.health;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.activity.health.edit.AllergiesActivityEdit;
import pl.design.mrn.matned.dogmanagementapp.activity.health.edit.BirthControlActivityEdit;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.BirthControl;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.BirthControlDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;

import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;

public class BirthControlActivity extends AppCompatActivity {


    private Button edit;
    private Button back;
    private TextView numberPupsTV;
    private TextView descET;
    private TextView dateOfBirthET;
    private TextView noteET;
    private ImageView photoStampIV;

    private BirthControl birthControl;
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.healthdata_birth_control);
        BirthControlDao dao = new BirthControlDao(this);
        birthControl = dao.findById(DataPositionListener.getInstance().getSelectedItemId());
        edit = findViewById(R.id.birth_edit_button);
        back = findViewById(R.id.birth_back_btn);
        numberPupsTV = findViewById(R.id.birth_number_of_pups_dataText);
        descET = findViewById(R.id.birth_description_dataText);
        dateOfBirthET = findViewById(R.id.birth_date_dataText);
        noteET = findViewById(R.id.birth_note_dataText);
        photoStampIV = findViewById(R.id.birth_photo);
        fillAllFields();
        clickListeners();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            finish();
        }
    }

    private void clickListeners() {
        back.setOnClickListener(v->finish());
        edit.setOnClickListener(v->{
            Intent intent = new Intent(this, BirthControlActivityEdit.class);
            startActivityForResult(intent, 101);
        });
    }

    private void fillAllFields() {
        numberPupsTV.setText(birthControl.getNumberOfChildren());
        descET.setText(birthControl.getDescription());
        if(birthControl.getDateOfBirth() != null)
            dateOfBirthET.setText(dateFormat.format(birthControl.getDateOfBirth()));
        else dateOfBirthET.setText("");
        noteET.setText(birthControl.getNote());
//        TODO , Photo
    }


}
