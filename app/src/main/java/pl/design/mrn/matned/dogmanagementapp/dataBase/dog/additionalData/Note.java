package pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData;

import java.io.Serializable;

public class Note  implements Serializable {

    private int noteId;
    private String note;
    private int dogId;

    public Note() {
    }

    public Note(int noteId) {
        this.noteId = noteId;
    }

    public int getDogId() {
        return dogId;
    }

    public void setDogId(int dogId) {
        this.dogId = dogId;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Note{" +
                "noteId=" + noteId +
                ", note='" + note + '\'' +
                ", dogId=" + dogId +
                '}';
    }
}