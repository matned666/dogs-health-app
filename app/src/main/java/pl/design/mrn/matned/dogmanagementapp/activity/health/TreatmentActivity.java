package pl.design.mrn.matned.dogmanagementapp.activity.health;

import android.annotation.SuppressLint;
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
import pl.design.mrn.matned.dogmanagementapp.activity.health.edit.TreatmentActivityEdit;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.InjectionOther;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.Treatment;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.TreatmentDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;

import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;

public class TreatmentActivity extends AppCompatActivity {

    private Button edit;
    private Button back;
    private TextView illness;
    private TextView descET;
    private TextView dateOfTreatmentTV;
    private TextView dateOfNextTreatmentTV;
    private TextView noteET;
    private ImageView photoStampIV;

    private Treatment treatment;

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.healthdata_treatment);
        TreatmentDao dao = new TreatmentDao(this);
        treatment = dao.findById(DataPositionListener.getInstance().getSelectedItemId());
        edit = findViewById(R.id.treatment_edit_btn);
        back = findViewById(R.id.treatment_back_btn);
        illness = findViewById(R.id.treatment_name_dataText);
        descET = findViewById(R.id.treatment_description_dataText);
        dateOfTreatmentTV = findViewById(R.id.treatment_date_dataText);
        dateOfNextTreatmentTV = findViewById(R.id.allergen_name_subtitle);
        noteET = findViewById(R.id.treatment_note_dataText);
        photoStampIV = findViewById(R.id.treatment_photo);
        photoStampIV.setVisibility(View.GONE);
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
            Intent intent = new Intent(this, TreatmentActivityEdit.class);
            startActivityForResult(intent, 101);
        });
    }

    private void fillAllFields() {
        illness.setText(treatment.getIllness());
        descET.setText(treatment.getDescription());
        if(treatment.getDateOfTreatment() != null)
            dateOfTreatmentTV.setText(dateFormat.format(treatment.getDateOfTreatment()));
        else dateOfTreatmentTV.setText("");
        if(treatment.getDateOfNextTreatment() != null)
            dateOfNextTreatmentTV.setText(dateFormat.format(treatment.getDateOfNextTreatment()));
        else dateOfNextTreatmentTV.setText("");
        noteET.setText(treatment.getNote());
//        TODO , Photo
    }
}
