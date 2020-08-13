package pl.design.mrn.matned.dogmanagementapp.activity.health.add;

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
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.Deworming;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.DewormingDao;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.InjectionOther;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.InjectionOtherDao;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.TeethControl;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.TeethControlDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.PositionListener;

import static android.os.Environment.DIRECTORY_PICTURES;
import static android.os.Environment.getExternalStoragePublicDirectory;
import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;
import static pl.design.mrn.matned.dogmanagementapp.Statics.REQUEST_IMAGE_CAPTURE;
import static pl.design.mrn.matned.dogmanagementapp.TextStrings.WARNING_FIELD;
import static pl.design.mrn.matned.dogmanagementapp.TextStrings.WRONG_DATE;

public class InjectionsOtherActivityAdd extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private Button save;
    private Button back;
    private EditText medicineET;
    private EditText descET;
    private EditText dateET;
    private EditText nextDateET;
    private EditText noteET;
    private ImageView photoStampIV;

    private Bitmap bitmap;

    private String photoPath;

    private boolean isDatePutOrExp;


    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    private Uri photoUri;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.healthdata_other_deseases_vaxine_add_edit);
        initViews();
//        onSavedReload(savedInstanceState);
        bitmap = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initViews() {
        save = findViewById(R.id.otherVaxine_save_btn);
        back = findViewById(R.id.otherVaxine_back_btn);
        Button delete = findViewById(R.id.otherVaxine_delete_btn);
        medicineET = findViewById(R.id.otherVaxine_name_textData);
        descET = findViewById(R.id.otherVaxine_description_textData);
        dateET = findViewById(R.id.otherVaxine_date_textData);
        nextDateET = findViewById(R.id.otherVaxine_next_date_textData);
        noteET = findViewById(R.id.otherVaxine_note_textData);
        photoStampIV = findViewById(R.id.otherVaxine_notephoto);
        delete.setVisibility(View.GONE);
        isDatePutOrExp = true;
        initDatePicker();
        setOnClickListeners();
        initDatePicker();
    }

//    private void onSavedReload(Bundle savedInstanceState) {
//        if (savedInstanceState != null) {
//            String description = savedInstanceState.getString("RACE");
//            if (description != null) descET.setText(description);
//            String date = savedInstanceState.getString("BIRTH_DATE");
//            if (date != null) dateET.setText(date);
//            String nextDate = savedInstanceState.getString("COLOR");
//            if (nextDate != null) nextDateET.setText(nextDate);
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
        save.setOnClickListener(addClickListener());
        back.setOnClickListener(v -> finish());
        if (this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
            photoStampIV.setOnClickListener(addPhoto());
        }
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
                InjectionOtherDao dao = new InjectionOtherDao(this);
                InjectionOther tc = new InjectionOther();
                tc.setMedicine(medicineET.getText().toString());
                tc.setDescription(descET.getText().toString());
                try {
                    tc.setTreatmentDate(dateFormat.parse(dateET.getText().toString()));
                } catch (ParseException e) {
                    Toast.makeText(this, WRONG_DATE, Toast.LENGTH_SHORT).show();
                }
                try {
                    tc.setNextTreatment(dateFormat.parse(nextDateET.getText().toString()));
                } catch (ParseException e) {
                    Toast.makeText(this, WRONG_DATE, Toast.LENGTH_SHORT).show();
                }
                tc.setNote(noteET.getText().toString());
                tc.setPhoto(photoPath);
                tc.setDogId(PositionListener.getInstance().getSelectedDogId());
                dao.add(tc);
                finish();
            }else{
                Toast.makeText(this, WARNING_FIELD, Toast.LENGTH_SHORT).show();
            }
        };
    }


    private boolean validation() {
        boolean b1 = checkET(dateET);
        boolean b2 = checkET(nextDateET);
        boolean b3 = checkET2(medicineET);
        return b1 && b2 && b3;
    }

    private boolean checkET(EditText et) {
        if (!Validate.dateFormat(et.getText().toString())) {
            et.setBackgroundResource(R.drawable.roundcornerstextred);
            return false;
        } else {
            et.setBackgroundResource(R.drawable.roundcornerstext);
            return true;
        }
    }

    private boolean checkET2(EditText et) {
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
        if (isDatePutOrExp) dateET.setText(dateFormat.format(cal.getTime()));
        else nextDateET.setText(dateFormat.format(cal.getTime()));
    }

    private void initDatePicker() {
        dateET.setOnClickListener(v -> {
            isDatePutOrExp = true;
            datePickDialog();
        });
        dateET.setRawInputType(InputType.TYPE_NULL);
        dateET.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                isDatePutOrExp = true;
                datePickDialog();
            }
        });
        nextDateET.setOnClickListener(v -> {
            isDatePutOrExp = false;
            datePickDialog();
        });
        nextDateET.setRawInputType(InputType.TYPE_NULL);
        nextDateET.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                isDatePutOrExp = false;
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
        String imageFileName = "dogPhoto_" + timeStamp + "_";
        File storageDir = getExternalStoragePublicDirectory(DIRECTORY_PICTURES);
        File image = null;
        image = new File(storageDir.getParent() + imageFileName + ".jpg");
        return image;
    }


}
