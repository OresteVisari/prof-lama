package fr.pbenoit.proflama.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

import fr.pbenoit.proflama.R;
import fr.pbenoit.proflama.models.Note;

public class EditionNoteAdapter extends BaseAdapter implements ListAdapter {

    private final List<Note> notes;

    private final LayoutInflater layoutInflater;

    public EditionNoteAdapter(Context context, List<Note> notes) {
        this.notes = notes;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Note getItem(int i) {
        return notes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.update_note_dialog, null);
        view.setBackgroundResource(R.drawable.shape);

        Note currentNote = getItem(position);
        EditText title = view.findViewById(R.id.edit_title);
        title.setText(currentNote.getTitle());

        EditText definition = view.findViewById(R.id.edit_definition);
        definition.setText(currentNote.getDefinition());

        EditText quote = view.findViewById(R.id.edit_quote);
        quote.setText(currentNote.getQuote());

        TextView save = view.findViewById(R.id.action_save);
        save.setVisibility(View.VISIBLE);
        TextView delete = view.findViewById(R.id.actions_delete);
        delete.setVisibility(View.VISIBLE);

        return view;
    }

    public void updateList() {
        this.notifyDataSetChanged();
    }
}
