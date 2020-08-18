package pl.design.mrn.matned.dogmanagementapp.activity.health;

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
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import pl.design.mrn.matned.dogmanagementapp.ImageAdvancedFunction;
import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.Validate;

import static android.os.Environment.DIRECTORY_PICTURES;
import static android.os.Environment.getExternalStoragePublicDirectory;
import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;
import static pl.design.mrn.matned.dogmanagementapp.Statics.REQUEST_IMAGE_CAPTURE;

public abstract class SuperHealthClass extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    protected abstract void changeDateViews(Calendar cal);
    protected abstract boolean validation();
    protected abstract void initLocalViews();
    protected abstract void initDatePicker();
    protected abstract void onSavedReload(Bundle savedInstanceState);

    protected abstract void setOtherOnClickListeners();
    protected ImageView photoStampIV;
    protected Bitmap bitmap;

    protected String photoPath;
    @SuppressLint("SimpleDateFormat")
    protected DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    protected Uri photoUri;



    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void initSuper() {
        bitmap = null;
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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
        dateFormat.setTimeZone(cal.getTimeZone());
        changeDateViews(cal);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void initPhotoClick() {
        if (this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
            photoStampIV.setOnClickListener(addPhoto());
        }
    }

    protected View.OnClickListener addPhoto() {
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
        };
    }


    protected boolean checkIsNotEmpty(EditText et) {
        if (!Validate.notEmpty(et.getText().toString())) {
            et.setBackgroundResource(R.drawable.roundcornerstextred);
            return false;
        } else {
            et.setBackgroundResource(R.drawable.roundcornerstext);
            return true;
        }
    }

    protected boolean checkIsNumeric(EditText et) {
        if (!Validate.isNumeric(et.getText().toString())) {
            et.setBackgroundResource(R.drawable.roundcornerstextred);
            return false;
        } else {
            et.setBackgroundResource(R.drawable.roundcornerstext);
            return true;
        }
    }

    protected boolean checkDateFormat(EditText et) {
        if (!Validate.dateFormat(et.getText().toString())) {
            et.setBackgroundResource(R.drawable.roundcornerstextred);
            return false;
        } else {
            et.setBackgroundResource(R.drawable.roundcornerstext);
            return true;
        }
    }

    protected void showImage() {
        try {
            bitmap = ImageAdvancedFunction.handleSamplingAndRotationBitmap(this, photoUri);
            Drawable d = new BitmapDrawable(getResources(), bitmap);
            photoStampIV.setImageDrawable(d);
            photoStampIV.setBackgroundResource(R.drawable.roundcornerimageframeblack);

        } catch (IOException e) {
            Log.d("myLog", "Except : " + e.toString());
        }
    }





    protected void datePickDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(this, this, year, month, day);
        datePicker.show();
    }

    protected File createImageFile() {
        @SuppressLint("SimpleDateFormat")
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "dogPhoto_" + timeStamp + "_";
        File storageDir = getExternalStoragePublicDirectory(DIRECTORY_PICTURES);
        File image = null;
        image = new File(storageDir.getParent() + imageFileName + ".jpg");
        return image;
    }


}
