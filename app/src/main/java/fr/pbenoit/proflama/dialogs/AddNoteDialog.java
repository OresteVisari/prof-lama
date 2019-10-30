package fr.pbenoit.proflama.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

import fr.pbenoit.proflama.R;


public class AddNoteDialog extends AppCompatDialogFragment {
    private EditText editTextTitle;
    private EditText editTextDefinition;
    private EditText editTextQuote;
    private AddNoteDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.update_note_dialog, null);

        builder.setView(view)
                .setTitle("Nowe Słowo   ( ^ ᗜ ^ )")
                .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String title = editTextTitle.getText().toString();
                        String definition = editTextDefinition.getText().toString();
                        String quote = editTextQuote.getText().toString();
                        listener.addNewNote(title, definition, quote);
                    }
                });

        editTextTitle = view.findViewById(R.id.edit_title);
        editTextDefinition = view.findViewById(R.id.edit_definition);
        editTextQuote = view.findViewById(R.id.edit_quote);

        view .findViewById(R.id.button_switch_title_and_content).setVisibility(View.GONE);
        view .findViewById(R.id.button_switch_content_and_quote).setVisibility(View.GONE);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
        editTextTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                } else {
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                }
            }
        });

        return alertDialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (AddNoteDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement AddNoteDialogListener");
        }
    }

    public interface AddNoteDialogListener {
        void addNewNote(String title, String definition, String quote);
    }
}