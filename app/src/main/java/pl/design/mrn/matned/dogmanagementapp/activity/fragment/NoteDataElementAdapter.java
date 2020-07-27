package pl.design.mrn.matned.dogmanagementapp.activity.fragment;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.Note;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.Owner;
import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;

import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;


public class NoteDataElementAdapter extends RecyclerView.Adapter<NoteDataElementAdapter.ViewHolder>  {

    private List<Note> notes;
    private DataPositionListener dataPositionListener;
    private int selectedPosition;

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    public NoteDataElementAdapter(List<Note> notes) {
        this.notes = notes;
        this.dataPositionListener = DataPositionListener.getInstance();
        this.selectedPosition = dataPositionListener.getPosition();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_note_info, null);
        return new ViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.note.setText(generateNoteHeader(note.getNote()));
        holder.dateOfNote.setText(dateFormat.format(note.getDate()));
        if(selectedPosition == position) holder.holderButton.setBackgroundResource(R.drawable.roundcornersrecyclerviewelementselected);
        else holder.holderButton.setBackgroundResource(R.drawable.roundcornersrecyclerviewelement);

        holder.holderButton.setOnClickListener(v -> {
            selectedPosition = position;
            dataPositionListener.setPosition(position);
            dataPositionListener.setSelectedItemId(note.getNoteId());
            notifyDataSetChanged();
        });
    }

    private String generateNoteHeader(String note) {
        return note.substring(0,25).trim() + "...";
    }


    @Override
    public int getItemCount() {
        if(notes == null) return 0;
        else return notes.size();
    }




    static class ViewHolder extends RecyclerView.ViewHolder{

        private ConstraintLayout holderButton;
        private TextView note;
        private TextView dateOfNote;



        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.holderButton = itemView.findViewById(R.id.noteItemButton);
            this.note = itemView.findViewById(R.id.noteItem_note);
            this.dateOfNote = itemView.findViewById(R.id.noteItem_dateOfNote);

        }


    }

}

