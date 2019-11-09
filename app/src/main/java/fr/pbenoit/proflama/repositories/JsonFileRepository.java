package fr.pbenoit.proflama.repositories;

import android.app.Application;

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
import fr.pbenoit.proflama.notifications.NotificationPreferences;

public class JsonFileRepository extends Application {

    private static final String NOTES_FILE_NAME = "notes.json";

    private static final String ALARM_PREFERENCE_FILE_NAME = "alarm.json";

    private static void writeFile(String json, String fileName) {
        File file = new File(ProfLama.getAppContext().getExternalFilesDir(null), fileName);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveNotes(List<Note> notes) {
        Gson gson = new Gson();
        String json = gson.toJson(notes);
        writeFile(json, NOTES_FILE_NAME);
    }

    public static void saveNotificationPreferences(NotificationPreferences notificationPreferences) {
        Gson gson = new Gson();
        String json = gson.toJson(notificationPreferences);
        writeFile(json, ALARM_PREFERENCE_FILE_NAME);
    }

    public static List<Note> getAllNotes() {
        Gson gson = new Gson();
        ArrayList<Note> notes = new ArrayList<>();
        File file = new File(ProfLama.getAppContext().getExternalFilesDir(null), NOTES_FILE_NAME);

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            Type listType = new TypeToken<ArrayList<Note>>(){}.getType();
            notes = gson.fromJson(br, listType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return notes;
    }

    public static NotificationPreferences getNotificationPreferences() {
        Gson gson = new Gson();
        NotificationPreferences notificationPreferences = new NotificationPreferences();
        File file = new File(ProfLama.getAppContext().getExternalFilesDir(null), ALARM_PREFERENCE_FILE_NAME);

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            Type listType = new TypeToken<NotificationPreferences>(){}.getType();
            notificationPreferences = gson.fromJson(br, listType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return notificationPreferences;
    }
}
