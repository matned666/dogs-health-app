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
import pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.edit.OwnerActivityEdit;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.Owner;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.OwnerDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;
import pl.design.mrn.matned.dogmanagementapp.listeners.PositionListener;

import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;

public class OwnerActivityInfo extends AppCompatActivity {

    private Button ok;
    private Button edit;

    private TextView ownerNameTV;
    private TextView ownerSurnameTV;
    private TextView ownerAddressTV;
    private TextView ownerPhoneTV;
    private TextView ownerEmailTV;
    private TextView ownerFromDateTV;
    private TextView ownerToDateTV;
    private TextView ownerDescriptionTV;

    private Owner owner;

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.owner_info_dialog);
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
        ok = findViewById(R.id.okOwnerDialogInfo);
        edit = findViewById(R.id.editOwnerDialogInfo);
        ownerNameTV = findViewById(R.id.ownerNameInfoTextView);
        ownerSurnameTV = findViewById(R.id.ownerSurnameInfoTextView);
        ownerAddressTV = findViewById(R.id.ownerAddressInfoTextView);
        ownerPhoneTV = findViewById(R.id.ownerPhoneInfoTextView);
        ownerEmailTV = findViewById(R.id.ownerEmailInfoTextView);
        ownerFromDateTV = findViewById(R.id.ownerFromDateInfoTextView);
        ownerToDateTV = findViewById(R.id.ownerToDateInfoTextView);
        ownerDescriptionTV = findViewById(R.id.ownerDescriptionInfoTextView);
        OwnerDao dao = new OwnerDao(this);
        owner = dao.findById(DataPositionListener.getInstance().getSelectedItemId());
        initEndingListeners();
    }

    private void fillAllFields(){
        if (owner.getName() != null) ownerNameTV.setText(owner.getName());
        if (owner.getSurname() != null) ownerSurnameTV.setText(owner.getSurname());
        if (owner.getAddress() != null) ownerAddressTV.setText(owner.getAddress());
        if (owner.getPhoneNumber() != null) ownerPhoneTV.setText(owner.getPhoneNumber());
        if (owner.getEmail() != null) ownerEmailTV.setText(owner.getEmail());
        if (owner.getDateFrom() != null) ownerFromDateTV.setText(dateFormat.format(owner.getDateFrom()));
        if (owner.getDateTo() != null) ownerToDateTV.setText(dateFormat.format(owner.getDateTo()));
        if (owner.getDescription() != null) ownerDescriptionTV.setText(owner.getDescription());
    }

    private void initEndingListeners() {
        ok.setOnClickListener(v -> finish());
        edit.setOnClickListener(v -> {
            Intent intent = new Intent(this, OwnerActivityEdit.class);
            startActivityForResult(intent, 101);
        });
    }

}
