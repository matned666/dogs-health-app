package pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.info;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.add.BreedingActivityAdd;
import pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.edit.BreedingActivityEdit;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.Breeding;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.BreedingDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.PositionListener;

import static pl.design.mrn.matned.dogmanagementapp.TextStrings_PL.ADD;
import static pl.design.mrn.matned.dogmanagementapp.TextStrings_PL.EDIT;

public class BreedingActivityInfo extends AppCompatActivity {



    private Button okBtn;
    private Button editBtn;

    private TextView breedingName;
    private TextView address;
    private TextView phone;
    private TextView email;
    private TextView description;

    private Breeding breeding;
    private BreedingDao dao;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dao = new BreedingDao(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onResume() {
        super.onResume();
        PositionListener listener = PositionListener.getInstance();
        breeding = dao.findByDogId(listener.getSelectedDogId());
        setContentView(R.layout.breeding_info_dialog);
        init();
        fillAllFields();
        setOnUsageView();
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void init() {
        breedingName = findViewById(R.id.breedingNameInfoTextView);
        address = findViewById(R.id.breedingAddressInfoTextView);
        phone = findViewById(R.id.breedingPhoneInfoTextView);
        email = findViewById(R.id.breedingEmailInfoTextView);
        description = findViewById(R.id.breedingDescriptionInfoTextView);
        okBtn = findViewById(R.id.okBreedingDialogInfo);
        editBtn = findViewById(R.id.editBreedingInfoDialog);
        TextView message = findViewById(R.id.breedingInfoDialog_informationTextView);
        if (breeding == null) {
            message.setVisibility(View.VISIBLE);
            editBtn.setText(ADD);
        } else {
            message.setVisibility(View.GONE);
            editBtn.setText(EDIT);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void fillAllFields() {
        if (breeding != null) {
            if (breeding.getName() != null) breedingName.setText(breeding.getName());
            if (breeding.getAddress() != null) address.setText(breeding.getAddress());
            if (breeding.getPhone() != null) phone.setText(breeding.getPhone());
            if (breeding.getEmail() != null) email.setText(breeding.getEmail());
            if (breeding.getDescription() != null) description.setText(breeding.getDescription());
        } else {
            setOnUsageView();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setOnUsageView() {
        okBtn.setOnClickListener(v -> finish());
        editBtn.setOnClickListener(v -> {
            if (breeding == null) {
                Intent intent = new Intent(this, BreedingActivityAdd.class);
                startActivityForResult(intent, 100);
            } else {
                Intent intent = new Intent(this, BreedingActivityEdit.class);
                startActivityForResult(intent, 100);
            }
        });
    }

}
