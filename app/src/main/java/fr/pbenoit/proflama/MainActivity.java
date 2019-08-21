package fr.pbenoit.proflama;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
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
import adapters.NoteTitleAdapter;
import models.Note;

public class MainActivity extends AppCompatActivity implements AddNoteDialog.ExampleDialogListener {

    private List<Note> notes;

    private Button modalButton;

    private ListView notesView;

    private final String FILE_NAME = "notes.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main2);
        this.notes = readNotesFile();

        this.notesView = findViewById(R.id.notesListView);
        notesView.setAdapter(new NoteAdapter(this, notes));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

//    private void addNewTitle() {
//        EditText newTitle = findViewById(R.id.newTitleText);
//        notes.add(new Note(newTitle.getText().toString()));
//        newTitle.setText("");
//        Collections.sort(notes);
//        NoteTitleAdapter adapter = (NoteTitleAdapter) this.notesView.getAdapter();
//        adapter.updateList();
//        saveNotesToFile();
//    }

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

    public void openDialog() {
        AddNoteDialog addNoteDialog = new AddNoteDialog();
        addNoteDialog.show(getSupportFragmentManager(), "example dialog");
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

}
