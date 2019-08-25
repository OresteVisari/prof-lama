package repositories;

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
import models.Note;

public class JsonFileRepository extends Application {

    private static final String FILE_NAME = "notes.json";

    public static void saveNotes(List<Note> notes) {
        Gson gson = new Gson();
        String json = gson.toJson(notes);
        File file = new File(ProfLama.getAppContext().getExternalFilesDir(null), FILE_NAME);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Note> getAllNotes() {
        Gson gson = new Gson();
        ArrayList<Note> notes = new ArrayList();
        File file = new File(ProfLama.getAppContext().getExternalFilesDir(null), FILE_NAME);

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            Type listType = new TypeToken<ArrayList<Note>>(){}.getType();
            notes = gson.fromJson(br, listType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return notes;
    }
}
