package models;

import java.util.Date;

public class Note implements  Comparable<Note> {

    private boolean shouldDisplayAllFields = false;

    private String title;

    private String definition;

    private String quote;

    public Date creationDate;

    public String getTitle() {
        return title;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public boolean isShouldDisplayAllFields() {
        return shouldDisplayAllFields;
    }

    public void setShouldDisplayAllFields(boolean shouldDisplayAllFields) {
        this.shouldDisplayAllFields = shouldDisplayAllFields;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public  Note(String title) {
        this.title = title;
        this.creationDate = new Date(System.currentTimeMillis());
    }

    @Override
    public int compareTo(Note note) {
        return note.getCreationDate().compareTo(this.creationDate);
    }


}

