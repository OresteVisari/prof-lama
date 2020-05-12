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

    public MenuController(final MainActivity activity) {
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
        View.OnClickListener toasterEdition = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity.getApplicationContext(),"Coming soon!", Toast.LENGTH_SHORT).show();
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
