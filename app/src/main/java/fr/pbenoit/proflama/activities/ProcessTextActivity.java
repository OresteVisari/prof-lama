package fr.pbenoit.proflama.activities;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Collections;
import java.util.List;

import fr.pbenoit.proflama.models.Note;
import fr.pbenoit.proflama.notifications.LocalNotifications;
import fr.pbenoit.proflama.repositories.JsonFileRepository;

public class ProcessTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CharSequence text = getIntent().getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);
        if (text != null && text.length() > 0) {
            addNewTitle(text.toString());
        }
    }


    private void addNewTitle(String title) {
        List<Note> notes = JsonFileRepository.getAllNotes();
        notes.add(new Note(title));
        Collections.sort(notes);
        JsonFileRepository.saveNotes(notes);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        LocalNotifications.sentNotification(pendingIntent, title);

        this.finish();
    }

}
