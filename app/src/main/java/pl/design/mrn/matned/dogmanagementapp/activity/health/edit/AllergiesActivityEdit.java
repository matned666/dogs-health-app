package pl.design.mrn.matned.dogmanagementapp.activity.health.edit;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import pl.design.mrn.matned.dogmanagementapp.ImageAdvancedFunction;
import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.Validate;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.Allergies;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.AllergiesDao;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.TreatmentDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;
import pl.design.mrn.matned.dogmanagementapp.listeners.PositionListener;

import static android.os.Environment.DIRECTORY_PICTURES;
import static android.os.Environment.getExternalStoragePublicDirectory;
import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;
import static pl.design.mrn.matned.dogmanagementapp.Statics.REQUEST_IMAGE_CAPTURE;
import static pl.design.mrn.matned.dogmanagementapp.TextStrings.WARNING_FIELD;
import static pl.design.mrn.matned.dogmanagementapp.TextStrings.WRONG_DATE;

public class AllergiesActivityEdit extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{


    private Button save;
    private Button back;
    private Button delete;
    private EditText nameET;
    private EditText descET;
    private EditText dateOfDiscoveryET;
    private EditText dateOfTreatmentET;
    private EditText nextDateOfTreatmentET;
    private EditText noteET;
    private ImageView photoStampIV;

    private Bitmap bitmap;

    private String photoPath;

    private int datePut;
    private AllergiesDao dao;
    private Allergies allergy;

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    private Uri photoUri;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.healthdata_allergy_add_edit);
        initViews();
//        onSavedReload(savedInstanceState);
        bitmap = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initViews() {
        dao = new AllergiesDao(this);
        allergy = dao.findById(DataPositionListener.getInstance().getSelectedItemId());
        save = findViewById(R.id.allergy_save_btn);
        back = findViewById(R.id.allergy_back_btn);
        delete = findViewById(R.id.allergy_delete_btn);
        nameET = findViewById(R.id.allergen_name_textData);
        descET = findViewById(R.id.allergen_description_textData);
        dateOfDiscoveryET = findViewById(R.id.allergen_discoveryDate_textData);
        dateOfTreatmentET = findViewById(R.id.allergen_treatmentStartDate_textData);
        nextDateOfTreatmentET = findViewById(R.id.allergen_treatmentNextDate_textData);
        noteET = findViewById(R.id.allergen_note_textData);
        photoStampIV = findViewById(R.id.allergy_photo);
        datePut = 0;
        initDatePicker();
        setOnClickListeners();
        fillAllFields();
        initDatePicker();
    }

    private void fillAllFields() {
        nameET.setText(allergy.getAllergen());
        descET.setText(allergy.getDescription());
        if(allergy.getDateOfDetection() != null) dateOfDiscoveryET.setText(dateFormat.format(allergy.getDateOfDetection()));
        if(allergy.getDateOfTreatment() != null) dateOfTreatmentET.setText(dateFormat.format(allergy.getDateOfTreatment()));
        if(allergy.getDateOfNextTreatment() != null) nextDateOfTreatmentET.setText(dateFormat.format(allergy.getDateOfNextTreatment()));
        noteET.setText(allergy.getNote());
    }

