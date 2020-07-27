package pl.design.mrn.matned.dogmanagementapp.activity.addActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import pl.design.mrn.matned.dogmanagementapp.listeners.PositionListener;
import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.activity.Add_DogActivity;
import pl.design.mrn.matned.dogmanagementapp.activity.Edit_DogActivity;

import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;
import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE;
import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE_ADD;
import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE_EDIT;
import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE_INFO;

public class NoteActivity extends AppCompatActivity {

    private Button ok;
    private Button cancel;

    private String usage;

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        usage = intent.getStringExtra(USAGE);
        assert usage != null;
        if(!usage.equals(USAGE_INFO)) {
            setContentView(R.layout.note_dialog);
            init();
            if (usage.equals(USAGE_EDIT)) {
                fillAllFields();
            }
        }else{
            setContentView(R.layout.note_info_dialog);
            init();
            fillAllFields();

        }
    }

    private void init() {
        initViews();
    }

    private void initViews() {
        ok = findViewById(R.id.okNoteDialog);
        cancel = findViewById(R.id.cancelNoteDialog);
        initEndingListeners();
    }
    private void fillAllFields() {
        int dogId = PositionListener.getInstance().getPosition()+1;
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
