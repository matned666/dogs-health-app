package pl.design.mrn.matned.dogmanagementapp.activity.health;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.Statics;
import pl.design.mrn.matned.dogmanagementapp.activity.ImageActivity;
import pl.design.mrn.matned.dogmanagementapp.activity.health.edit.AllergiesActivityEdit;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.Allergies;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.AllergiesDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;

import static pl.design.mrn.matned.dogmanagementapp.ImageAdvancedFunction.setImage;
import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;

public class AllergiesActivity extends AppCompatActivity {

    private Button edit;
    private Button back;
    private TextView nameET;
    private TextView descET;
    private TextView dateOfDiscoveryET;
    private TextView dateOfTreatmentET;
    private TextView nextDateOfTreatmentET;
    private TextView noteET;
    private ImageView photoStampIV;

    private Allergies allergy;
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.healthdata_allergy);
        AllergiesDao dao = new AllergiesDao(this);
        allergy = dao.findById(DataPositionListener.getInstance().getSelectedItemId());
        edit = findViewById(R.id.allergy_edit_btn);
        back = findViewById(R.id.allergy_back_btn);
        nameET = findViewById(R.id.allergen_name_subtitle);
        descET = findViewById(R.id.allergen_description_textData);
        dateOfDiscoveryET = findViewById(R.id.allergen_discoveryDate_subtitle);
        dateOfTreatmentET = findViewById(R.id.allergen_treatmentStartDate_subtitle);
        nextDateOfTreatmentET = findViewById(R.id.allergen_treatmentNextDate_subtitle);
        noteET = findViewById(R.id.allergen_note_subtitle);
        photoStampIV = findViewById(R.id.allergy_photo);
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
            Intent intent = new Intent(this, AllergiesActivityEdit.class);
            startActivityForResult(intent, 101);
        });
        photoStampIV.setOnClickListener(goToImage -> {
            Intent intent = new Intent(this, ImageActivity.class);
            intent.putExtra(Statics.PHOTO_PATH, allergy.getPhoto());
            startActivity(intent);
        });
    }

    private void fillAllFields() {
        nameET.setText(allergy.getAllergen());
        descET.setText(allergy.getDescription());
        if(allergy.getDateOfDetection() != null)
            dateOfDiscoveryET.setText(dateFormat.format(allergy.getDateOfDetection()));
        else dateOfDiscoveryET.setText("");
        if(allergy.getDateOfTreatment() != null)
            dateOfTreatmentET.setText(dateFormat.format(allergy.getDateOfTreatment()));
        else dateOfTreatmentET.setText("");
        if(allergy.getDateOfNextTreatment() != null)
            nextDateOfTreatmentET.setText(dateFormat.format(allergy.getDateOfNextTreatment()));
        else nextDateOfTreatmentET.setText("");
        noteET.setText(allergy.getNote());
        if(allergy.getPhoto() != null)
            setImage(photoStampIV, allergy.getPhoto(), this, getResources());
    }

}
