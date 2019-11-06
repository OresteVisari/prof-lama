package fr.pbenoit.proflama.activities;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import fr.pbenoit.proflama.ProfLama;
import fr.pbenoit.proflama.models.Note;
import fr.pbenoit.proflama.notifications.NotificationManager;
import fr.pbenoit.proflama.repositories.JsonFileRepository;

public class FetchWordFromWiktionary extends AsyncTask<String, Void, Void> {

    Activity activity;

    public FetchWordFromWiktionary(Activity activity) {
      this.activity = activity;
    }

    @Override
    protected Void doInBackground(String... params) {
        String language = params[0];
        Note newNote = new Note(params[1], "", "");

        try {
            Document doc = Jsoup.connect("https://" + language + ".wiktionary.org/wiki/" + newNote.getTitle()).get();
            if ("pl".equals(language)) {
                newNote = parsePolishWiktionary(doc, newNote);
            } else {
                newNote = parseWiktionary(doc, newNote);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (language != "en" && newNote.getDefinition().length() > 0) {
                return null;
            }
            List<Note> notes = JsonFileRepository.getAllNotes();
            notes.add(newNote);
            Collections.sort(notes);
            JsonFileRepository.saveNotes(notes);

            Intent intent = new Intent(ProfLama.getAppContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this.activity, 0, intent, 0);
            NotificationManager.sendWorkCreationNotification(pendingIntent, newNote.getTitle());
        }
        return null;
    }

    private Note parseWiktionary(Document doc, Note note) {
        Elements e = doc.select("ol").first().getElementsByTag("li");
        String fullWord = e.get(0).wholeText();

        String[] lines = fullWord.split("\\r?\\n");
        note.setDefinition(lines[0]);
        note.setQuote(lines[1]);
        return note;
    }


    private Note parsePolishWiktionary(Document doc, Note note) {
        Element e = doc.select("p").first().nextElementSibling();
        String fullWord = e.wholeText();

        String[] lines = fullWord.split("\\r?\\n");
        note.setDefinition(lines[0]);
        return note;
    }


}
