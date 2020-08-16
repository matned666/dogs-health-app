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
import pl.design.mrn.matned.dogmanagementapp.activity.health.edit.InjectionsRabidActivityEdit;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.InjectionRabid;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.InjectionRabidDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;

import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;

public class InjectionsRabidActivity extends AppCompatActivity {



    private Button edit;
    private Button back;
    private TextView medicineTV;
    private TextView descET;
    private TextView dateOfTreatmentTV;
    private TextView dateOfNextTreatmentTV;
    private TextView noteET;
    private ImageView photoStampIV;

    private InjectionRabid injectionRabid;
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.healthdata_rabies_vaxine);
        InjectionRabidDao dao = new InjectionRabidDao(this);
        injectionRabid = dao.findById(DataPositionListener.getInstance().getSelectedItemId());
        edit = findViewById(R.id.rabiesVaxine_edit);
        back = findViewById(R.id.rabiesVaxine_back);
        medicineTV = findViewById(R.id.rabiesVaxine_name_subtitle);
        descET = findViewById(R.id.rabiesVaxine_description_textData);
        dateOfTreatmentTV = findViewById(R.id.rabiesVaxine_date_subtitle);
        dateOfNextTreatmentTV = findViewById(R.id.rabiesVaxine_next_date_subtitle);
        noteET = findViewById(R.id.rabiesVaxine_note_subtitle);
        photoStampIV = findViewById(R.id.rabiesVaxine_photo);
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
            Intent intent = new Intent(this, InjectionsRabidActivityEdit.class);
            startActivityForResult(intent, 101);
        });
    }

    private void fillAllFields() {
        medicineTV.setText(injectionRabid.getMedicine());
        descET.setText(injectionRabid.getDescription());
        if(injectionRabid.getTreatmentDate() != null)
            dateOfTreatmentTV.setText(dateFormat.format(injectionRabid.getTreatmentDate()));
        else dateOfTreatmentTV.setText("");
        if(injectionRabid.getNextTreatment() != null)
            dateOfNextTreatmentTV.setText(dateFormat.format(injectionRabid.getNextTreatment()));
        else dateOfNextTreatmentTV.setText("");
        noteET.setText(injectionRabid.getNote());
//        TODO , Photo
    }
}

