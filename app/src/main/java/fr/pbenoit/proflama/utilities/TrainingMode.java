package fr.pbenoit.proflama.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.pbenoit.proflama.models.Note;
import fr.pbenoit.proflama.models.Question;
import fr.pbenoit.proflama.utilities.NotesUtils;

public class TrainingMode {

    private int questionIndex;

    List<Question> questions;

    public TrainingMode(List<Note> notesForTraining) {
        this.questions = new ArrayList<>();
        this.questionIndex = 0;
        List<Note> notesCompleted = NotesUtils.getCompletedNote();
        for (Note note : notesForTraining) {
            questions.add(buildQuestion(notesCompleted, note));
        }
    }

    public Question getNextQuestion() {
        this.questionIndex++;
        if (questionIndex == 5) {
            questionIndex = 0;
        }
        if (!this.questions.get(questionIndex).isSolved) {
            return this.questions.get(questionIndex);
        }
        return getNextQuestion();
    }

    private Question buildQuestion(List<Note> allNotesCompleted, Note note) {
        Question question = new Question(note);
        ArrayList<Note> currentNotes = new ArrayList<>();
        currentNotes.addAll(allNotesCompleted);

        int indexCurrentNote = 0;
        for (int i=0; i < currentNotes.size(); i++) {
            if (currentNotes.get(i).getTitle().equals(note.getTitle())) {
                indexCurrentNote = i;
                break;
            }
         }
        currentNotes.remove(indexCurrentNote);
        question.addAnswer(note.getDefinition());

        Random rand = new Random();

        int randomIndex = rand.nextInt(currentNotes.size());
        Note randomNote = currentNotes.get(randomIndex);
        question.addAnswer(randomNote.getDefinition());
        currentNotes.remove(randomIndex);

        randomIndex = rand.nextInt(currentNotes.size());
        randomNote = currentNotes.get(randomIndex);
        question.addAnswer(randomNote.getDefinition());

        return question;
    }

    public boolean isValidAnswer(String answer) {
        if (this.questions.get(questionIndex).getNote().getDefinition().equals(answer)) {
            this.questions.get(questionIndex).isSolved = true;
            return true;
        }
        return false;
    }

    public boolean isQuizFinish() {
        for (Question question : questions) {
            if (!question.isSolved) {
                return false;
            }
        }
        return true;
    }
}
