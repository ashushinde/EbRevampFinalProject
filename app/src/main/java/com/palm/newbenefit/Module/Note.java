package com.palm.newbenefit.Module;

public class Note {

    String notes;

    @Override
    public String toString() {
        return "Note{" +
                "notes='" + notes + '\'' +
                '}';
    }

    public Note() {
    }

    public Note(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
