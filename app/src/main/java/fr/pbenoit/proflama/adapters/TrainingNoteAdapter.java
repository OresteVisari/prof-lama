package fr.pbenoit.proflama.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

import fr.pbenoit.proflama.R;
import fr.pbenoit.proflama.models.Note;

public class TrainingNoteAdapter extends BaseAdapter implements ListAdapter {

    private final List<Note> notes;

    private final LayoutInflater layoutInflater;

    public TrainingNoteAdapter(Context context, List<Note> notes) {
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
        view = layoutInflater.inflate(R.layout.note, null);

        Note currentNote = getItem(position);
        TextView title = view.findViewById(R.id.title);
        title.setText(currentNote.getDefinition());

        TextView definition = view.findViewById(R.id.definition);
        TextView quote = view.findViewById(R.id.quote);

        definition.setVisibility(View.GONE);
        quote.setVisibility(View.GONE);

        TextView date = view.findViewById(R.id.date);
        date.setVisibility(View.GONE);

        return view;
    }

    public void updateList() {
        this.notifyDataSetChanged();
    }
}
