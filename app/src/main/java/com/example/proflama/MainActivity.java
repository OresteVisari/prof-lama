package com.example.proflama;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.List;

import models.Note;

public class MainActivity extends AppCompatActivity {

    private List<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.setContentView(R.layout.activity_main);

        Note defaultNote = new Note("Aimer");
        defaultNote.setDefinition("Eprouver de l'affection, de l'amour ou de l'attachement pour quelqu'un ou quelque chose.");
        defaultNote.setQuote("\"Aimer, ce n'est pas se regarder l'un l'autre, c'est regarder ensemble dans la même direction.\"\nAntoine De Saint-Exupéry");

        TextView title = findViewById(R.id.title);
        title.setText(defaultNote.getTitle());

        TextView definition = findViewById(R.id.definition);
        definition.setText(defaultNote.getDefinition());

        //TextView quote = findViewById(R.id.quote);
        //quote.setText(defaultNote.getQuote());


        Note note2 = new Note("Passion");
        note2.setDefinition("État affectif intense et irraisonné qui domine quelqu'un");
        note2.setQuote("Nous respectons la raison, mais nous aimons nos passions.");

        TextView title2 = findViewById(R.id.title2);
        title2.setText(note2.getTitle());

        TextView definition2 = findViewById(R.id.definition2);
        definition2.setText(note2.getDefinition());


    }
}
