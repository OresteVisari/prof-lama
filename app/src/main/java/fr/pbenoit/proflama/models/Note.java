package fr.pbenoit.proflama.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Note implements  Comparable<Note> {

    private transient boolean shouldDisplayAllFields = false;

    private String title;

    private String definition;

    private String quote;

    public Date creationDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public void setCreationDate(Date date) { this.creationDate = date;}

    public String getFormattedCreationDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(this.getCreationDate());
    }

    public Note(String title) {
        this.title = title;
        this.definition = "";
        this.quote = "";
        this.creationDate = new Date(System.currentTimeMillis());
    }

    public Note(String title, String definition, String quote) {
        this(title);
        this.setDefinition(definition);
        this.setQuote(quote);
    }

    @Override
    public int compareTo(Note note) {
        return note.getCreationDate().compareTo(this.creationDate);
    }


}

