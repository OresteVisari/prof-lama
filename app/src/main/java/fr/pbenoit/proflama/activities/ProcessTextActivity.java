package fr.pbenoit.proflama.activities;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Collections;
import java.util.List;

import fr.pbenoit.proflama.ProfLama;
import fr.pbenoit.proflama.models.Note;
import fr.pbenoit.proflama.notifications.NotificationManager;
import fr.pbenoit.proflama.repositories.JsonFileRepository;

public class ProcessTextActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CharSequence text = getIntent().getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);
        if (text != null && text.length() > 0) {
            addNewTitle(text.toString());
        }
    }


    private void addNewTitle(final String title) {
        final Activity activity = this;

        List<Note> notes = JsonFileRepository.getAllNotes();
        notes.add(new Note(title));
        Collections.sort(notes);
        JsonFileRepository.saveNotes(notes);

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(activity, 0, intent, 0);
        NotificationManager.sendWorkCreationNotification(pendingIntent, title);

        this.finish();
    }

}
