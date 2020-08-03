package pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.edit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
import pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.add.BreedingActivityAdd;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.Validate;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.Breeding;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.BreedingDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.PositionListener;

import static pl.design.mrn.matned.dogmanagementapp.Statics.DATA_SPLITMENT_REGEX;

public class BreedingActivityEdit extends AppCompatActivity {



    private Button ok;
    private Button cancelOrEdit;
    private Button deleteBreedingBtn;

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
        init();
        if (breeding != null) {
            fillAllFields();
        } else {
            Intent intent = new Intent(this, BreedingActivityAdd.class);
            startActivity(intent);
            finish();
        }
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
        deleteBreedingBtn = findViewById(R.id.deleteBreedingDialog);
        cancelOrEdit = findViewById(R.id.cancelBreedingDialog);
        cancelOrEdit.setText("Anuluj");
        dao = new BreedingDao(this);
        breeding = dao.findByDogId(PositionListener.getInstance().getSelectedDogId());
        initSpinner();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initSpinner() {
        List<Breeding> breedings = dao.findAll();
        String[] array = getBredingsNamesList(breedings);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.drpdn, array);
        breedingSpinner.setAdapter(adapter);
        breedingSpinner.setOnItemClickListener(clickItem());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private AdapterView.OnItemClickListener clickItem() {
        return (parent, view, position, id) -> {
            int breedingId = breeding.getBreedingId();
            breeding = dao.findById(getTheSelectedBreedingId((String) parent.getSelectedItem()));
            breeding.setBreedingId(breedingId);
            breeding.setDogId(PositionListener.getInstance().getSelectedDogId());
            fillAllFields();
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
        if (breeding.getName() != null)
            breedingName.setText(breeding.getName());
        if (breeding.getAddress() != null)
            address.setText(breeding.getAddress());
        if (breeding.getPhone() != null)
            phone.setText(breeding.getPhone());
        if (breeding.getEmail() != null)
            email.setText(breeding.getEmail());
        if (breeding.getDescription() != null)
            description.setText(breeding.getDescription());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setOnUsageView() {
        cancelOrEdit.setOnClickListener(b -> {
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_CANCELED,returnIntent);
            finish();
        });
        ok.setOnClickListener(v -> {
            if (validation()) {
                generateBreeding();
                dao.update(breeding);
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
        deleteBreedingBtn.setOnClickListener(c -> {
            dao.remove(breeding);
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        });
    }


    private void generateBreeding() {
        breeding.setName(breedingName.getText().toString());
        breeding.setDescription(description.getText().toString());
        breeding.setAddress(address.getText().toString());
        breeding.setPhone(phone.getText().toString());
        breeding.setEmail(email.getText().toString());
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
