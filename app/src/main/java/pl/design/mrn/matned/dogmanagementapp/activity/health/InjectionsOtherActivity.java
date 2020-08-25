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
import pl.design.mrn.matned.dogmanagementapp.Statics;
import pl.design.mrn.matned.dogmanagementapp.activity.ImageActivity;
import pl.design.mrn.matned.dogmanagementapp.activity.health.edit.InjectionsOtherActivityEdit;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.InjectionOther;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.InjectionOtherDao;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.InjectionRabidDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;

import static pl.design.mrn.matned.dogmanagementapp.ImageAdvancedFunction.setImage;
import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;

public class InjectionsOtherActivity extends AppCompatActivity {


    private Button edit;
    private Button back;
    private TextView medicineTV;
    private TextView descET;
    private TextView dateOfTreatmentTV;
    private TextView dateOfNextTreatmentTV;
    private TextView noteET;
    private ImageView photoStampIV;

    private InjectionOther injectionOther;

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.healthdata_other_deseases_vaxine);
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
            Intent intent = new Intent(this, InjectionsOtherActivityEdit.class);
            startActivityForResult(intent, 101);
        });
        photoStampIV.setOnClickListener(goToImage -> {
            Intent intent = new Intent(this, ImageActivity.class);
            intent.putExtra(Statics.PHOTO_PATH, injectionOther.getPhoto());
            startActivity(intent);
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
        if(injectionOther.getPhoto() != null)
            setImage(photoStampIV, injectionOther.getPhoto(), this, getResources());
    }
}
