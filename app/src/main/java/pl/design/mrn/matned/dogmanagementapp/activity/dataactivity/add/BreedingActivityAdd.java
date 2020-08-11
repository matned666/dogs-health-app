package pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.add;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.Validate;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.Breeding;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.BreedingDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.PositionListener;

import static pl.design.mrn.matned.dogmanagementapp.Statics.DATA_SPLITMENT_REGEX;
import static pl.design.mrn.matned.dogmanagementapp.TextStrings.CANCEL;

public class BreedingActivityAdd extends AppCompatActivity {


    private Button ok;
    private Button cancelOrEdit;

    private Spinner breedingSpinner;

    private TextView breedingName;
    private TextView address;
    private TextView phone;
    private TextView email;
    private TextView description;

    private BreedingDao dao;
    private Breeding breeding;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint({"ResourceType", "InflateParams"})
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.breeding_dialog);
        init();
        setOnUsageView();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    private void init() {

        breedingSpinner = findViewById(R.id.breedingDataSpinner);
        breedingName = findViewById(R.id.breedingName);
        address = findViewById(R.id.breedingAddress);
        phone = findViewById(R.id.breedingPhone);
        email = findViewById(R.id.breedingEmail);
        description = findViewById(R.id.breedingDescription);
        ok = findViewById(R.id.okBreedingDialog);
        Button deleteBreedingBtn = findViewById(R.id.deleteBreedingDialog);
        deleteBreedingBtn.setVisibility(View.GONE);
        cancelOrEdit = findViewById(R.id.cancelBreedingDialog);
        cancelOrEdit.setText(CANCEL);
        dao = new BreedingDao(this);
        initSpinner();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initSpinner() {
        List<Breeding> breedings = dao.findAll();
        String[] array = getBredingsNamesList(breedings);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.drpdn, array);
        breedingSpinner.setAdapter(adapter);
        breedingSpinner.setOnItemSelectedListener(clickItem());
    }

    private AdapterView.OnItemSelectedListener clickItem() {
        return new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int breedingId = breeding.getBreedingId();
                breeding = dao.findById(getTheSelectedBreedingId((String) parent.getSelectedItem()));
                breeding.setBreedingId(breedingId);
                breeding.setDogId(PositionListener.getInstance().getSelectedDogId());
                fillAllFields();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }


    private int getTheSelectedBreedingId(String str) {
        String[] temp = str.split(DATA_SPLITMENT_REGEX);
        return Integer.parseInt(temp[0].trim());
    }

    private String[] getBredingsNamesList(List<Breeding> breedings) {
        String[] array = new String[breedings.size()];
        for (int i = 0; i < array.length; i++) {
            String data = breedings.get(i).getBreedingId() + " " + DATA_SPLITMENT_REGEX + " " + breedings.get(i).getName() + " " + breedings.get(i).getEmail();
            array[i] = data;
        }
        return array;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void fillAllFields() {
        breedingName.setText(breeding.getName());
        address.setText(breeding.getAddress());
        phone.setText(breeding.getPhone());
        email.setText(breeding.getEmail());
        description.setText(breeding.getDescription());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setOnUsageView() {
        cancelOrEdit.setOnClickListener(b -> finish());
        ok.setOnClickListener(v -> {
            if (validation()) {
                generateBreeding();
                dao.add(breeding);
                finish();
            }
        });
    }


    private void generateBreeding() {
        breeding = new Breeding();
        breeding.setName(breedingName.getText().toString());
        breeding.setDescription(description.getText().toString());
        breeding.setAddress(address.getText().toString());
        breeding.setPhone(phone.getText().toString());
        breeding.setEmail(email.getText().toString());
        breeding.setDogId(PositionListener.getInstance().getSelectedDogId());
    }

    private boolean validation() {
        return checkET(breedingName);
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
