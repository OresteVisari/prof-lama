package fr.pbenoit.proflama.models;

class Question {

    Note note;

    String fakeAnswer1;

    String fakeAnswer2;

    public Question(Note note) {
        this.note = note;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public String getFakeAnswer1() {
        return fakeAnswer1;
    }

    public void setFakeAnswer1(String fakeAnswer1) {
        this.fakeAnswer1 = fakeAnswer1;
    }

    public String getFakeAnswer2() {
        return fakeAnswer2;
    }

    public void setFakeAnswer2(String fakeAnswer2) {
        this.fakeAnswer2 = fakeAnswer2;
    }


}
