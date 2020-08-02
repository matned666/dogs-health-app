package pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.edit;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.Validate;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.Note;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.NoteDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;

public class NoteActivityEdit extends AppCompatActivity {

    private Button ok;
    private Button cancel;
    private Button delete;
    private TextView noteTV;

    private Note note;
    private NoteDao dao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.note_dialog);
            init();
            fillAllFields();
    }

    private void init() {
        ok = findViewById(R.id.okNoteDialog);
        cancel = findViewById(R.id.cancelNoteDialog);
        delete = findViewById(R.id.removeNote);
        noteTV = findViewById(R.id.noteDescription);
        dao = new NoteDao(this);
        note = dao.findById(DataPositionListener.getInstance().getSelectedItemId());
        initEndingListeners();
    }
    private void fillAllFields() {
        noteTV.setText(note.getNote());
    }


    private void initEndingListeners() {
        ok.setOnClickListener(v -> {
            if(Validate.checkET(noteTV)){
                note.setNote(noteTV.getText().toString());
                dao.update(note);
                noteTV.setBackgroundResource(R.drawable.roundcornerstext);
                finish();
            }else{
                noteTV.setBackgroundResource(R.drawable.roundcornerstextred);
            }
        });
        cancel.setOnClickListener(v -> finish());
        delete.setOnClickListener(v -> {
            dao.remove(note);
            finish();
        });
    }

}
