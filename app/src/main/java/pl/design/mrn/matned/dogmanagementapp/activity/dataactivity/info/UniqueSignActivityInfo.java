package pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.info;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.activity.Add_DogActivity;
import pl.design.mrn.matned.dogmanagementapp.activity.Edit_DogActivity;
import pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.edit.UniqueSignActivityEdit;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.SpecialSign;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.SpecialSignDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;
import pl.design.mrn.matned.dogmanagementapp.listeners.PositionListener;

import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE;
import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE_ADD;
import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE_EDIT;

public class UniqueSignActivityInfo extends AppCompatActivity {

    private Button ok;
    private Button edit;
    private TextView descriptionTV;

    private SpecialSignDao dao;
    private SpecialSign sign;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.special_sign_info_dialog);
        initViews();
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

    private void initViews() {
        ok = findViewById(R.id.okSignDialogInfo);
        edit = findViewById(R.id.editSignDialogInfo);
        descriptionTV = findViewById(R.id.signDescriptionInfoTextView);
        dao = new SpecialSignDao(this);
        sign = dao.findById(DataPositionListener.getInstance().getSelectedItemId());
        initEndingListeners();
    }

    private void fillAllFields() {
        descriptionTV.setText(sign.getDescription());
    }

    private void initEndingListeners() {
        ok.setOnClickListener(v -> finish());
        edit.setOnClickListener(v -> {
            Intent intent = new Intent(this, UniqueSignActivityEdit.class);
            startActivityForResult(intent, 101);
        });
    }


}
