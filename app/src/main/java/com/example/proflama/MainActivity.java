package com.example.proflama;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adapters.NoteAdapter;
import models.Note;

public class MainActivity extends AppCompatActivity {

    private List<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        this.notes = new ArrayList<>();

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

        ListView notesView = findViewById(R.id.notesListView);
        notesView.setAdapter(new NoteAdapter(this, notes));
    }
}
