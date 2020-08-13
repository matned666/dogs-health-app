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
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.TeethControl;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.TeethControlDao;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.TreatmentDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;
import pl.design.mrn.matned.dogmanagementapp.listeners.PositionListener;

import static android.os.Environment.DIRECTORY_PICTURES;
import static android.os.Environment.getExternalStoragePublicDirectory;
import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;
import static pl.design.mrn.matned.dogmanagementapp.Statics.REQUEST_IMAGE_CAPTURE;
import static pl.design.mrn.matned.dogmanagementapp.TextStrings.WARNING_FIELD;
import static pl.design.mrn.matned.dogmanagementapp.TextStrings.WRONG_DATE;

public class TeethControlActivityEdit extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private Button save;
    private Button back;
    private Button delete;
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
        setContentView(R.layout.healthdata_teeth_control_add_edit);
        initViews();
//        onSavedReload(savedInstanceState);
        bitmap = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initViews() {
        save = findViewById(R.id.teeth_save_btn);
        back = findViewById(R.id.teeth_back_btn);
        delete = findViewById(R.id.teeth_delete_btn);
        descET = findViewById(R.id.teeth_description_dataText);
        dateET = findViewById(R.id.teeth_date_dataText);
        nextDateET = findViewById(R.id.teeth_next_date_dataText);
        noteET = findViewById(R.id.teeth_note_dataText);
        photoStampIV = findViewById(R.id.teeth_photo);
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
        TreatmentDao dao = new TreatmentDao(this);
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
                TeethControlDao dao = new TeethControlDao(this);
                TeethControl tc = new TeethControl();
                tc.setDescription(descET.getText().toString());
                try {
                    tc.setDateOfControl(dateFormat.parse(dateET.getText().toString()));
                } catch (ParseException e) {
                    Toast.makeText(this, WRONG_DATE, Toast.LENGTH_SHORT).show();
                }
                try {
                    tc.setDateOfNextControl(dateFormat.parse(nextDateET.getText().toString()));
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
        return checkET(dateET);
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
