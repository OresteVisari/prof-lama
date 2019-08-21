package fr.pbenoit.proflama;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;


public class AddNoteDialog extends AppCompatDialogFragment {
    private EditText editTextTitle;
    private EditText editTextDefinition;
    private EditText editTextQuote;
    private ExampleDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view)
                .setTitle("Nowe Słowo   ( ^ ᗜ ^ )")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String username = editTextTitle.getText().toString();
                        String password = editTextDefinition.getText().toString();
                        String quote = editTextQuote.getText().toString();
                        listener.applyTexts(username, password, quote);
                    }
                });

        editTextTitle = view.findViewById(R.id.edit_title);
        editTextDefinition = view.findViewById(R.id.edit_definition);
        editTextQuote = view.findViewById(R.id.edit_quote);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener {
        void applyTexts(String username, String password, String quote);
    }
}