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
import pl.design.mrn.matned.dogmanagementapp.PositionListener;
import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.activity.addActivity.BreedingActivity;
import pl.design.mrn.matned.dogmanagementapp.activity.addActivity.ChipActivity;
import pl.design.mrn.matned.dogmanagementapp.activity.addActivity.NoteActivity;
import pl.design.mrn.matned.dogmanagementapp.activity.addActivity.OwnerActivity;
import pl.design.mrn.matned.dogmanagementapp.activity.addActivity.TattooActivity;
import pl.design.mrn.matned.dogmanagementapp.activity.addActivity.UniqueSignActivity;
import pl.design.mrn.matned.dogmanagementapp.dataBase.Sex;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.DogDao;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.DogModel;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.Validate;

import static android.os.Environment.DIRECTORY_PICTURES;
import static android.os.Environment.getExternalStoragePublicDirectory;
import static pl.design.mrn.matned.dogmanagementapp.ImageAdvancedFunction.setImage;
import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;
import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE;
import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE_ADD;
import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE_EDIT;

public class AddEdit_DogActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

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
    private Button cancel;
    private ImageView dogPhoto;

    private Button deleteDogBtn;

    private Bitmap bitmap;

    private String photoPath;
    private String dogName;
    private String dogRace;
    private String dogBirthDatePicker;
    private String dogColor;

    private String usage;

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    private Uri photoUri;

    private DogModel dog;
    private DogDao dogDao;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        usage = intent.getStringExtra(USAGE);
        onSvedInstanceReload_usage(savedInstanceState);
        assert usage != null;
        if(usage.equals(USAGE_ADD)) {
            setContentView(R.layout.add_dog);
            initializeFields_add();
        }else if(usage.equals(USAGE_EDIT)) {
            setContentView(R.layout.edit_dog);
            dogDao = new DogDao(this);
            dog = dogDao.findById(PositionListener.getInstance().getSelectedDogId());
            initializeFields_edit();
        }
        onSavedReload(savedInstanceState);
        bitmap = null;
    }

    private void onSvedInstanceReload_usage(Bundle savedInstanceState) {
        if (savedInstanceState != null){
            usage = savedInstanceState.getString(USAGE);
        }

        }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initializeFields_edit() {
        dogNameET = findViewById(R.id.dogNameEdit);
        dogRaceET = findViewById(R.id.dogRaceEdit);
        dogBirthDatePickerET = findViewById(R.id.dogDateOfBirthEdit);
        dogColorET = findViewById(R.id.dogColorEdit);
        dogSexET = findViewById(R.id.dogSexEdit);
        chipBtn = findViewById(R.id.chipBtnEdit);
        tattooBtn = findViewById(R.id.tattooBtnEdit);
        signsBtn = findViewById(R.id.signsBtnEdit);
        ownerBtn = findViewById(R.id.ownerBtnEdit);
        notesBtn = findViewById(R.id.notesBtnEdit);
        breedingBtn = findViewById(R.id.breedingBtnEdit);
        saveDog = findViewById(R.id.saveDogEdit);
        deleteDogBtn = findViewById(R.id.deleteDogEdit);
        cancel = findViewById(R.id.cancelButtonEdit);
        dogPhoto = findViewById(R.id.photoEdit);
        initSpinner();
        initOnClickListeners_forButtons_edit();
        fillAllFields_edit();
        initDatePicker();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initializeFields_add() {
        dogPhoto = findViewById(R.id.photoAdd);
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
        cancel = findViewById(R.id.cancelAdd);
        initSpinner();
        initDatePicker();
        initClickListeners_add();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initClickListeners_add() {
        breedingBtn.setOnClickListener(c->showDial(BreedingActivity.class));
        chipBtn.setOnClickListener(c->showDial(ChipActivity.class));
        notesBtn.setOnClickListener(c->showDial(NoteActivity.class));
        tattooBtn.setOnClickListener(c->showDial(TattooActivity.class));
        ownerBtn.setOnClickListener(c->showDial(OwnerActivity.class));
        signsBtn.setOnClickListener(c->showDial(UniqueSignActivity.class));
        cancel.setOnClickListener(c->showDial(StartActivity.class));
        if(this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY))
        {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 2);
            dogPhoto.setOnClickListener(addPhoto());
        }
        saveDog.setOnClickListener(saveDog());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void fillAllFields_edit() {
        dogNameET.setText(dog.getName());
        dogRaceET.setText(dog.getRace());
        dogBirthDatePickerET.setText(dateFormat.format(dog.getBirthDate()));
        dogColorET.setText(dog.getColor());
        if (dog.getDogImage() != null)
            setImage(dogPhoto, dog.getDogImage(), this, getResources());
        else {
            Drawable drawable;
            drawable = getResources().getDrawable(R.drawable.ic_stat_name);
            dogPhoto.setImageDrawable(drawable);
        }
        dogSexET.setSelection(Sex.getPosition(dog.getSex()));
        if(this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY))
        {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 2);
            dogPhoto.setOnClickListener(addPhoto());
        }
    }

    private void initOnClickListeners_forButtons_edit() {
            chipBtn.setOnClickListener(changeActivityOnClick_dataEdit(ChipActivity.class));
            tattooBtn.setOnClickListener(changeActivityOnClick_dataEdit(TattooActivity.class));
            signsBtn.setOnClickListener(changeActivityOnClick_dataEdit(UniqueSignActivity.class));
            ownerBtn.setOnClickListener(changeActivityOnClick_dataEdit(OwnerActivity.class));
            notesBtn.setOnClickListener(changeActivityOnClick_dataEdit(NoteActivity.class));
            breedingBtn.setOnClickListener(changeActivityOnClick_dataEdit(BreedingActivity.class));
            saveDog.setOnClickListener(changeActivityOnClick_saveEdit());
            deleteDogBtn.setOnClickListener(changeActivityOnClick_deleteEdit());
            cancel.setOnClickListener(changeActivityOnClick_dataEdit(DogDataActivity.class));
    }

    private View.OnClickListener changeActivityOnClick_dataEdit(Class activity) {
        return v -> {
            Intent intent = new Intent(AddEdit_DogActivity.this, activity);
            intent.putExtra(USAGE, USAGE_EDIT);
            startActivity(intent);
        };
    }

    private View.OnClickListener changeActivityOnClick_deleteEdit() {
        return v -> {
            dogDao.remove(dog);
            Intent intent = new Intent(AddEdit_DogActivity.this, StartActivity.class);
            startActivity(intent);
        };
    }

    private View.OnClickListener changeActivityOnClick_saveEdit() {
        return v -> {
            if (validation()) {
                dog.setTo(getDogModelFromFields());
                dogDao.update(PositionListener.getInstance().getSelectedDogId(), dog);
                Intent intent = new Intent(AddEdit_DogActivity.this, DogDataActivity.class);
                startActivity(intent);
            }
        };
    }

    private void onSavedReload(Bundle savedInstanceState) {
        if (savedInstanceState != null){
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
                photoUri = FileProvider.getUriForFile(AddEdit_DogActivity.this, "pl.design.mrn.matned.dogmanagementapp.fileprovider", new File(photoPath));
                showImage();
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("NAME",  dogName);
        outState.putString("RACE",  dogRace);
        outState.putString("BIRTH_DATE",  dogBirthDatePicker);
        outState.putString("COLOR",  dogColor);
        outState.putInt("SEX",  dogSexET.getSelectedItemPosition());
        outState.putString("PHOTO_PATH", photoPath);
        outState.putString(USAGE, usage);
        super.onSaveInstanceState(outState);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
        dateFormat.setTimeZone(cal.getTimeZone());
        dogBirthDatePickerET.setText(dateFormat.format(cal.getTime()));
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

    public void showDial(Class clazz) {
        Intent intent = new Intent(AddEdit_DogActivity.this, clazz);
        intent.putExtra(USAGE, USAGE_ADD);
        startActivity(intent);
    }


    private void showImage() {
        try {
            bitmap = ImageAdvancedFunction.handleSamplingAndRotationBitmap(AddEdit_DogActivity.this, photoUri);
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
                    photoUri = FileProvider.getUriForFile(AddEdit_DogActivity.this, "pl.design.mrn.matned.dogmanagementapp.fileprovider", photoFile);
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
                DogModel dogToSave = getDogModelFromFields();
                DogDao dao = new DogDao(this);
                dogToSave.setDogImage(photoPath);
                dao.add(dogToSave);
                Intent intent = new Intent(AddEdit_DogActivity.this, StartActivity.class);
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
            dogBirthDate = new Date();
            Toast.makeText(AddEdit_DogActivity.this, "Wrong date", Toast.LENGTH_SHORT).show();
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
        System.out.println(">>>>>>>>>>>>>>>>>>   SETTING UP SPINNER");
        String[] sexes = new String[3];
        sexes[0] = "";
        sexes[1] = Sex.PIES.name();
        sexes[2] = Sex.SUKA.name();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.drpdn, sexes);
        dogSexET.setAdapter(adapter);
    }

    private void datePickDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(AddEdit_DogActivity.this, AddEdit_DogActivity.this, year,month,day);
        datePicker.show();
    }


    private File createImageFile()  {
        @SuppressLint("SimpleDateFormat")
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "dogPhoto_" + timeStamp + "_";
        File storageDir = getExternalStoragePublicDirectory(DIRECTORY_PICTURES);
        File image = null;
        image = new File(storageDir.getParent()+imageFileName+".jpg");
        return image;
    }

}
