package pl.design.mrn.matned.dogmanagementapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.activity.health.AllergiesActivity;
import pl.design.mrn.matned.dogmanagementapp.activity.health.BirthControlActivity;
import pl.design.mrn.matned.dogmanagementapp.activity.health.DeWormingActivity;
import pl.design.mrn.matned.dogmanagementapp.activity.health.InjectionsOtherActivity;
import pl.design.mrn.matned.dogmanagementapp.activity.health.InjectionsRabidActivity;
import pl.design.mrn.matned.dogmanagementapp.activity.health.TeethControlActivity;
import pl.design.mrn.matned.dogmanagementapp.activity.health.TreatmentActivity;

import static pl.design.mrn.matned.dogmanagementapp.Statics.*;

public class HealthActivity extends AppCompatActivity {

    private Button rabidInjection;
    private Button otherInjections;
    private Button deworming;
    private Button birthControl;
    private Button teethControl;
    private Button treatmentButton;
    private Button allergies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.health_activity);
        rabidInjection = findViewById(R.id.rabidInjectionBtn);
        otherInjections = findViewById(R.id.otherInjectionsButton);
        deworming = findViewById(R.id.dewormingButton);
        birthControl = findViewById(R.id.parentingButton);
        teethControl = findViewById(R.id.teethControllButton);
        treatmentButton = findViewById(R.id.illnessButton);
        allergies = findViewById(R.id.allergiesButton);
        onClickListeners();
    }

    private void onClickListeners() {
        rabidInjection.setOnClickListener(v -> startNewActivity(InjectionsRabidActivity.class, INJECTION));
        otherInjections.setOnClickListener(v -> startNewActivity(InjectionsOtherActivity.class, INJECTION));
        deworming.setOnClickListener(v -> startNewActivity(DeWormingActivity.class, DEWORMING));
        birthControl.setOnClickListener(v -> startNewActivity(BirthControlActivity.class, BIRTH));
        teethControl.setOnClickListener(v -> startNewActivity(TeethControlActivity.class, TEETH));
        treatmentButton.setOnClickListener(v -> startNewActivity(TreatmentActivity.class, TREATMENT));
        allergies.setOnClickListener(v -> startNewActivity(AllergiesActivity.class, ALLERGIES));
    }

    private void startNewActivity(Class clazz, String usage) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra(USAGE, usage);
        startActivity(intent);
    }


}
