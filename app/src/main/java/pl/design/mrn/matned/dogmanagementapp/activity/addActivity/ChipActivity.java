package pl.design.mrn.matned.dogmanagementapp.activity.addActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import pl.design.mrn.matned.dogmanagementapp.PositionListener;
import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.activity.AddDogActivity;

import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE;
import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE_EDIT;
import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE_INFO;

public class ChipActivity extends AppCompatActivity {

    private Button ok;
    private Button cancel;
    private String usage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        usage = intent.getStringExtra(USAGE);
        assert usage != null;
        if(!usage.equals(USAGE_INFO)) {
            setContentView(R.layout.chip_dialog);
            init();
            if (usage.equals(USAGE_EDIT)) {
                fillAllFields();
            }
        }else{
            setContentView(R.layout.chip_info_dialog);
            init();
            fillAllFields();

        }
    }

    private void init() {
        initViews();
        initEndingListeners();

    }

    private void initViews() {
        ok = findViewById(R.id.okChipDialog);
        cancel = findViewById(R.id.cancelChipDialog);
    }

    private void fillAllFields() {
        int dogId = PositionListener.getInstance().getPosition()+1;
    }


    private void initEndingListeners() {
        ok.setOnClickListener(v -> {
            Intent intent = new Intent(ChipActivity.this, AddDogActivity.class);
            startActivity(intent);
        });
        cancel.setOnClickListener(v -> {
            Intent intent = new Intent(ChipActivity.this, AddDogActivity.class);
            startActivity(intent);
        });
    }


}
