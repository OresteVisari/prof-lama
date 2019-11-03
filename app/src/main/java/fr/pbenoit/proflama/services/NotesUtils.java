package fr.pbenoit.proflama.services;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fr.pbenoit.proflama.models.Note;
import fr.pbenoit.proflama.repositories.JsonFileRepository;

public class NotesUtils {

    private  static final long DAY_IN_MS = 1000 * 60 * 60 * 24;


    /***
     * This method is here to help the NotificationSchedule debugging
     */
    public static void logJobScheduleTime(int index, Date date) {
        String log = (index == 0) ? "daily " : "weekly ";
        addLog(log + " job schedule " + new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH).format(date));
    }

    private static void createWorkToLog(List<Note> notes) {
        Note noteWithLogs = new Note("logs");

        Calendar dateOfFirstKiss = Calendar.getInstance();
        int decemberMonth = 11;
        dateOfFirstKiss.set(2018, decemberMonth, 7);
        noteWithLogs.setCreationDate(dateOfFirstKiss.getTime());

        notes.add(noteWithLogs);
        Collections.sort(notes);
    }

    public static void addLog(String log) {
        List<Note> notes = JsonFileRepository.getAllNotes();

        if (notes.isEmpty()) {
            createWorkToLog(notes);
        }

        Note noteWithLogs = notes.get(notes.size() - 1);
        if (! noteWithLogs.getTitle().equals("logs")) {
            createWorkToLog(notes);
        }

        noteWithLogs.setDefinition(noteWithLogs.getDefinition() + "\n" + log);
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
        Calendar sevenDaysAgo = Calendar.getInstance();
        sevenDaysAgo.setTime(new Date(System.currentTimeMillis() - (7 * DAY_IN_MS)));
        sevenDaysAgo.set(Calendar.HOUR_OF_DAY, 0);
        sevenDaysAgo.set(Calendar.MINUTE, 0);
        sevenDaysAgo.set(Calendar.SECOND, 0);
        sevenDaysAgo.set(Calendar.MILLISECOND, 0);

        return countNumberOfWordSinceSpecificDate(sevenDaysAgo);
    }
}
