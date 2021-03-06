package fr.pbenoit.proflama.utilities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import fr.pbenoit.proflama.models.Note;
import fr.pbenoit.proflama.models.TestStatus;
import fr.pbenoit.proflama.models.TrainingMode;
import fr.pbenoit.proflama.repositories.JsonFileRepository;

public class NotesUtils {

    private static int countNumberOfWordSinceSpecificDate(Calendar date) {
        List<Note> notes = JsonFileRepository.getAllNotes();
        int numberOfWord = 0;

        Calendar currentNoteDateCalendar = Calendar.getInstance();
        for (Note note : notes) {
            currentNoteDateCalendar.setTime(note.getCreationDate());
            if (currentNoteDateCalendar.after(date) || currentNoteDateCalendar.equals(date)) {
                numberOfWord ++;
            }
        }

        return numberOfWord;
    }

    public static int countNumberOfWordThisWeek() {
        final long DAY_IN_MS = 1000 * 60 * 60 * 24;
        Calendar sevenDaysAgo = Calendar.getInstance();
        sevenDaysAgo.setTime(new Date(System.currentTimeMillis() - (7 * DAY_IN_MS)));
        sevenDaysAgo.set(Calendar.HOUR_OF_DAY, 0);
        sevenDaysAgo.set(Calendar.MINUTE, 0);
        sevenDaysAgo.set(Calendar.SECOND, 0);
        sevenDaysAgo.set(Calendar.MILLISECOND, 0);

        return countNumberOfWordSinceSpecificDate(sevenDaysAgo);
    }

    public static List<Note> getUncompletedNote(List<Note> notes) {
        List<Note> uncompletedNotes = new ArrayList<>();
        for (Note note : notes) {
            if (note.getDefinition().isEmpty()) {
                uncompletedNotes.add(note);
            }
        }
        return uncompletedNotes;
    }

    public static List<Note> getCompletedNote(List<Note> notes) {
        List<Note> completedNotes = new ArrayList<>();
        for (Note note : notes) {
            if (!note.getDefinition().isEmpty() && note.getTestStatus() != TestStatus.NOT_APPLICABLE) {
                completedNotes.add(note);
            }
        }
        return completedNotes;
    }

    public static List<Note> getNotesForTraining(List<Note> notes) {
        List<Note> completedNotes = getCompletedNote(notes);

        if (completedNotes.size() < 10) {
            return new ArrayList<>();
        }
        Collections.shuffle(completedNotes);


        List<Note> notesForTraining = new ArrayList<>();
        for (Note note : completedNotes) {
            if (note.getTestStatus() == TestStatus.FAILED) {
                notesForTraining.add(note);
                if (notesForTraining.size() == TrainingMode.NUMBER_OF_WORD_IN_TRAINING) {
                    return notesForTraining;
                }
            }
        }

        for (Note note : completedNotes) {
            if (note.getTestStatus() == TestStatus.UNKNOW) {
                notesForTraining.add(note);
                if (notesForTraining.size() == TrainingMode.NUMBER_OF_WORD_IN_TRAINING) {
                    return notesForTraining;
                }
            }
        }

        for (Note note : completedNotes) {
            if (note.getTestStatus() == TestStatus.SUCCESS) {
                notesForTraining.add(note);
                if (notesForTraining.size() == TrainingMode.NUMBER_OF_WORD_IN_TRAINING) {
                    return notesForTraining;
                }
            }
        }

        return new ArrayList<>();
    }

}
