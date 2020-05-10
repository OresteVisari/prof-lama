package fr.pbenoit.proflama.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.pbenoit.proflama.utilities.NotesUtils;

public class TrainingMode {

    List<Question> questions;

    public TrainingMode(List<Note> notesForTraining) {
        this.questions = new ArrayList<>();
        List<Note> notesCompleted = NotesUtils.getCompletedNote();
        for (Note note : notesForTraining) {
            questions.add(buildQuestion(notesCompleted, note));
        }
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

        Random rand = new Random();

        int randomIndex = rand.nextInt(currentNotes.size());
        Note randomNote = currentNotes.get(randomIndex);
        question.setFakeAnswer1(randomNote.getDefinition());
        currentNotes.remove(randomIndex);

        randomIndex = rand.nextInt(currentNotes.size());
        randomNote = currentNotes.get(randomIndex);
        question.setFakeAnswer2(randomNote.getDefinition());

        return question;
    }
}
