package pl.design.mrn.matned.dogmanagementapp.activity.addActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.activity.AddDogActivity;

import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE_EDIT;

public class TattooActivity extends AppCompatActivity {

    private Button ok;
    private Button cancel;
    private String usage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tattoo_dialog);
        initViews();
        if(usage.equals(USAGE_EDIT)) {
            fillAllFields();
        }
    }
    private void fillAllFields() {
//        TODO
    }

    private void initViews() {
        ok = findViewById(R.id.okTattooDialog);
        cancel = findViewById(R.id.cancelTattooDialog);
        initEndingListeners();
    }


    private void initEndingListeners() {
        ok.setOnClickListener(v -> {
            Intent intent = new Intent(TattooActivity.this, AddDogActivity.class);
            startActivity(intent);
        });
        cancel.setOnClickListener(v -> {
            Intent intent = new Intent(TattooActivity.this, AddDogActivity.class);
            startActivity(intent);
        });
    }


}
