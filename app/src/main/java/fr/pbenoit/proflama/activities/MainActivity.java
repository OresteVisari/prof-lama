package fr.pbenoit.proflama.activities;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import fr.pbenoit.proflama.NotificationAlarmReceiver;
import fr.pbenoit.proflama.ProfLama;
import fr.pbenoit.proflama.R;
import fr.pbenoit.proflama.adapters.NoteAdapter;
import fr.pbenoit.proflama.dialogs.AddNoteDialog;
import fr.pbenoit.proflama.dialogs.UpdateNoteDialog;
import fr.pbenoit.proflama.models.Note;
import fr.pbenoit.proflama.repositories.JsonFileRepository;

public class MainActivity extends AppCompatActivity implements AddNoteDialog.AddNoteDialogListener, UpdateNoteDialog.UpdateNoteDialogListener {

    private List<Note> notes;

    private ListView notesView;

    private TextView menuItemTraining;

    private TextView menuItemEdition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main_screen_wrapper);
        this.notesView = findViewById(R.id.notesListView);
        this.menuItemTraining = findViewById(R.id.textMenuTraining);
        this.menuItemEdition = findViewById(R.id.textMenuEdition);

        notesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Note currentNote = notes.get(i);
                if (currentNote.getDefinition().isEmpty() && currentNote.getQuote().isEmpty()) {
                    openUpdateNoteDialog(i);
                } else {
                    toggleCurrentNote(i);
                }
            }
        });

        notesView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                openUpdateNoteDialog(i);
                return true;
            }
        });

        View.OnClickListener toaster = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Coming soon!", Toast.LENGTH_SHORT).show();
            }
        };
        menuItemTraining.setOnClickListener(toaster);
        menuItemEdition.setOnClickListener(toaster);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddNoteDialog();
            }
        });

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancelAll();

        scheduleNotificationAlarm();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.notes = JsonFileRepository.getAllNotes();
        if (notes.isEmpty()) {
            Note defaultNote1 = new Note(getString(R.string.tutorial1title));
            defaultNote1.setDefinition(getString(R.string.tutorial1definition));
            defaultNote1.setQuote(getString(R.string.tutorial1quote));

            Note defaultNote2 = new Note(getString(R.string.tutorial2title));
            defaultNote2.setDefinition(getString(R.string.tutorial2definition));
            defaultNote2.setQuote(getString(R.string.tutorial2quote));

            notes.add(defaultNote1);
            notes.add(defaultNote2);
        }
        notesView.setAdapter(new NoteAdapter(this, notes));
    }

    private void scheduleNotificationAlarm() {
        AlarmManager alarmManager = ProfLama.getAppContext().getSystemService(AlarmManager.class);
        int request_code = 0;

        Calendar dateToSchedule = Calendar.getInstance();
        dateToSchedule.set(Calendar.HOUR_OF_DAY, 20);
        dateToSchedule.set(Calendar.MINUTE, 0);
        dateToSchedule.set(Calendar.SECOND, 0);

        Calendar now = Calendar.getInstance();
        if (now.after(dateToSchedule)) {
            dateToSchedule.add(Calendar.DATE, 1);
        }

        Intent intent = new Intent(this, NotificationAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ProfLama.getAppContext(), request_code, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC, dateToSchedule.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
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

    private void openAddNoteDialog() {
        AddNoteDialog addNoteDialog = new AddNoteDialog();
        addNoteDialog.show(getSupportFragmentManager(), "add");
    }

    private void openUpdateNoteDialog(int i) {
        UpdateNoteDialog updateNoteDialog = new UpdateNoteDialog(i, notes.get(i));
        updateNoteDialog.show(getSupportFragmentManager(), "update");
    }

    @Override
    public void addNewNote(String title, String definition, String quote) {
        Note note = new Note(title);
        note.setDefinition(definition);
        note.setQuote(quote);
        notes.add(note);
        Collections.sort(notes);
        this.postUpdateAnyNoteModification();
    }

    @Override
    public void updateNote(int index, String title, String definition, String quote) {
        Note note = notes.get(index);
        note.setTitle(title);
        note.setDefinition(definition);
        note.setQuote(quote);
        this.postUpdateAnyNoteModification();
    }

    @Override
    public void deleteNote(int index) {
        notes.remove(index);
        this.postUpdateAnyNoteModification();
    }

    private void postUpdateAnyNoteModification() {
        NoteAdapter adapter = (NoteAdapter) this.notesView.getAdapter();
        adapter.updateList();
        JsonFileRepository.saveNotes(notes);
    }

}
