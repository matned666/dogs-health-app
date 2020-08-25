package pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.add;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.Validate;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.Note;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.NoteDao;
import pl.design.mrn.matned.dogmanagementapp.listeners.PositionListener;

public class NoteActivityAdd extends AppCompatActivity {

    private Button ok;
    private Button cancel;
    private TextView noteTV;

    private Note note;
    private NoteDao dao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.note_dialog);
            init();
    }

    private void init() {
        ok = findViewById(R.id.okNoteDialog);
        cancel = findViewById(R.id.cancelNoteDialog);
        Button delete = findViewById(R.id.removeNote);
        delete.setVisibility(View.GONE);
        noteTV = findViewById(R.id.noteDescription);
        dao = new NoteDao(this);
        initEndingListeners();
    }

    private void initEndingListeners() {
        ok.setOnClickListener(v -> {
            if(Validate.checkET(noteTV)){
                note = new Note();
                note.setNote(noteTV.getText().toString());
                note.setDogId(PositionListener.getInstance().getSelectedDogId());
                dao.add(note);
                noteTV.setBackgroundResource(R.drawable.roundcornerstext);
                finish();
            }else{
                noteTV.setBackgroundResource(R.drawable.roundcornerstextred);
            }
        });
        cancel.setOnClickListener(v -> finish());
    }

}
