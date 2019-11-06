package fr.pbenoit.proflama.utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import fr.pbenoit.proflama.models.Note;
import fr.pbenoit.proflama.repositories.JsonFileRepository;

public class Logger {

    private static void createWorkToLog(List<Note> notes) {
        Note noteWithLogs = new Note("logs");

        Calendar dateOfFirstKiss = Calendar.getInstance();
        int decemberMonth = 11;
        dateOfFirstKiss.set(2018, decemberMonth, 7);
        noteWithLogs.setCreationDate(dateOfFirstKiss.getTime());

        notes.add(noteWithLogs);
        Collections.sort(notes);
    }

    public static void add(String log) {
        List<Note> notes = JsonFileRepository.getAllNotes();

        if (notes.isEmpty()) {
            createWorkToLog(notes);
        }

        Note noteWithLogs = notes.get(notes.size() - 1);
        if (! noteWithLogs.getTitle().equals("logs")) {
            createWorkToLog(notes);
        }

        SimpleDateFormat logTimeFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
        String time = logTimeFormat.format(new Date()) + " - ";

        String prefix = "\n";
        if (log.equals("Prof Lama start")) prefix = "\n\n";

        noteWithLogs.setDefinition(noteWithLogs.getDefinition() + prefix + time + log);
        JsonFileRepository.saveNotes(notes);
    }
}
