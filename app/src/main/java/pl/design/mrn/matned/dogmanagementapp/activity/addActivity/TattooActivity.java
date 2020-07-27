package pl.design.mrn.matned.dogmanagementapp.activity.addActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import pl.design.mrn.matned.dogmanagementapp.listeners.PositionListener;
import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.activity.Add_DogActivity;
import pl.design.mrn.matned.dogmanagementapp.activity.Edit_DogActivity;

import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE;
import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE_ADD;
import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE_EDIT;
import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE_INFO;

public class TattooActivity extends AppCompatActivity {

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
            setContentView(R.layout.tattoo_dialog);
            initViews();
            if (usage.equals(USAGE_EDIT)) {
                fillAllFields();
            }
        }else{
            setContentView(R.layout.tattoo_info_dialog);
            initViews();
            fillAllFields();
        }
    }
    private void fillAllFields() {
        int dogId = PositionListener.getInstance().getPosition()+1;
    }

    private void initViews() {
        ok = findViewById(R.id.okTattooDialog);
        cancel = findViewById(R.id.cancelTattooDialog);
        initEndingListeners();
    }


    private void initEndingListeners() {
        ok.setOnClickListener(v -> {
            Intent intent;
            if (usage.equals(USAGE_ADD)) {
                intent = new Intent(this, Add_DogActivity.class);
                intent.putExtra(USAGE, USAGE_ADD);

            }
            else{
                intent = new Intent(this, Edit_DogActivity.class);
                intent.putExtra(USAGE, USAGE_EDIT);
            }
            startActivity(intent);
        });
        cancel.setOnClickListener(v -> finish());
    }

}
