package pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.info;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.activity.Add_DogActivity;
import pl.design.mrn.matned.dogmanagementapp.activity.Edit_DogActivity;
import pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.edit.TattooActivityEdit;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.Tattoo;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.TattooDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;
import pl.design.mrn.matned.dogmanagementapp.listeners.PositionListener;

import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;
import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE;
import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE_ADD;
import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE_EDIT;
import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE_INFO;

public class TattooActivityInfo extends AppCompatActivity {

    private Button ok;
    private Button cancel;
    private TextView tattooDateTV;
    private TextView tattooDescriptionTV;

    private Tattoo tattoo;

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.tattoo_info_dialog);
            initViews();
            fillAllFields();
    }
    private void initViews() {
        ok = findViewById(R.id.okTattooDialogInfo);
        cancel = findViewById(R.id.editTattooDialogInfo);
        tattooDateTV = findViewById(R.id.tattooPutDateInfoTextView);
        tattooDescriptionTV = findViewById(R.id.tattooDescriptionInfoTextView);
        initEndingListeners();
    }

    private void fillAllFields() {
        TattooDao dao = new TattooDao(this);
        tattoo = dao.findById(DataPositionListener.getInstance().getSelectedItemId());
        tattooDateTV.setText(dateFormat.format(tattoo.getTattooDate()));
        tattooDescriptionTV.setText(tattoo.getDescription());
    }


    private void initEndingListeners() {
        ok.setOnClickListener(v -> finish());
        cancel.setOnClickListener(v -> {
            Intent intent = new Intent(this, TattooActivityEdit.class);
            startActivity(intent);
        });
    }

}
