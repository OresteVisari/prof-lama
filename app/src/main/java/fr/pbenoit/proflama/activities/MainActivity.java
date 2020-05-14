package fr.pbenoit.proflama.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.List;

import fr.pbenoit.proflama.R;
import fr.pbenoit.proflama.adapters.EditionNoteAdapter;
import fr.pbenoit.proflama.adapters.NoteAdapter;
import fr.pbenoit.proflama.controller.MenuController;
import fr.pbenoit.proflama.controller.TrainingController;
import fr.pbenoit.proflama.dialogs.AddNoteDialog;
import fr.pbenoit.proflama.dialogs.UpdateNoteDialog;
import fr.pbenoit.proflama.models.Note;
import fr.pbenoit.proflama.models.TestStatus;
import fr.pbenoit.proflama.notifications.NotificationManager;
import fr.pbenoit.proflama.repositories.JsonFileRepository;
import fr.pbenoit.proflama.utilities.NotesUtils;

public class MainActivity extends AppCompatActivity implements AddNoteDialog.AddNoteDialogListener, UpdateNoteDialog.UpdateNoteDialogListener {

    private FloatingActionButton floatingActionButton;
    private MenuController menuController;

    private RelativeLayout mainLayout;
    private LinearLayout trainingLayout;
    private LinearLayout trainingResultLayout;

    private List<Note> notes;
    private ListView notesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main_screen_wrapper);
        this.floatingActionButton = findViewById(R.id.fab);
        this.notesView = findViewById(R.id.notesListView);
        this.menuController = new MenuController(this, notesView);
        this.mainLayout = findViewById(R.id.mainLayout);
        this.trainingLayout = findViewById(R.id.trainingLayout);
        this.trainingLayout.setVisibility(View.GONE);
        this.trainingResultLayout = findViewById(R.id.trainingResultLayout);
        this.trainingResultLayout.setVisibility(View.GONE);

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


        this.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddNoteDialog();
            }
        });

        NotificationManager.disableDisplayedNotification(this);
        NotificationManager.scheduleNotificationAlarm(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.notes = JsonFileRepository.getAllNotes();
        Collections.sort(this.notes);
        if (notes.isEmpty()) {
            Note defaultNote1 = new Note(getString(R.string.tutorial1title));
            defaultNote1.setDefinition(getString(R.string.tutorial1definition));
            defaultNote1.setQuote(getString(R.string.tutorial1quote));
            defaultNote1.setTestStatus(TestStatus.NOT_APPLICABLE);

            Note defaultNote2 = new Note(getString(R.string.tutorial2title));
            defaultNote2.setDefinition(getString(R.string.tutorial2definition));
            defaultNote2.setQuote(getString(R.string.tutorial2quote));
            defaultNote2.setTestStatus(TestStatus.NOT_APPLICABLE);

            notes.add(defaultNote1);
            notes.add(defaultNote2);
        }
        notesView.setAdapter(new NoteAdapter(this, notes));
        //notesView.setAdapter(new EditionNoteAdapter(this, NotesUtils.getUncompleteNote(notes)));
    }

    public void openMainPage() {
        if (MenuController.CURRENT_MENU == MenuController.ALL) {
            Note.toggleMode();
            Collections.sort(notes);
            notesView.setAdapter(new NoteAdapter(this, notes));
            return;
        }
        this.menuController.enableMenu(MenuController.ALL);
        this.mainLayout.setVisibility(View.VISIBLE);
        this.trainingLayout.setVisibility(View.GONE);
        this.trainingResultLayout.setVisibility(View.GONE);

        this.floatingActionButton.setImageResource(android.R.drawable.ic_input_add);
        this.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddNoteDialog();
            }
        });
        this.floatingActionButton.show();
        this.notesView.setAdapter(new NoteAdapter(this, notes));
    }

    public void openTrainingMode() {
        if (MenuController.CURRENT_MENU == MenuController.TRAINING) {
            return;
        }
        List<Note> notesForTraining = NotesUtils.getNotesForTraining(this.notes);
        if (notesForTraining.isEmpty()) {
            Toast.makeText(getApplicationContext(),"You need to have at least 10 complete words to unlock this mode.", Toast.LENGTH_SHORT).show();
            return;
        }

        this.menuController.enableMenu(MenuController.TRAINING);

        this.mainLayout.setVisibility(View.GONE);
        this.trainingResultLayout.setVisibility(View.GONE);
        this.trainingLayout.setVisibility(View.VISIBLE);

        this.floatingActionButton.setImageResource(android.R.drawable.ic_media_play);
        this.floatingActionButton.show();
        TrainingController trainingController = new TrainingController(this, this.floatingActionButton, this.notes);
        trainingController.runQuiz();
    }

    public void openEditionPage() {
        this.menuController.enableMenu(MenuController.EDITION);
        this.mainLayout.setVisibility(View.VISIBLE);
        this.trainingLayout.setVisibility(View.GONE);
        this.trainingResultLayout.setVisibility(View.GONE);

        this.floatingActionButton.hide();
        this.notesView.setAdapter(new NoteAdapter(this, NotesUtils.getUncompleteNote(this.notes)));
    }

    private void toggleCurrentNote(int i) {
        notes.get(i).toggleShouldDisplayAllFields();
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

    public List<Note> getNotes() {
        return this.notes;
    }
}
