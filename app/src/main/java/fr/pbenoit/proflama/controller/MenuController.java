package fr.pbenoit.proflama.controller;

import android.graphics.Typeface;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import fr.pbenoit.proflama.R;
import fr.pbenoit.proflama.activities.MainActivity;

public class MenuController {

    private TextView menuItemAll;
    private TextView menuItemTraining;

    public static final String ALL = "ALL";
    public static final String TRAINING = "TRAINING";
    public static String CURRENT_MENU = ALL;


    public MenuController(final MainActivity activity, final ListView list) {
        this.menuItemAll = activity.findViewById(R.id.textMenuAll);
        this.menuItemTraining = activity.findViewById(R.id.textMenuTraining);

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

        menuItemAll.setOnClickListener(onClickMainPageMenu);
        menuItemTraining.setOnClickListener(onClickTrainingMenu);
    }

    public void enableMenu(String name) {
        switch (name) {
            case TRAINING:
                CURRENT_MENU = TRAINING;
                enableMenuItem(this.menuItemTraining);
                disableMenuItem(this.menuItemAll);
                break;
            case ALL:
            default:
                CURRENT_MENU = ALL;
                enableMenuItem(this.menuItemAll);
                disableMenuItem(this.menuItemTraining);
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
