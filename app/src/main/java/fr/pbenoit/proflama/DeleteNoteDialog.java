package fr.pbenoit.proflama;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;


public class DeleteNoteDialog extends AppCompatDialogFragment {

    private DeleteNoteDialogListener listener;

    private int indexElementToDelete;

    private String wordToDelete;

    public DeleteNoteDialog(int indexElementToDelete, String wordToDelete) {
        this.indexElementToDelete = indexElementToDelete;
        this.wordToDelete = wordToDelete;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder
                .setTitle("Usuń słowo")
                .setMessage("Czy chcesz usunąć \""+ wordToDelete  + "\" ?")
                .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.deleteNote(indexElementToDelete);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (DeleteNoteDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement DeleteNoteDialogListener");
        }
    }

    public interface DeleteNoteDialogListener {
        void deleteNote(int i);
    }
}