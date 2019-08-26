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
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

import fr.pbenoit.proflama.R;
import fr.pbenoit.proflama.models.Note;


public class UpdateNoteDialog extends AppCompatDialogFragment {
    private int indexNoteToUpdate;
    private Note note;

    private EditText editTextTitle;
    private EditText editTextDefinition;
    private EditText editTextQuote;
    private UpdateNoteDialogListener listener;

    public UpdateNoteDialog(int indexNoteToUpdate, Note note) {
        this.indexNoteToUpdate = indexNoteToUpdate;
        this.note = note;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view)
                .setTitle(R.string.updateDialogTitle)
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNeutralButton(R.string.updateDialogDelete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.deleteNote(indexNoteToUpdate);
                    }
                })
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String title = editTextTitle.getText().toString();
                        String definition = editTextDefinition.getText().toString();
                        String quote = editTextQuote.getText().toString();
                        listener.updateNote(indexNoteToUpdate, title, definition, quote);
                    }
                });

        editTextTitle = view.findViewById(R.id.edit_title);
        editTextTitle.setText(note.getTitle());

        editTextDefinition = view.findViewById(R.id.edit_definition);
        editTextDefinition.setText(note.getDefinition());

        editTextQuote = view.findViewById(R.id.edit_quote);
        editTextQuote.setText(note.getQuote());

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

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
            listener = (UpdateNoteDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement UpdateNoteDialogListener");
        }
    }

    public interface UpdateNoteDialogListener {
        void updateNote(int index, String title, String definition, String quote);
        void deleteNote(int index);
    }
}