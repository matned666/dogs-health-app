package pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.info;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.edit.NoteActivityEdit;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.Note;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.NoteDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;

import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;

public class NoteActivityInfo extends AppCompatActivity {

    private Button ok;
    private Button edit;
    private TextView noteTV;
    private TextView noteDateTV;

    private NoteDao dao;
    private Note note;

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.note_info_dialog);
            init();
            fillAllFields();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101) {
            if (resultCode == Activity.RESULT_OK) {
                finish();
            }
        }
    }

    private void init() {
        initViews();
    }

    private void initViews() {
        ok = findViewById(R.id.okNoteDialogInfo);
        edit = findViewById(R.id.editNoteDialogInfo);
        noteTV = findViewById(R.id.noteDescriptionInfoTextView);
        noteDateTV = findViewById(R.id.noteDate_noteInfo);
        dao = new NoteDao(this);
        note = dao.findById(DataPositionListener.getInstance().getSelectedItemId());
        initEndingListeners();
    }
    private void fillAllFields() {
        noteTV.setText(note.getNote());
        noteDateTV.setText(dateFormat.format(note.getDate()));
    }


    private void initEndingListeners() {
        ok.setOnClickListener(v -> finish());
        edit.setOnClickListener(v -> {
            Intent intent = new Intent(this, NoteActivityEdit.class);
            startActivity(intent);
        });
    }


}
