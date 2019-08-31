package fr.pbenoit.proflama.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ProcessTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CharSequence text = getIntent().getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);
        if (text != null && text.length() > 0) {
            addNewTitle(text.toString());
        }
    }


    private void addNewTitle(final String title) {
        final Activity activity = this;

        new Thread(new Runnable() {
            @Override
            public void run() {
                new FetchWordFromWiktionary(activity).execute(title);
            }
        }).start();

        this.finish();
    }

}
