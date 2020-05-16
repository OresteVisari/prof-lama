package fr.pbenoit.proflama.controller;

import android.graphics.Typeface;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.PopupMenu;

import fr.pbenoit.proflama.R;
import fr.pbenoit.proflama.activities.ImportActivity;
import fr.pbenoit.proflama.activities.MainActivity;

public class MenuController {

    private TextView menuItemAll;
    private TextView menuItemTraining;

    private PopupMenu popupMenu;
    private ImageView optionMenuView;

    public static final String ALL = "ALL";
    public static final String TRAINING = "TRAINING";
    public static String CURRENT_MENU = ALL;


    public MenuController(final MainActivity activity, final ListView list) {
        this.menuItemAll = activity.findViewById(R.id.textMenuAll);
        this.menuItemTraining = activity.findViewById(R.id.textMenuTraining);
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

        menuItemAll.setOnClickListener(onClickMainPageMenu);
        menuItemTraining.setOnClickListener(onClickTrainingMenu);
        optionMenuView.setOnClickListener(onClickOptionMenu);

        //final ImportActivity importActivity = new ImportActivity();

        MenuInflater inflater = this.popupMenu.getMenuInflater();
        inflater.inflate(R.menu.option_menu, this.popupMenu.getMenu());
        this.popupMenu.getMenu().getItem(0).setChecked(true);
        this.popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getOrder() == 2) {
                    activity.openFile();
                    return false;
                }
                if (item.isChecked()) {
                    return false;
                }
                item.setChecked(true);
                activity.changeOrder();
                return true;
            }
        });
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
