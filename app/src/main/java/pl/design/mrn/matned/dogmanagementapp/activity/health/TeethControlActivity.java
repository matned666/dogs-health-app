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
import pl.design.mrn.matned.dogmanagementapp.activity.health.edit.TeethControlActivityEdit;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.InjectionOther;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.TeethControl;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.TeethControlDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;

import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;

public class TeethControlActivity extends AppCompatActivity {

    private Button edit;
    private Button back;
    private TextView descET;
    private TextView dateOfTreatmentTV;
    private TextView dateOfNextTreatmentTV;
    private TextView noteET;
    private ImageView photoStampIV;

    private TeethControl teethControl;

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.healthdata_teeth_control);
        TeethControlDao dao = new TeethControlDao(this);
        teethControl = dao.findById(DataPositionListener.getInstance().getSelectedItemId());
        edit = findViewById(R.id.teeth_edit_btn);
        back = findViewById(R.id.teeth_back_btn);
        descET = findViewById(R.id.teeth_description_dataText);
        dateOfTreatmentTV = findViewById(R.id.teeth_date_dataText);
        dateOfNextTreatmentTV = findViewById(R.id.teeth_next_date_dataText);
        noteET = findViewById(R.id.teeth_note_dataText);
        photoStampIV = findViewById(R.id.teeth_photo);
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
            Intent intent = new Intent(this, TeethControlActivityEdit.class);
            startActivityForResult(intent, 101);
        });
    }

    private void fillAllFields() {
        descET.setText(teethControl.getDescription());
        if(teethControl.getDateOfControl() != null)
            dateOfTreatmentTV.setText(dateFormat.format(teethControl.getDateOfControl()));
        else dateOfTreatmentTV.setText("");
        if(teethControl.getDateOfNextControl() != null)
            dateOfNextTreatmentTV.setText(dateFormat.format(teethControl.getDateOfNextControl()));
        else dateOfNextTreatmentTV.setText("");
        noteET.setText(teethControl.getNote());
//        TODO , Photo
    }
}
