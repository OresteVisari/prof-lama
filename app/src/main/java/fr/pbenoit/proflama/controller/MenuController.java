package fr.pbenoit.proflama.controller;

import android.graphics.Typeface;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import fr.pbenoit.proflama.R;
import fr.pbenoit.proflama.activities.MainActivity;
import fr.pbenoit.proflama.adapters.NoteAdapter;
import fr.pbenoit.proflama.utilities.NotesUtils;

public class MenuController {

    private TextView menuItemAll;
    private TextView menuItemTraining;
    private TextView menuItemEdition;

    public static final String ALL = "ALL";
    public static final String TRAINING = "TRAINING";
    public static final String EDITION = "EDITION";

    public MenuController(final MainActivity activity, final ListView list) {
        this.menuItemAll = activity.findViewById(R.id.textMenuAll);
        this.menuItemTraining = activity.findViewById(R.id.textMenuTraining);
        this.menuItemEdition = activity.findViewById(R.id.textMenuEdition);

        View.OnClickListener onClickMainPageMenu = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.openMainPage();
            }
        };
        View.OnClickListener onClickTrainingMenu = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.openTrainingMode();
            }
        };
        View.OnClickListener onClickEditionMenu = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.openEditionPage();
            }
        };

        menuItemAll.setOnClickListener(onClickMainPageMenu);
        menuItemTraining.setOnClickListener(onClickTrainingMenu);
        menuItemEdition.setOnClickListener(onClickEditionMenu);
    }

    public void enableMenu(String name) {
        switch (name) {
            case TRAINING:
                enableMenuItem(this.menuItemTraining);
                disableMenuItem(this.menuItemAll);
                disableMenuItem(this.menuItemEdition);
                break;
            case EDITION:
                enableMenuItem(this.menuItemEdition);
                disableMenuItem(this.menuItemAll);
                disableMenuItem(this.menuItemTraining);
                break;
            case ALL:
            default:
                enableMenuItem(this.menuItemAll);
                disableMenuItem(this.menuItemTraining);
                disableMenuItem(this.menuItemEdition);
                break;
        }
    }

    private void enableMenuItem(TextView view) {
        view.setBackgroundResource(R.drawable.border);
        view.setTypeface(null, Typeface.BOLD);
    }

    private void disableMenuItem(TextView view) {
        view.setBackgroundResource(0);
        view.setTypeface(null, Typeface.NORMAL);
    }
}
