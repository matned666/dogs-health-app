package pl.design.mrn.matned.dogmanagementapp.activity;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import pl.design.mrn.matned.dogmanagementapp.dataBase.Sex;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.DogDao;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.DogModel;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.Validate;

import static android.os.Environment.DIRECTORY_PICTURES;
import static android.os.Environment.getExternalStoragePublicDirectory;
import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;
import static pl.design.mrn.matned.dogmanagementapp.Statics.LIST_ELEMENT_ACTIVITY;
import static pl.design.mrn.matned.dogmanagementapp.TextStrings_PL.NOT_WORKING_YET;
import static pl.design.mrn.matned.dogmanagementapp.TextStrings_PL.WRONG_DATE;

public class Add_DogActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    private EditText dogNameET;
    private EditText dogRaceET;
    private EditText dogBirthDatePickerET;
    private EditText dogColorET;
    private Spinner dogSexET;
    private Button saveDog;
    private Button cancel;
    private Button galleryPicture;
    private ImageView dogPhoto;

    private Bitmap bitmap;

    private String photoPath;
    private String dogName;
    private String dogRace;
    private String dogBirthDatePicker;
    private String dogColor;

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    private Uri photoUri;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_dog);
        initializeFields_add();
        onSavedReload(savedInstanceState);
        bitmap = null;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initializeFields_add() {
        dogPhoto = findViewById(R.id.photoAdd);
        dogNameET = findViewById(R.id.dogNameAdd);
        dogRaceET = findViewById(R.id.dogRaceAdd);
        dogBirthDatePickerET = findViewById(R.id.dogDateOfBirthAdd);
        dogColorET = findViewById(R.id.dogColorAdd);
        dogSexET = findViewById(R.id.dogSexAdd);
        saveDog = findViewById(R.id.saveDogAdd);
        cancel = findViewById(R.id.cancelAdd);
        galleryPicture = findViewById(R.id.addDogDialogPictureFromGalleryAdd);
        initSpinner();
        initDatePicker();
        initClickListeners_add();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initClickListeners_add() {
        galleryPicture.setOnClickListener(v ->
                Toast.makeText(this, NOT_WORKING_YET, Toast.LENGTH_SHORT).show());
        cancel.setOnClickListener(c -> finish());
        if (this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
            dogPhoto.setOnClickListener(addPhoto());
        }
        saveDog.setOnClickListener(saveDog());
    }

    public void showDial(Class clazz, String el) {
        Intent intent = new Intent(Add_DogActivity.this, clazz);
        intent.putExtra(LIST_ELEMENT_ACTIVITY, el);
        startActivity(intent);
    }

    private void onSavedReload(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            dogName = savedInstanceState.getString("NAME");
            if (dogName != null) dogNameET.setText(dogName);
            dogRace = savedInstanceState.getString("RACE");
            if (dogRace != null) dogRaceET.setText(dogName);
            dogBirthDatePicker = savedInstanceState.getString("BIRTH_DATE");
            if (dogBirthDatePicker != null) dogBirthDatePickerET.setText(dogName);
            dogColor = savedInstanceState.getString("COLOR");
            if (dogColor != null) dogColorET.setText(dogName);
            int dogSex = 0;
            try {
                dogSex = savedInstanceState.getInt("SEX");
            } catch (NullPointerException ignored) {
            }
            dogSexET.setSelection(dogSex);
            if (photoPath != null) {
                photoUri = FileProvider.getUriForFile(Add_DogActivity.this, "pl.design.mrn.matned.dogmanagementapp.fileprovider", new File(photoPath));
                showImage();
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("NAME", dogName);
        outState.putString("RACE", dogRace);
        outState.putString("BIRTH_DATE", dogBirthDatePicker);
        outState.putString("COLOR", dogColor);
        outState.putInt("SEX", dogSexET.getSelectedItemPosition());
        outState.putString("PHOTO_PATH", photoPath);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                showImage();
            }
        }
    }


    private void showImage() {
        try {
            bitmap = ImageAdvancedFunction.handleSamplingAndRotationBitmap(Add_DogActivity.this, photoUri);
            Drawable d = new BitmapDrawable(getResources(), bitmap);
            dogPhoto.setImageDrawable(d);
            dogPhoto.setBackgroundResource(R.drawable.roundcornerimageframeblack);

        } catch (IOException e) {
            Log.d("myLog", "Except : " + e.toString());
        }

    }

    private View.OnClickListener addPhoto() {
        return v -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                photoFile = createImageFile();

                photoPath = photoFile.getAbsolutePath();
                photoUri = FileProvider.getUriForFile(Add_DogActivity.this, "pl.design.mrn.matned.dogmanagementapp.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private View.OnClickListener saveDog() {
        return v -> {
            if (validation()) {
                DogModel dogToSave = getDogModelFromFields();
                DogDao dao = new DogDao(this);
                dogToSave.setDogImage(photoPath);
                dao.add(dogToSave);
                Intent intent = new Intent(Add_DogActivity.this, StartActivity.class);
                startActivity(intent);
            }
        };
    }

    private DogModel getDogModelFromFields() {
        String dogName = dogNameET.getText().toString();
        String dogRace = dogRaceET.getText().toString();
        Date dogBirthDate = null;
        try {
            dogBirthDate = dateFormat.parse(dogBirthDatePickerET.getText().toString());
        } catch (ParseException e) {
            Toast.makeText(Add_DogActivity.this, WRONG_DATE, Toast.LENGTH_SHORT).show();
        }
        String dogColor = dogColorET.getText().toString();

        Sex dogSex = Sex.valueOf(dogSexET.getSelectedItem().toString());
        DogModel dogToSave = new DogModel(dogName, dogRace, dogBirthDate, dogColor, dogSex);
        dogToSave.setDogImage(photoPath);
        return dogToSave;
    }

    private boolean validation() {
        checkET(dogNameET);
        checkET(dogRaceET);
        checkET(dogColorET);
        checkSpinner(dogSexET);
        checkDate(dogBirthDatePickerET);
        return checkET(dogNameET) && checkET(dogRaceET) && checkET(dogColorET) && checkSpinner(dogSexET) && checkDate(dogBirthDatePickerET);
    }

    private boolean checkDate(EditText et) {
        if (!Validate.dateFormat(et.getText().toString())) {
            et.setBackgroundResource(R.drawable.roundcornerstextred);
            return false;
        } else {
            et.setBackgroundResource(R.drawable.roundcornerstext);
            return true;
        }
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

    private boolean checkSpinner(Spinner et) {
        if (!Validate.selectedSexIn(et)) {
            et.setBackgroundResource(R.drawable.roundcornerstextred);
            return false;
        } else {
            et.setBackgroundResource(R.drawable.roundcornerstext);
            return true;
        }
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
        dateFormat.setTimeZone(cal.getTimeZone());
        dogBirthDatePickerET.setText(dateFormat.format(cal.getTime()));
    }

    private void initDatePicker() {
        dogBirthDatePickerET.setOnClickListener(v -> datePickDialog());
        dogBirthDatePickerET.setRawInputType(InputType.TYPE_NULL);
        dogBirthDatePickerET.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) datePickDialog();
        });
    }

    private void datePickDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(Add_DogActivity.this, Add_DogActivity.this, year, month, day);
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

    private void initSpinner() {
        String[] sexes = new String[3];
        sexes[0] = "";
        sexes[1] = Sex.PIES.name();
        sexes[2] = Sex.SUKA.name();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.drpdn, sexes);
        dogSexET.setAdapter(adapter);
    }


}