//    private void onSavedReload(Bundle savedInstanceState) {
//        if (savedInstanceState != null) {
//            String name = savedInstanceState.getString("ALLERGEN");
//            if (name != null) descET.setText(name);
//            String description = savedInstanceState.getString("DESCRIPTION");
//            if (description != null) descET.setText(description);
//            String date = savedInstanceState.getString("BIRTH_DATE");
//            if (date != null) dateOfTreatmentET.setText(date);
//            String nextDate = savedInstanceState.getString("COLOR");
//            if (nextDate != null) nextDateOfTreatmentET.setText(nextDate);
//            String note = savedInstanceState.getString("COLOR");
//            if (note != null) noteET.setText(note);
//            if (photoPath != null) {
//                photoUri = FileProvider.getUriForFile(this, "pl.design.mrn.matned.dogmanagementapp.fileprovider", new File(photoPath));
//                showImage();
//            }
//        }
//    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setOnClickListeners() {
        delete.setOnClickListener(v->{
            deleteRecord();
        });
        save.setOnClickListener(addClickListener());
        back.setOnClickListener(v -> finish());
        if (this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
            photoStampIV.setOnClickListener(addPhoto());
        }
    }

    private void deleteRecord() {
        dao.remove(DataPositionListener.getInstance().getSelectedItemId());
        finish();
    }

    private View.OnClickListener addPhoto() {
        return v -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                photoFile = createImageFile();
                photoPath = photoFile.getAbsolutePath();
                photoUri = FileProvider.getUriForFile(this, "pl.design.mrn.matned.dogmanagementapp.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        };    }


    private View.OnClickListener addClickListener() {
        return v -> {
            if(validation()) {
                allergy.setAllergen(nameET.getText().toString());
                allergy.setDescription(descET.getText().toString());
                try {
                    allergy.setDateOfDetection(dateFormat.parse(dateOfDiscoveryET.getText().toString()));
                } catch (ParseException e) {
                    Toast.makeText(this, WRONG_DATE, Toast.LENGTH_SHORT).show();
                }
                try {
                    allergy.setDateOfTreatment(dateFormat.parse(dateOfTreatmentET.getText().toString()));
                } catch (ParseException ignored) {
                }
                try {
                    allergy.setDateOfNextTreatment(dateFormat.parse(nextDateOfTreatmentET.getText().toString()));
                } catch (ParseException ignored) {
                }
                allergy.setNote(noteET.getText().toString());
                allergy.setPhoto(photoPath);
                allergy.setDogId(PositionListener.getInstance().getSelectedDogId());
                dao.update(allergy);
                finish();
            }else{
                Toast.makeText(this, WARNING_FIELD, Toast.LENGTH_SHORT).show();
            }
        };
    }


    private boolean validation() {
        return checkET(nameET);
    }

    private boolean checkET(EditText et) {
        if (!Validate.notEmpty(et.getText().toString())) {
            et.setBackgroundResource(R.drawable.roundcornerstextred);
            return false;
        } else {
            et.setBackgroundResource(R.drawable.roundcornerstext);
            return true;
        }
    }

    private void showImage() {
        try {
            bitmap = ImageAdvancedFunction.handleSamplingAndRotationBitmap(this, photoUri);
            Drawable d = new BitmapDrawable(getResources(), bitmap);
            photoStampIV.setImageDrawable(d);
            photoStampIV.setBackgroundResource(R.drawable.roundcornerimageframeblack);

        } catch (IOException e) {
            Log.d("myLog", "Except : " + e.toString());
        }
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
        dateFormat.setTimeZone(cal.getTimeZone());
        if (datePut == 1) dateOfTreatmentET.setText(dateFormat.format(cal.getTime()));
        else if (datePut == 2) nextDateOfTreatmentET.setText(dateFormat.format(cal.getTime()));
        else dateOfDiscoveryET.setText(dateFormat.format(cal.getTime()));
    }

    private void initDatePicker() {
        dateOfTreatmentET.setOnClickListener(v -> {
            datePut = 1;
            datePickDialog();
        });
        dateOfTreatmentET.setRawInputType(InputType.TYPE_NULL);
        dateOfTreatmentET.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                datePut = 1;
                datePickDialog();
            }
        });
        nextDateOfTreatmentET.setOnClickListener(v -> {
            datePut = 2;
            datePickDialog();
        });
        nextDateOfTreatmentET.setRawInputType(InputType.TYPE_NULL);
        nextDateOfTreatmentET.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                datePut = 2;
                datePickDialog();
            }
        });

        dateOfDiscoveryET.setOnClickListener(v -> {
            datePut = 3;
            datePickDialog();
        });
        dateOfDiscoveryET.setRawInputType(InputType.TYPE_NULL);
        dateOfDiscoveryET.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                datePut = 3;
                datePickDialog();
            }
        });
    }

    private void datePickDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(this, this, year, month, day);
        datePicker.show();
    }

    private File createImageFile() {
        @SuppressLint("SimpleDateFormat")
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "allergyPhoto_" + timeStamp + "_";
        File storageDir = getExternalStoragePublicDirectory(DIRECTORY_PICTURES);
        File image = null;
        image = new File(storageDir.getParent() + imageFileName + ".jpg");
        return image;
    }


}

