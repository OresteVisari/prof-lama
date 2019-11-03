package fr.pbenoit.proflama.services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fr.pbenoit.proflama.models.Note;
import fr.pbenoit.proflama.repositories.JsonFileRepository;

public class NotesUtils {

    /***
     * This method is here to help the NotificationSchedule debugging
     */
    public static void addTimeInNoteQuote(int index, Date date) {
        List<Note> notes = JsonFileRepository.getAllNotes();
        Note note = notes.get(index);
        String type = (index == 0) ? "daily" : "weekly";
        String time = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH).format(date);
        note.setQuote(note.getQuote() + "\n" + type + " " + time);
        JsonFileRepository.saveNotes(notes);
    }

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
        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        Calendar sevenDaysAgo = Calendar.getInstance();
        sevenDaysAgo.setTime(new Date(System.currentTimeMillis() - (7 * DAY_IN_MS)));
        sevenDaysAgo.set(Calendar.HOUR_OF_DAY, 0);
        sevenDaysAgo.set(Calendar.MINUTE, 0);
        sevenDaysAgo.set(Calendar.SECOND, 0);
        sevenDaysAgo.set(Calendar.MILLISECOND, 0);

        return countNumberOfWordSinceSpecificDate(sevenDaysAgo);
    }
}
