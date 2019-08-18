package com.example.proflama;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import models.Note;

public class MainActivity extends AppCompatActivity {

    private List<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Note defaultNote = new Note("Aimer");
        defaultNote.setDefinition("Eprouver de l'affection, de l'amour ou de l'attachement pour quelqu'un ou quelque chose. ");
        defaultNote.setQuote("\"Aimer, ce n'est pas se regarder l'un l'autre, c'est regarder ensemble dans la même direction.\"\nAntoine De Saint-Exupéry");

        TextView title = findViewById(R.id.title);
        title.setText(defaultNote.getTitle());

        TextView definition = findViewById(R.id.definition);
        definition.setText(defaultNote.getDefinition());

        TextView quote = findViewById(R.id.quote);
        quote.setText(defaultNote.getQuote());

        TextView quote2 = findViewById(R.id.quote2);
        quote2.setText("Si je devais choisir entre t'aimer et respirer, je voudrais avoir mon dernier souffle pour dire Je t'aime.");
    }
}
