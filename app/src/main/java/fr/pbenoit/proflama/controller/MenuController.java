package fr.pbenoit.proflama.controller;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import fr.pbenoit.proflama.R;
import fr.pbenoit.proflama.activities.MainActivity;

public class MenuController {

    private TextView menuItemAll;

    private TextView menuItemTraining;

    private TextView menuItemEdition;

    public static final String ALL = "ALL";

    public static final String TRAINING = "TRAINING";


    public MenuController(final MainActivity mainActivity, View menuItemAll, View menuItemTraining, View menuItemEdition) {
        this.menuItemAll = (TextView) menuItemAll;
        this.menuItemTraining = (TextView) menuItemTraining;
        this.menuItemEdition = (TextView) menuItemEdition;

        View.OnClickListener onClickMainPageMenu = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.openMainPage();
            }
        };
        View.OnClickListener onClickTrainingMenu = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.openTrainingMode();
            }
        };
        View.OnClickListener toasterEdition = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mainActivity.getApplicationContext(),"Coming soon!", Toast.LENGTH_SHORT).show();
            }
        };

        menuItemAll.setOnClickListener(onClickMainPageMenu);
        menuItemTraining.setOnClickListener(onClickTrainingMenu);
        menuItemEdition.setOnClickListener(toasterEdition);
    }

    public void enableMenu(String name) {
        TextView viewToEnable;
        TextView viewToDisable;
        switch (name) {
            case TRAINING:
                viewToEnable = this.menuItemTraining;
                viewToDisable = this.menuItemAll;
                break;
            case ALL:
            default:
                viewToEnable = this.menuItemAll;
                viewToDisable = this.menuItemTraining;
                break;
        }

        viewToDisable.setBackgroundResource(0);
        viewToDisable.setTypeface(null, Typeface.NORMAL);

        viewToEnable.setBackgroundResource(R.drawable.border);
        viewToEnable.setTypeface(null, Typeface.BOLD);
    }
}
