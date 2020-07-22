package pl.design.mrn.matned.dogmanagementapp.activity.addActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import pl.design.mrn.matned.dogmanagementapp.PositionListener;
import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.activity.AddDogActivity;
import pl.design.mrn.matned.dogmanagementapp.activity.DogDataActivity;
import pl.design.mrn.matned.dogmanagementapp.activity.EditActivity;

import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE;
import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE_ADD;
import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE_EDIT;
import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE_INFO;

public class BreedingActivity extends AppCompatActivity {


    private static final String BREEDING_DESCRIPTION = "BREEDING_DESCRIPTION";
    private static final String BREEDING_EMAIL = "BREEDING_EMAIL";
    private static final String BREEDING_PHONE = "BREEDING_PHONE";
    private static final String BREEDING_ADDRESS = "BREEDING_ADDRESS";
    private static final String BREEDING_NAME = "BREEDING_NAME";

    private String usage;

    private Button ok;
    private Button cancel;

    private TextView breedingName;
    private TextView address;
    private TextView phone;
    private TextView email;
    private TextView description;


    @SuppressLint({"ResourceType", "InflateParams"})
    @NonNull
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        usage = intent.getStringExtra(USAGE);
        assert usage != null;
        if(!usage.equals(USAGE_INFO)) {
            setContentView(R.layout.breeding_dialog);
            init();
            if (usage.equals(USAGE_EDIT)) {
                fillAllFields();
            }
        }else{
            setContentView(R.layout.breeding_info_dialog);
            init();
            fillAllFields();

        }

    }

    private void init() {
        initFields();
        setOnUsageView();
    }

    private void initFields() {
        if(!usage.equals(USAGE_INFO)) {
            breedingName = findViewById(R.id.breedingName);
            address = findViewById(R.id.breedingAddress);
            phone = findViewById(R.id.breedingPhone);
            email = findViewById(R.id.breedingEmail);
            description = findViewById(R.id.breedingDescription);
        }else{
            breedingName = findViewById(R.id.breedingNameInfoTextView);
            address = findViewById(R.id.breedingAddressInfoTextView);
            phone = findViewById(R.id.breedingPhoneInfoTextView);
            email = findViewById(R.id.breedingEmailInfoTextView);
            description = findViewById(R.id.breedingDescriptionInfoTextView);
        }

    }

    private void fillAllFields() {
        int dogId = PositionListener.getInstance().getPosition()+1;
    }

    private void setOnUsageView() {
        ok = findViewById(R.id.okBreedingDialog);
        if (usage.equals(USAGE_ADD)) {
            getCancelButton();
            ok.setOnClickListener(v -> {
                Intent intent = new Intent(BreedingActivity.this, AddDogActivity.class);
                intent.putExtra(BREEDING_DESCRIPTION, description.getText().toString());
                intent.putExtra(BREEDING_EMAIL, email.getText().toString());
                intent.putExtra(BREEDING_PHONE, phone.getText().toString());
                intent.putExtra(BREEDING_ADDRESS, address.getText().toString());
                intent.putExtra(BREEDING_NAME, breedingName.getText().toString());
                startActivity(intent);
            });
        }else if(usage.equals(USAGE_EDIT)){
            getCancelButton();
            ok.setOnClickListener(v -> {
                Intent intent = new Intent(BreedingActivity.this, EditActivity.class);
                intent.putExtra(BREEDING_DESCRIPTION, description.getText().toString());
                intent.putExtra(BREEDING_EMAIL, email.getText().toString());
                intent.putExtra(BREEDING_PHONE, phone.getText().toString());
                intent.putExtra(BREEDING_ADDRESS, address.getText().toString());
                intent.putExtra(BREEDING_NAME, breedingName.getText().toString());
                startActivity(intent);
            });
        }else{
            ok.setOnClickListener(v -> {
                Intent intent = new Intent(BreedingActivity.this, DogDataActivity.class);
                startActivity(intent);
            });
        }

    }

    private void getCancelButton() {
        cancel = findViewById(R.id.cancelBreedingDialog);
        cancel.setOnClickListener(b-> {
            Intent intent = new Intent(BreedingActivity.this, AddDogActivity.class);
            startActivity(intent);
        });
    }
}
