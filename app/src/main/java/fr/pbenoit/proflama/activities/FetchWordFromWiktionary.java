package fr.pbenoit.proflama.activities;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import fr.pbenoit.proflama.ProfLama;
import fr.pbenoit.proflama.models.Note;
import fr.pbenoit.proflama.notifications.LocalNotifications;
import fr.pbenoit.proflama.repositories.JsonFileRepository;

public class FetchWordFromWiktionary extends AsyncTask<String, Void, Void> {

    Activity activity;

    public FetchWordFromWiktionary(Activity activity) {
      this.activity = activity;
    }

    @Override
    protected Void doInBackground(String... params) {
        Note newNote = new Note(params[0], "", "");

        try {
            Document doc = Jsoup.connect("https://en.wiktionary.org/wiki/" + newNote.getTitle()).get();
            Elements e = doc.select("ol").first().getElementsByTag("li");

            String fullWord = e.get(0).wholeText();
            String[] lines = fullWord.split("\\r?\\n");
            newNote.setDefinition(lines[0]);
            newNote.setQuote(lines[1]);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            List<Note> notes = JsonFileRepository.getAllNotes();
            notes.add(newNote);
            Collections.sort(notes);
            JsonFileRepository.saveNotes(notes);

            Intent intent = new Intent(ProfLama.getAppContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this.activity, 0, intent, 0);
            LocalNotifications.sentNotification(pendingIntent, newNote.getTitle(), newNote.getDefinition());
        }
        return null;
    }


}
