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
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.InjectionOther;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.InjectionOtherDao;
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

    private InjectionOther injectionOther;
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.healthdata_deworming);
        InjectionOtherDao dao = new InjectionOtherDao(this);
        injectionOther = dao.findById(DataPositionListener.getInstance().getSelectedItemId());
        edit = findViewById(R.id.otherVaxine_edit_btn);
        back = findViewById(R.id.otherVaxine_back_btn);
        medicineTV = findViewById(R.id.otherVaxine_name_subtitle);
        descET = findViewById(R.id.otherVaxine_description_textData);
        dateOfTreatmentTV = findViewById(R.id.otherVaxine_date_subtitle);
        dateOfNextTreatmentTV = findViewById(R.id.otherVaxine_next_date_subtitle);
        noteET = findViewById(R.id.otherVaxine_note_subtitle);
        photoStampIV = findViewById(R.id.otherVaxine_notephoto);
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
        medicineTV.setText(injectionOther.getMedicine());
        descET.setText(injectionOther.getDescription());
        if(injectionOther.getTreatmentDate() != null)
            dateOfTreatmentTV.setText(dateFormat.format(injectionOther.getTreatmentDate()));
        else dateOfTreatmentTV.setText("");
        if(injectionOther.getNextTreatment() != null)
            dateOfNextTreatmentTV.setText(dateFormat.format(injectionOther.getNextTreatment()));
        else dateOfNextTreatmentTV.setText("");
        noteET.setText(injectionOther.getNote());
//        TODO , Photo
    }
}

