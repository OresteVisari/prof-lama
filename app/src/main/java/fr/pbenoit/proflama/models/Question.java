package fr.pbenoit.proflama.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Question {

    private boolean isSolved;

    private boolean isFirstTryEver;

    public boolean isFirstTryForThisTurn;

    Note note;

    private List<String> answers;

    public Question(Note note) {
        this.note = note;
        this.answers = new ArrayList<>();
        this.isSolved = false;
        this.isFirstTryEver = true;
        this.isFirstTryForThisTurn = true;
    }

    public Note getNote() {
        return note;
    }

    public void addAnswer(String answer) {
        this.answers.add(answer);
        Collections.shuffle(this.answers);
    }

    public void shuffleAnswers() {
        Collections.shuffle(answers);
    }

    public List<String> getAnswers() {
        return answers;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public void firstTurnDone() {
        this.isFirstTryForThisTurn = false;
        this.isFirstTryEver = false;
    }
    public void changeToSolved() {
        if (isFirstTryEver) {
            this.note.setTestStatus(TestStatus.SUCCESS);
        } else {
            this.note.setTestStatus(TestStatus.FAILED);
        }
        if (isFirstTryForThisTurn) {
            this.isSolved = true;
        }
    }
}
