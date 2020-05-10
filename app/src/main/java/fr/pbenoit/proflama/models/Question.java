package fr.pbenoit.proflama.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Question {

    public boolean isSolved;

    public boolean isFirstTry;

    Note note;

    private List<String> answers;

    public Question(Note note) {
        this.note = note;
        this.answers = new ArrayList<>();
        this.isSolved = false;
        this.isFirstTry = true;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public void addAnswer(String answer) {
        this.answers.add(answer);
        Collections.shuffle(this.answers);
    }

    public List<String> getAnswers() {
        return answers;
    }
}
