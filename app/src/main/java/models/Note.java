package models;

import java.util.Date;

public class Note implements  Comparable<Note>{

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

    public Date getCreationDate() {
        return this.creationDate;
    }

    public  Note(String title) {
        this.title = title;
        this.creationDate = new Date(System.currentTimeMillis());
    }

    public Note(String title, String definition, String quote) {
        this(title);
        this.definition = definition;
        this.quote = quote;
    }

    @Override
    public int compareTo(Note note) {
        return note.getCreationDate().compareTo(this.creationDate);
    }
}

