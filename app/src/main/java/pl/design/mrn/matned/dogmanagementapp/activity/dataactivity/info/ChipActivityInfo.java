package pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.info;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.edit.ChipActivityEdit;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.Chip;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.ChipDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;

import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;

public class ChipActivityInfo extends AppCompatActivity {

    private DataPositionListener dataPositionListener;

    private Button ok;
    private Button edit;
    private TextView chipNumberTV;
    private TextView chipPutDateTV;
    private TextView chipExpDateTV;
    private TextView chipDescriptionTV;

    private Chip chip;
    private ChipDao dao;

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            init();
            fillAllFields();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == Activity.RESULT_OK) {
                finish();
            }
        }
    }

    private void init() {
        setContentView(R.layout.chip_info_dialog);
        dataPositionListener = DataPositionListener.getInstance();
        initViews();
        initEndingListeners();

    }

    private void initViews() {
        ok = findViewById(R.id.okChipInfoDialog);
        edit = findViewById(R.id.editChipDialogInfo);
        chipNumberTV = findViewById(R.id.chipNumberInfoTextView);
        chipPutDateTV = findViewById(R.id.chipPutDateInfoTextView);
        chipExpDateTV = findViewById(R.id.chipExpiryDateInfoTextView);
        chipDescriptionTV = findViewById(R.id.chipDescriptionInfoTextView);
    }

    private void fillAllFields() {
        dao = new ChipDao(this);
        chip = dao.findById(dataPositionListener.getSelectedItemId());
        chipNumberTV.setText(chip.getChipNumber());
        if(chip.getPutDate() != null) chipPutDateTV.setText(dateFormat.format(chip.getPutDate()));
        if(chip.getExpDate() != null) chipExpDateTV.setText(dateFormat.format(chip.getExpDate()));
        if(chip.getChipDescription() != null) chipDescriptionTV.setText(chip.getChipDescription());
    }


    private void initEndingListeners() {
        ok.setOnClickListener(v -> finish());
        edit.setOnClickListener(v -> {
            Intent intent = new Intent(this, ChipActivityEdit.class);
            startActivityForResult(intent, 101);
        });
    }


}
