package fr.pbenoit.proflama.repositories;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import fr.pbenoit.proflama.ProfLama;
import fr.pbenoit.proflama.models.Note;
import fr.pbenoit.proflama.models.Question;
import fr.pbenoit.proflama.notifications.NotificationScheduler;

public class JsonFileRepository   {

    private static final String NOTES_FILE_NAME = "notes.json";

    private static final String ALARM_PREFERENCE_FILE_NAME = "alarm.json";

    private static void writeFile(String json, String fileName) {
        File file = new File(ProfLama.getAppContext().getExternalFilesDir(null), fileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveNotes(List<Note> notes) {
        Gson gson = new Gson();
        String json = gson.toJson(notes);
        writeFile(json, NOTES_FILE_NAME);
    }

    public static void saveQuizResult(List<Note> notes, List<Question> questions) {
        for (Question question : questions) {
            for (Note note : notes) {
                if (question.getNote().getTitle().equals(note.getTitle())) {
                    note.setTestStatus(question.getNote().getTestStatus());
                    break;
                }
            }
        }
        saveNotes(notes);
    }

    public static void saveNotificationPreferences(NotificationScheduler notificationScheduler) {
        Gson gson = new Gson();
        String json = gson.toJson(notificationScheduler);
        writeFile(json, ALARM_PREFERENCE_FILE_NAME);
    }

    public static List<Note> getAllNotes() {
        Gson gson = new Gson();
        ArrayList<Note> notes = new ArrayList<>();
        File file = new File(ProfLama.getAppContext().getExternalFilesDir(null), NOTES_FILE_NAME);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            Type listType = new TypeToken<ArrayList<Note>>(){}.getType();
            notes = gson.fromJson(br, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return formatAllNotes(notes);
    }

    private static List<Note> formatAllNotes(ArrayList<Note> notes) {
        boolean foundInvalidNote = false;
        ArrayList<Note> notesFormated = new ArrayList<>();

        for (Note note : notes) {
            if (note.getTitle() == null) {
                foundInvalidNote = true;
                continue;
            }
            if (note.getDefinition() == null || note.getQuote() == null || note.getCreationDate() == null || note.getTestStatus() == null) {
                foundInvalidNote = true;
                notesFormated.add(new Note(note.getTitle(), note.getDefinition(), note.getQuote(), note.getCreationDate(), note.getTestStatus()));
            } else {
                notesFormated.add(note);
            }
        }

        if (foundInvalidNote) {
            saveNotes(notesFormated);
        }
        return notesFormated;
    }

    public static NotificationScheduler getNotificationPreferences() {
        Gson gson = new Gson();
        NotificationScheduler notificationScheduler = new NotificationScheduler();
        File file = new File(ProfLama.getAppContext().getExternalFilesDir(null), ALARM_PREFERENCE_FILE_NAME);

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            Type listType = new TypeToken<NotificationScheduler>(){}.getType();
            notificationScheduler = gson.fromJson(br, listType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return notificationScheduler;
    }
}
