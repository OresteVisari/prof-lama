package fr.pbenoit.proflama.activities;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;

import fr.pbenoit.proflama.ProfLama;
import fr.pbenoit.proflama.R;
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
            String titleEncode = newNote.getTitle().toLowerCase().replace(" ", "_");
            String url = "https://" + language + ".wiktionary.org/wiki/" + titleEncode;
            Document doc = Jsoup.connect(url).get();
            newNote = parseWiktionary(doc, newNote);
//            if ("pl".equals(language)) {
//                newNote = parsePolishWiktionary(doc, newNote);
//            } else {
//                newNote = parseWiktionary(doc, newNote);
//            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            List<Note> notes = JsonFileRepository.getAllNotes();
            notes.add(newNote);
            Collections.sort(notes);
            JsonFileRepository.saveNotes(notes);
            final String definition = (newNote.getDefinition().isEmpty()) ? "Prof Lama doesn't know this word yet." : newNote.getDefinition();
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(activity.getApplicationContext(), definition, Toast.LENGTH_LONG).show();
                }
            });
        }
        return null;
    }

    private Note parseWiktionary(Document doc, Note note) {
        Elements e = doc.select("ol").first().getElementsByTag("li");
        String fullWord = e.get(0).wholeText();
        System.out.println(fullWord);
        String[] lines = fullWord.split("\\r?\\n");
        if (lines.length > 0) {
            note.setDefinition(lines[0]);
        }
        if (lines.length > 1) {
            note.setQuote(lines[1]);
        }
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
