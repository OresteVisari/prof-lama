package fr.pbenoit.proflama.utilities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.pbenoit.proflama.models.Note;
import fr.pbenoit.proflama.models.TestStatus;
import fr.pbenoit.proflama.repositories.JsonFileRepository;

public class NotesUtils {

    private  static final long DAY_IN_MS = 1000 * 60 * 60 * 24;

    private static final int NUMBER_OF_WORD_IN_TRAINING = 5;

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

    public static int countNumberOfWordAddToday() {
        Calendar morning = Calendar.getInstance();
        morning.set(Calendar.HOUR_OF_DAY, 0);
        morning.set(Calendar.MINUTE, 0);
        morning.set(Calendar.SECOND, 0);
        morning.set(Calendar.MILLISECOND, 0);

        return countNumberOfWordSinceSpecificDate(morning);
    }

    public static int countNumberOfWordThisWeek() {
        Calendar sevenDaysAgo = Calendar.getInstance();
        sevenDaysAgo.setTime(new Date(System.currentTimeMillis() - (7 * DAY_IN_MS)));
        sevenDaysAgo.set(Calendar.HOUR_OF_DAY, 0);
        sevenDaysAgo.set(Calendar.MINUTE, 0);
        sevenDaysAgo.set(Calendar.SECOND, 0);
        sevenDaysAgo.set(Calendar.MILLISECOND, 0);

        return countNumberOfWordSinceSpecificDate(sevenDaysAgo);
    }

    public static List<Note> getUncompleteNote() {
        List<Note> allNotes = JsonFileRepository.getAllNotes();
        List<Note> uncompletedNotes = new ArrayList<>();

        for (Note note : allNotes) {
            if (note.getDefinition().isEmpty()) {
                uncompletedNotes.add(note);
            }
        }

        return uncompletedNotes;
    }

    public static List<Note> getCompletedNote() {
        List<Note> allNotes = JsonFileRepository.getAllNotes();
        List<Note> completedNotes = new ArrayList<>();

        for (Note note : allNotes) {
            if (!note.getDefinition().isEmpty() && note.getTestStatus() != TestStatus.NOT_APPLICABLE) {
                completedNotes.add(note);
            }
        }

        return completedNotes;
    }

    public static List<Note> getNotesForTraining() {
        List<Note> completedNotes = getCompletedNote();

        if (completedNotes.size() < 10) {
            return new ArrayList<>();
        }

        List<Note> notesForTraining = new ArrayList<>();
        for (Note note : completedNotes) {
            if (note.getTestStatus() == TestStatus.FAILED) {
                notesForTraining.add(note);
                if (notesForTraining.size() == NUMBER_OF_WORD_IN_TRAINING) {
                    return notesForTraining;
                }
            }
        }

        for (Note note : completedNotes) {
            if (note.getTestStatus() == TestStatus.UNKNOW) {
                notesForTraining.add(note);
                if (notesForTraining.size() == NUMBER_OF_WORD_IN_TRAINING) {
                    return notesForTraining;
                }
            }
        }

        for (Note note : completedNotes) {
            if (note.getTestStatus() == TestStatus.SUCCESS) {
                notesForTraining.add(note);
                if (notesForTraining.size() == NUMBER_OF_WORD_IN_TRAINING) {
                    return notesForTraining;
                }
            }
        }

        return new ArrayList<>();
    }

}
