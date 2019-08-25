package fr.pbenoit.proflama.activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.List;

import adapters.NoteAdapter;
import fr.pbenoit.proflama.R;
import fr.pbenoit.proflama.dialogs.AddNoteDialog;
import fr.pbenoit.proflama.dialogs.DeleteNoteDialog;
import fr.pbenoit.proflama.models.Note;
import fr.pbenoit.proflama.notifications.LocalNotifications;
import fr.pbenoit.proflama.repositories.JsonFileRepository;

public class MainActivity extends AppCompatActivity implements AddNoteDialog.ExampleDialogListener, DeleteNoteDialog.DeleteNoteDialogListener {

    private List<Note> notes;

    private ListView notesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main2);
        this.notes = JsonFileRepository.getAllNotes();

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

        //NotificationManager notificationManager = (NotificationManager)getSystemService(this.NOTIFICATION_SERVICE);
        //notificationManager.cancelAll();
        LocalNotifications.createNotificationChannel();
    }

    private void addNewTitle(String title) {
        notes.add(new Note(title));
        Collections.sort(notes);
        NoteAdapter adapter = (NoteAdapter) this.notesView.getAdapter();
        adapter.updateList();
        JsonFileRepository.saveNotes(notes);

        Intent intent = new Intent(this, this.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        LocalNotifications.sentNotification(pendingIntent, title);

        this.finish();
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
        JsonFileRepository.saveNotes(notes);
    }

    @Override
    public void deleteNote(int i) {
        notes.remove(i);
        NoteAdapter adapter = (NoteAdapter) this.notesView.getAdapter();
        adapter.updateList();
        JsonFileRepository.saveNotes(notes);
    }
}
