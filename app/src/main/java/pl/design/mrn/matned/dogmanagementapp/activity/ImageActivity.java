package pl.design.mrn.matned.dogmanagementapp.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.Statics;

import static pl.design.mrn.matned.dogmanagementapp.ImageAdvancedFunction.setImage;

public class ImageActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_image_activity);
        Button back = findViewById(R.id.image_back_btn);
        ImageView image = findViewById(R.id.image_imageView);
        String photoPath = getIntent().getStringExtra(Statics.PHOTO_PATH);
        if (photoPath != null)
            setImage(image, photoPath, this, getResources());
        back.setOnClickListener(v->finish());
    }
}
