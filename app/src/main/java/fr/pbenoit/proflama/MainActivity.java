package fr.pbenoit.proflama;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import java.util.Collections;
import java.util.List;

import adapters.NoteAdapter;
import models.Note;

public class MainActivity extends AppCompatActivity implements AddNoteDialog.ExampleDialogListener, DeleteNoteDialog.DeleteNoteDialogListener {

    private List<Note> notes;

    private ListView notesView;

    private final String FILE_NAME = "notes.json";

    private final String CHANNEL_ID = "channel_prof_lama";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main2);
        this.notes = readNotesFile();

        this.notesView = findViewById(R.id.notesListView);
        notesView.setAdapter(new NoteAdapter(this, notes));

        notesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                toggleCurrentNote(i);
            }
        });

        notesView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                openDeleteNoteDialog(i);
                return true;
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddNoteDialog();
            }
        });

        CharSequence text = getIntent().getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);
        if (text != null && text.length() > 0) {
            addNewTitle(text.toString());
        }

        createNotificationChannel();
    }

    private void addNewTitle(String title) {
        notes.add(new Note(title));
        Collections.sort(notes);
        NoteAdapter adapter = (NoteAdapter) this.notesView.getAdapter();
        adapter.updateList();
        saveNotesToFile();
        sentNotification(title);
        this.finish();
    }

    private void sentNotification(String title) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Prof Lama")
                .setContentText("Dodano nowe słowo: " + title)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        android.app.NotificationManager notificationManager =
                (android.app.NotificationManager) this.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 /* ID of notification */, builder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //CharSequence name = getString(R.string.channel_name);
            //String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Tltle A", importance);
            channel.setDescription("Description D");
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    private void saveNotesToFile() {
        Gson gson = new Gson();
        String json = gson.toJson(this.notes);

        String externalPath = this.getExternalFilesDir(null).getPath();
        File helloFile = new File(externalPath, FILE_NAME);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(helloFile));
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Note> readNotesFile() {
        Gson gson = new Gson();
        ArrayList<Note> notes = new ArrayList();

        File privateDir = this.getExternalFilesDir(null);
        File file = new File(privateDir.getPath(), FILE_NAME);

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            Type listType = new TypeToken<ArrayList<Note>>(){}.getType();
            notes = gson.fromJson(br, listType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return notes;
    }

    private void toggleCurrentNote(int i) {
        Note currentNote = notes.get(i);
        if (currentNote.isShouldDisplayAllFields()) {
            currentNote.setShouldDisplayAllFields(false);
        } else {
            currentNote.setShouldDisplayAllFields(true);
        }
        NoteAdapter adapter = (NoteAdapter) this.notesView.getAdapter();
        adapter.updateList();
    }

    public void openAddNoteDialog() {
        AddNoteDialog addNoteDialog = new AddNoteDialog();
        addNoteDialog.show(getSupportFragmentManager(), "add");
    }

    public void openDeleteNoteDialog(int i) {
        DeleteNoteDialog deleteNoteDialog = new DeleteNoteDialog(i, notes.get(i).getTitle());
        deleteNoteDialog.show(getSupportFragmentManager(), "delete");
    }


    @Override
    public void applyTexts(String title, String definition, String quote) {
        Note note = new Note(title);
        note.setDefinition(definition);
        note.setQuote(quote);
        notes.add(note);
        Collections.sort(notes);
        NoteAdapter adapter = (NoteAdapter) this.notesView.getAdapter();
        adapter.updateList();
        saveNotesToFile();
    }

    @Override
    public void deleteNote(int i) {
        notes.remove(i);
        NoteAdapter adapter = (NoteAdapter) this.notesView.getAdapter();
        adapter.updateList();
        saveNotesToFile();
    }
}
