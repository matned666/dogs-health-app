package pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.add;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.Validate;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.SpecialSign;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.SpecialSignDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;
import pl.design.mrn.matned.dogmanagementapp.listeners.PositionListener;

public class UniqueSignActivityAdd extends AppCompatActivity {

    private Button ok;
    private Button cancel;
    private Button delete;

    private TextView descriptionTV;

    private SpecialSignDao dao;
    private SpecialSign sign;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.special_sign_dialog);
        initViews();
    }

    private void initViews() {
        ok = findViewById(R.id.okSignDialog);
        cancel = findViewById(R.id.cancelSignDialog);
        delete = findViewById(R.id.deleteSignDialog);
        delete.setVisibility(View.GONE);
        descriptionTV = findViewById(R.id.signDescription);
        dao = new SpecialSignDao(this);
        initEndingListeners();
    }

    private void initEndingListeners() {
        ok.setOnClickListener(v -> {
            if(Validate.checkET(descriptionTV)){
                sign = new SpecialSign();
                sign.setDogId(PositionListener.getInstance().getSelectedDogId());
                sign.setDescription(descriptionTV.getText().toString());
                dao.add(sign);
                finish();
            }
        });
        cancel.setOnClickListener(v -> finish());
        delete.setOnClickListener(v -> {
            dao.remove(sign);
            finish();
        });
    }


}
