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
import android.os.Environment;
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
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.Breeding;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.Chip;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.Note;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.SpecialSign;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.Tattoo;
import pl.design.mrn.matned.dogmanagementapp.dataBase.owner.Owner;

import static android.os.Environment.getExternalStoragePublicDirectory;
import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;

public class AddDogActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    private EditText dogNameET;
    private EditText dogRaceET;
    private EditText dogBirthDatePickerET;
    private EditText dogColorET;
    private Spinner dogSexET;
    private Button chipBtn;
    private Button tattooBtn;
    private Button signsBtn;
    private Button notesBtn;
    private Button breedingBtn;
    private Button ownerBtn;
    private Button saveDog;
    private ImageView dogPhoto;

    private Bitmap bitmap;

    private String photoPath;
    private String dogName;
    private String dogRace;
    private String dogBirthDatePicker;
    private String dogColor;
    private Integer dogSex;
    private Chip chip;
    private Tattoo tattoo;
    private SpecialSign signs;
    private Note notes;
    private Breeding breeding;
    private Owner owner;

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    private Uri photoUri;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_dog);
        bitmap = null;
        dogPhoto = findViewById(R.id.photoAdd);
        if (savedInstanceState != null){
            photoUri = (Uri) savedInstanceState.get("URI");
            dogName = (String) savedInstanceState.get("NAME");
            if (dogName != null) dogNameET.setText(dogName);
            dogRace = (String) savedInstanceState.get("RACE");
            if (dogRace != null) dogRaceET.setText(dogName);
            dogBirthDatePicker = (String) savedInstanceState.get("BIRTH_DATE");
            if (dogBirthDatePicker != null) dogBirthDatePickerET.setText(dogName);
            dogColor = (String) savedInstanceState.get("COLOR");
            if (dogColor != null) dogColorET.setText(dogName);
            dogSex = (Integer) savedInstanceState.get("SEX");
            chip = (Chip) savedInstanceState.get("CHIP");
            tattoo = (Tattoo) savedInstanceState.get("TATTOO");
            signs = (SpecialSign) savedInstanceState.get("SIGN");
            notes = (Note) savedInstanceState.get("NOTE");
            breeding = (Breeding) savedInstanceState.get("BREEDING");
            owner = (Owner) savedInstanceState.get("OWNER");
            if (photoUri != null) {
                showImage();
            }
        }
        initializeFields();

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("NAME",  dogName);
        outState.putString("RACE",  dogRace);
        outState.putString("BIRTH_DATE",  dogBirthDatePicker);
        outState.putString("COLOR",  dogColor);
        outState.putInt("SEX",  dogSexET.getSelectedItemPosition());
        outState.putSerializable("CHIP", chip);
        outState.putSerializable("TATTOO", tattoo);
        outState.putSerializable("SIGN", signs);
        outState.putSerializable("NOTE", notes);
        outState.putSerializable("BREEDING", breeding);
        outState.putSerializable("OWNER", owner);
        outState.putParcelable("URI", photoUri);
        super.onSaveInstanceState(outState);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
        dateFormat.setTimeZone(cal.getTimeZone());
        dogBirthDatePickerET.setText(dateFormat.format(cal.getTime()));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initializeFields() {
        dogNameET = findViewById(R.id.dogNameAdd);
        dogRaceET = findViewById(R.id.dogRaceAdd);
        dogBirthDatePickerET = findViewById(R.id.dogDateOfBirthAdd);
        dogColorET = findViewById(R.id.dogColorAdd);
        dogSexET = findViewById(R.id.dogSexAdd);
        chipBtn = findViewById(R.id.chipBtnAdd);
        tattooBtn = findViewById(R.id.tattooBtnAdd);
        signsBtn = findViewById(R.id.signsBtnAdd);
        notesBtn = findViewById(R.id.notesBtnAdd);
        breedingBtn = findViewById(R.id.breedingBtnAdd);
        ownerBtn = findViewById(R.id.ownerBtnAdd);
        saveDog = findViewById(R.id.saveDogAdd);
        initSpinner();
        initDatePicker();
        if(this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY))
        {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 2);
            dogPhoto.setOnClickListener(addPhoto());
        }
        saveDog.setOnClickListener(saveDog());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if(requestCode == REQUEST_IMAGE_CAPTURE ) {
                showImage();
            }
        }
    }

    private void showImage() {
        try {
            bitmap = ImageAdvancedFunction.handleSamplingAndRotationBitmap(AddDogActivity.this, photoUri);
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

                if(photoFile != null) {
                    photoPath = photoFile.getAbsolutePath();
                    photoUri = FileProvider.getUriForFile(AddDogActivity.this, "pl.design.mrn.matned.dogmanagementapp.fileprovider", photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private View.OnClickListener saveDog() {
        return v -> {
            if(validation()) {
                String dogName = dogNameET.getText().toString();
                String dogRace = dogRaceET.getText().toString();
                Date dogBirthDate = null;
                try {
                    dogBirthDate = dateFormat.parse(dogBirthDatePickerET.getText().toString());
                } catch (ParseException e) {
                    dogBirthDate = new Date();
                    Toast.makeText(AddDogActivity.this, "Wrong date", Toast.LENGTH_SHORT).show();
                }
                String dogColor = dogColorET.getText().toString();
                Sex dogSex = Sex.valueOf(dogSexET.getSelectedItem().toString());
                DogDao dao = new DogDao(this);
                dao.add(new DogModel(-1, dogName, dogRace, dogBirthDate, dogColor, dogSex));
                Intent intent = new Intent(AddDogActivity.this, StartActivity.class);
                startActivityForResult(intent, 200);
            }
        };
    }

    private boolean validation() {
        return checkET(dogNameET) && checkET(dogRaceET) && checkET(dogColorET) && checkSpinner(dogSexET) && checkDate(dogBirthDatePickerET);
    }

    private boolean checkDate(EditText et) {
        if(!Validate.dateFormat(et.getText().toString())) {
            et.setBackgroundResource(R.drawable.roundcornerstextred);
            return false;
        }else{
            et.setBackgroundResource(R.drawable.roundcornerstext);
            return true;
        }
    }

    private boolean checkSpinner(Spinner et) {
        if(!Validate.selectedSexIn(et)) {
            et.setBackgroundResource(R.drawable.roundcornerstextred);
            return false;
        }else{
            et.setBackgroundResource(R.drawable.roundcornerstext);
            return true;
        }
    }

    private boolean checkET(EditText et){
        if(!Validate.checkText(et.getText().toString())) {
            et.setBackgroundResource(R.drawable.roundcornerstextred);
            return false;
        }else{
            et.setBackgroundResource(R.drawable.roundcornerstext);
            return true;
        }
    }

    private void initDatePicker() {
        dogBirthDatePickerET.setOnClickListener(v -> datePickDialog());
        dogBirthDatePickerET.setRawInputType(InputType.TYPE_NULL);
        dogBirthDatePickerET.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) datePickDialog();
        });
    }

    private void initSpinner() {
        ArrayAdapter<CharSequence> qual_adapter = ArrayAdapter.createFromResource(this, R.array.sexTypes,R.layout.spinner_item);
        qual_adapter.setDropDownViewResource(R.layout.drpdn);
        dogSexET.setAdapter(qual_adapter);
    }


    private void datePickDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(AddDogActivity.this, AddDogActivity.this, year,month,day);
        datePicker.show();
    }


    private File createImageFile()  {
        @SuppressLint("SimpleDateFormat")
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "dogPhoto_" + timeStamp + "_";
        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = null;
        try{
            image = File.createTempFile(
                    imageFileName,
                    ".jpg",
                    storageDir);
        }catch (IOException e){
            Log.d("myLog", "Except : " + e.toString());
        }
        return image;
    }






}
