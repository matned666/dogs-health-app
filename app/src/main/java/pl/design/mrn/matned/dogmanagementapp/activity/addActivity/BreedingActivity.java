package pl.design.mrn.matned.dogmanagementapp.activity.addActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.Validate;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.Breeding;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.BreedingDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.PositionListener;
import pl.design.mrn.matned.dogmanagementapp.R;

import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE;
import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE_ADD;
import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE_EDIT;
import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE_INFO;

public class BreedingActivity extends AppCompatActivity {


    private String usage;

    private Button ok;

    private TextView breedingName;
    private TextView address;
    private TextView phone;
    private TextView email;
    private TextView description;

    private BreedingDao dao;
    private Breeding breeding;
    private PositionListener listener;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint({"ResourceType", "InflateParams"})
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        usage = intent.getStringExtra(USAGE);
        dao = new BreedingDao(this);
        listener = PositionListener.getInstance();
        assert usage != null;
        if (!usage.equals(USAGE_INFO)) {
            setContentView(R.layout.breeding_dialog);
            init();
            if (usage.equals(USAGE_EDIT)) {
                fillAllFields();
            }
        } else {
            setContentView(R.layout.breeding_info_dialog);
            init();
            fillAllFields();
        }
        setOnUsageView();
    }

    private void init() {
        initFields();
    }

    private void initFields() {
        if (!usage.equals(USAGE_INFO)) {
            breedingName = findViewById(R.id.breedingName);
            address = findViewById(R.id.breedingAddress);
            phone = findViewById(R.id.breedingPhone);
            email = findViewById(R.id.breedingEmail);
            description = findViewById(R.id.breedingDescription);
            ok = findViewById(R.id.okBreedingDialog);
        } else {
            breedingName = findViewById(R.id.breedingNameInfoTextView);
            address = findViewById(R.id.breedingAddressInfoTextView);
            phone = findViewById(R.id.breedingPhoneInfoTextView);
            email = findViewById(R.id.breedingEmailInfoTextView);
            description = findViewById(R.id.breedingDescriptionInfoTextView);
            ok = findViewById(R.id.okBreedingDialogInfo);

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void fillAllFields() {
        int dogId = PositionListener.getInstance().getSelectedDogId();
        breeding = dao.findByDogId(dogId);
        if(breeding != null) {
            breedingName.setText(breeding.getName());
            address.setText(breeding.getAddress());
            phone.setText(breeding.getPhone());
            email.setText(breeding.getEmail());
            description.setText(breeding.getDescription());
        }else{
            usage = USAGE_ADD;
            setOnUsageView();
        }

    }

    private void setOnUsageView() {
        if (usage.equals(USAGE_ADD)) {
            getCancelButton();
            ok.setOnClickListener(v -> {
                if (validation()) {
                    breeding = new Breeding(-1);
                    generateBreeding();
                    dao.add(breeding);
                    finish();
                }
            });
        } else if (usage.equals(USAGE_EDIT)) {
            getCancelButton();
            ok.setOnClickListener(v -> {
                if (validation()) {
                    int id = breeding.getBreedingId();
                    generateBreeding();
                    dao.update(id, breeding);
                    finish();
                }
            });
        } else {
            ok.setOnClickListener(v -> finish());
        }

    }

    private void generateBreeding() {
        breeding.setName(breedingName.getText().toString());
        breeding.setDescription(description.getText().toString());
        breeding.setAddress(address.getText().toString());
        breeding.setPhone(phone.getText().toString());
        breeding.setEmail(email.getText().toString());
        breeding.setDogId(listener.getSelectedDogId());
    }

    private void getCancelButton() {
        Button cancel = findViewById(R.id.cancelBreedingDialog);
        cancel.setOnClickListener(b -> finish());
    }

    private boolean validation() {
        boolean b1 = checkET(breedingName);
        boolean b2 = checkET(address);
        boolean b3 = checkET(phone);
        boolean b4 = checkET(email);
        boolean b5 = checkET(description);
        return b1 && b2 && b3 && b4 && b5;
    }


    private boolean checkET(TextView et) {
        if (!Validate.checkText(et.getText().toString())) {
            et.setBackgroundResource(R.drawable.roundcornerstextred);
            return false;
        } else {
            et.setBackgroundResource(R.drawable.roundcornerstext);
            return true;
        }
    }
}
