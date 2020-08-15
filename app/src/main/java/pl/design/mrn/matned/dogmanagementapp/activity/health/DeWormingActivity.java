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
import pl.design.mrn.matned.dogmanagementapp.activity.health.edit.BirthControlActivityEdit;
import pl.design.mrn.matned.dogmanagementapp.activity.health.edit.DeWormingActivityEdit;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.BirthControlDao;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.Deworming;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.DewormingDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;

import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;

public class DeWormingActivity extends AppCompatActivity {



    private Button edit;
    private Button back;
    private TextView medicineTV;
    private TextView descET;
    private TextView dateOfTreatmentTV;
    private TextView dateOfNextTreatmentTV;
    private TextView noteET;
    private ImageView photoStampIV;

    private Deworming deworming;
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.healthdata_deworming);
        DewormingDao dao = new DewormingDao(this);
        deworming = dao.findById(DataPositionListener.getInstance().getSelectedItemId());
        edit = findViewById(R.id.deworming_edit_btn);
        back = findViewById(R.id.deworming_back_btn);
        medicineTV = findViewById(R.id.deworming_name_subtitle);
        descET = findViewById(R.id.deworming_description_textData);
        dateOfTreatmentTV = findViewById(R.id.deworming_date_subtitle);
        dateOfNextTreatmentTV = findViewById(R.id.deworming_next_date_subtitle);
        noteET = findViewById(R.id.deworming_note_subtitle);
        photoStampIV = findViewById(R.id.deworming_note_photo);
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
            Intent intent = new Intent(this, DeWormingActivityEdit.class);
            startActivityForResult(intent, 101);
        });
    }

    private void fillAllFields() {
        medicineTV.setText(deworming.getMedicine());
        descET.setText(deworming.getDescription());
        if(deworming.getTreatmentDate() != null)
            dateOfTreatmentTV.setText(dateFormat.format(deworming.getTreatmentDate()));
        else dateOfTreatmentTV.setText("");
        if(deworming.getNextTreatment() != null)
            dateOfNextTreatmentTV.setText(dateFormat.format(deworming.getNextTreatment()));
        else dateOfNextTreatmentTV.setText("");
        noteET.setText(deworming.getNote());
//        TODO , Photo
    }


}
