package fr.pbenoit.proflama.models;

import android.text.format.DateUtils;

import java.util.Date;

import fr.pbenoit.proflama.ProfLama;

public class Note implements  Comparable<Note> {

    private transient boolean shouldDisplayAllFields = false;

    private String title;

    private String definition;

    private String quote;

    private Date creationDate;

    private TestStatus testStatus;

    public Note(String title) {
        this.title = title;
        this.definition = "";
        this.quote = "";
        this.creationDate = new Date(System.currentTimeMillis());
        this.testStatus = TestStatus.UNKNOW;
    }

    public Note(String title, String definition, String quote) {
        this(title);
        this.setDefinition(definition);
        this.setQuote(quote);
    }

    public Note(String title, String definition, String quote, Date creationDate, TestStatus testStatus) {
        this(title, definition, quote);
        this.setCreationDate(creationDate);
        this.setTestStatus(testStatus);
    }

    @Override
    public int compareTo(Note note) {
        return note.getCreationDate().compareTo(this.creationDate);
    }

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
        if (definition == null) {
            definition = "";
        }
        this.definition = definition;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        if (quote == null) {
            quote = "";
        }
        this.quote = quote;
    }

    public boolean isShouldDisplayAllFields() {
        return shouldDisplayAllFields;
    }

    public void toggleShouldDisplayAllFields() {
        this.shouldDisplayAllFields = !this.shouldDisplayAllFields;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date date) {
        if (date == null) {
            date = new Date(System.currentTimeMillis());
        }
        this.creationDate = date;
    }

    public String getFormattedCreationDate() {
        int flags = DateUtils.FORMAT_ABBREV_MONTH | DateUtils.FORMAT_NO_YEAR;
        return DateUtils.formatDateTime(ProfLama.getAppContext(), this.getCreationDate().getTime(), flags);
    }

    public TestStatus getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(TestStatus testStatus) {
        if (testStatus == null) {
            testStatus = TestStatus.UNKNOW;
        }
        this.testStatus = testStatus;
    }
}

