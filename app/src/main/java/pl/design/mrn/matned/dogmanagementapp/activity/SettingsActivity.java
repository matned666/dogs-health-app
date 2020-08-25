package pl.design.mrn.matned.dogmanagementapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.dataBase.app.Configuration;
import pl.design.mrn.matned.dogmanagementapp.dataBase.app.ConfigurationDao;

public class SettingsActivity extends AppCompatActivity {

    private Switch vaccineAlert;
    private Switch dewormingAlert;
    private Switch birthdayAlert;
    private Switch otherAlert;
    private Button backBtn;

    private ConfigurationDao dao;
    private Configuration configuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        dao = new ConfigurationDao(this);
        configuration = dao.get();
        initViews();
        initClickListeners();
        initSwitches();
    }

    private void initSwitches() {
        vaccineAlert.setChecked(configuration.getIsVaccineNotificationOn() == 1);
        dewormingAlert.setChecked(configuration.getIsDewormingNotificationOn() == 1);
        birthdayAlert.setChecked(configuration.getIsBirthdayNotificationOn() == 1);
        otherAlert.setChecked(configuration.getIsOtherNotificationOn() == 1);
    }

    private void initViews() {
        vaccineAlert = findViewById(R.id.vaccine_alert_switch);
        dewormingAlert = findViewById(R.id.deworming_alert_switch);
        birthdayAlert = findViewById(R.id.birthday_alert_switch);
        otherAlert = findViewById(R.id.notifications_switch);
        backBtn = findViewById(R.id.back_btn);

    }

    private void initClickListeners() {
        backBtn.setOnClickListener(back -> finish());
        vaccineAlert.setOnCheckedChangeListener((buttonView, isChecked) -> {
            configuration.setIsVaccineNotificationOn((byte) (isChecked? 1 : 0));
            dao.update(configuration);
        });
        dewormingAlert.setOnCheckedChangeListener((buttonView, isChecked) -> {
            configuration.setIsDewormingNotificationOn((byte) (isChecked? 1 : 0));
            dao.update(configuration);
        });
        birthdayAlert.setOnCheckedChangeListener((buttonView, isChecked) -> {
            configuration.setIsBirthdayNotificationOn((byte) (isChecked? 1 : 0));
            dao.update(configuration);
        });
        otherAlert.setOnCheckedChangeListener((buttonView, isChecked) -> {
            configuration.setIsOtherNotificationOn((byte) (isChecked? 1 : 0));
            dao.update(configuration);
        });
    }


}
