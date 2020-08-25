package pl.design.mrn.matned.dogmanagementapp.activity.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.add.ChipActivityAdd;
import pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.add.NoteActivityAdd;
import pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.edit.ChipActivityEdit;
import pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.edit.NoteActivityEdit;
import pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.info.ChipActivityInfo;
import pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.info.NoteActivityInfo;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.Note;
import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;

import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;
import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE_ADD;
import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE_EDIT;


public class NoteDataElementAdapter extends RecyclerView.Adapter<NoteDataElementAdapter.ViewHolder>  {

    private List<Note> notes;
    private DataPositionListener dataPositionListener;
    private int selectedPosition;
    private String usage;
    private Context context;

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    public NoteDataElementAdapter(List<Note> notes, String usage, Context context) {
        this.context = context;
        this.usage = usage;
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
            if (usage.equals(USAGE_EDIT) || usage.equals(USAGE_ADD)){
                Intent intent = new Intent(context, NoteActivityEdit.class);
                context.startActivity(intent);
            } else {
                Intent intent = new Intent(context, NoteActivityInfo.class);
                context.startActivity(intent);
            }
        });
    }

    private String generateNoteHeader(String note) {
        if(note.length() > 25) return note.substring(0, 24).trim() + "...";
        else return note;    }


    @Override
    public int getItemCount() {
        if(notes == null) return 0;
        else return notes.size();
    }




    public static class ViewHolder extends RecyclerView.ViewHolder{

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

