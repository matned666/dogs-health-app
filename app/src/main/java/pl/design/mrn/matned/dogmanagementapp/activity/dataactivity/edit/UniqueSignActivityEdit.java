package pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.edit;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.Validate;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.SpecialSign;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.SpecialSignDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;

public class UniqueSignActivityEdit extends AppCompatActivity {

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
        fillAllFields();
    }

    private void initViews() {
        ok = findViewById(R.id.okSignDialog);
        cancel = findViewById(R.id.cancelSignDialog);
        delete = findViewById(R.id.deleteSignDialog);
        descriptionTV = findViewById(R.id.signDescription);
        dao = new SpecialSignDao(this);
        sign = dao.findById(DataPositionListener.getInstance().getSelectedItemId());
        initEndingListeners();
    }

    private void fillAllFields() {
        descriptionTV.setText(sign.getDescription());
    }

    private void initEndingListeners() {
        ok.setOnClickListener(v -> {
            if(Validate.checkET(descriptionTV)){
                sign.setDescription(descriptionTV.getText().toString());
                dao.update(sign);
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
