package fr.pbenoit.proflama.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.PopupMenu;

import fr.pbenoit.proflama.R;
import fr.pbenoit.proflama.activities.MainActivity;
import fr.pbenoit.proflama.utilities.NotesUtils;

public class MenuController {

    private static int CLICK_ON_LOGO_COUNTER = 0;

    private TextView menuItemAll;
    private TextView menuItemTraining;

    private PopupMenu popupMenu;
    private ImageView mainLogo;
    private ImageView optionMenuView;

    public static final String ALL = "ALL";
    public static final String TRAINING = "TRAINING";
    public static String CURRENT_MENU = ALL;


    public MenuController(final MainActivity activity) {
        this.menuItemAll = activity.findViewById(R.id.textMenuAll);
        this.menuItemTraining = activity.findViewById(R.id.textMenuTraining);
        this.mainLogo = activity.findViewById(R.id.logoLama);
        this.optionMenuView = activity.findViewById(R.id.option_menu_icon);

        ContextThemeWrapper wrapper = new ContextThemeWrapper(activity, R.style.MyPopupTheme);
        this.popupMenu = new PopupMenu(wrapper, this.optionMenuView);

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

        final PopupMenu pop = this.popupMenu;
        View.OnClickListener onClickOptionMenu = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop.show();
            }
        };

        View.OnClickListener onClickMainLogo = buildMainLogoListener(activity);
        mainLogo.setOnClickListener(onClickMainLogo);

        menuItemAll.setOnClickListener(onClickMainPageMenu);
        menuItemTraining.setOnClickListener(onClickTrainingMenu);
        optionMenuView.setOnClickListener(onClickOptionMenu);

        MenuInflater inflater = this.popupMenu.getMenuInflater();
        inflater.inflate(R.menu.option_menu, this.popupMenu.getMenu());
        this.popupMenu.getMenu().getItem(0).setChecked(true);
        this.popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.isChecked()) {
                    return false;
                }
                item.setChecked(true);
                activity.changeOrder();
                return true;
            }
        });
    }

    private View.OnClickListener buildMainLogoListener(final MainActivity activity) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CLICK_ON_LOGO_COUNTER++;
                if (CLICK_ON_LOGO_COUNTER % 5 != 0) {
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AlertDialogTheme);

                LayoutInflater inflater = activity.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.information_dialog, null);
                builder.setView(dialogView)
                        .setTitle("Information")
                        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                TextView numberOfWords = dialogView.findViewById(R.id.number_of_words);
                numberOfWords.setText("You have " + activity.getNotes().size() + " words.");

                TextView numberOfUncompletedWords = dialogView.findViewById(R.id.number_of_uncompleted_words);
                numberOfUncompletedWords.setText("You have " + NotesUtils.getUncompletedNote(activity.getNotes()).size() + " uncompleted words.");

                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(Color.RED);
            }
        };
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
