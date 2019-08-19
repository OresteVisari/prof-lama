package fr.pbenoit.proflama;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import adapters.NoteTitleAdapter;
import models.Note;

public class MainActivity extends AppCompatActivity {

    private List<Note> notes;

    private Button addTitleButton;

    private ListView notesView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        this.notes = new ArrayList<>();

        addTitleButton = findViewById(R.id.addTitleButton);
        addTitleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewTitle();
            }
        });

        Note note1 = new Note("Aimer");
        note1.setDefinition("Eprouver de l'affection, de l'amour ou de l'attachement pour quelqu'un ou quelque chose.");
        note1.setQuote("\"Aimer, ce n'est pas se regarder l'un l'autre, c'est regarder ensemble dans la même direction.\"\nAntoine De Saint-Exupéry");

        Note note2 = new Note("Passion");
        note2.setDefinition("État affectif intense et irraisonné qui domine quelqu'un.");
        note2.setQuote("Nous respectons la raison, mais nous aimons nos passions.");

        Note note3 = new Note("Retrouvailles");

        notes.add(note1);
        notes.add(note2);
        notes.add(note1);
        notes.add(note2);
        notes.add(note1);
        notes.add(note2);

        notes.add(note3);
        notes.add(note3);

        this.notesView = findViewById(R.id.notesListView);
        notesView.setAdapter(new NoteTitleAdapter(this, notes));
    }

    private void addNewTitle() {
        EditText newTitle = findViewById(R.id.newTitleText);
        notes.add(new Note(newTitle.getText().toString()));
        Collections.sort(notes);
        NoteTitleAdapter adapter = (NoteTitleAdapter) this.notesView.getAdapter();
        adapter.updateList();

        Gson gson = new Gson();
        String json = gson.toJson(this.notes);
        System.out.println(json);

        Type listType = new TypeToken<ArrayList<Note>>(){}.getType();
        List<Note> yourClassList = new Gson().fromJson(json, listType);
        System.out.println("after json des: " +  yourClassList.get(0).getTitle() );
        System.out.println("after json des date: " +  yourClassList.get(0).getCreationDate() );

        File privateDir = this.getExternalFilesDir(null);
        System.out.println(privateDir.getAbsolutePath());
    }

}
