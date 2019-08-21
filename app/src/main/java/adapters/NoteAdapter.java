package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import fr.pbenoit.proflama.R;

import java.util.List;

import models.Note;

public class NoteAdapter extends BaseAdapter implements ListAdapter {

    private Context context;

    private List<Note> notes;

    private LayoutInflater layoutInflater;

    public NoteAdapter(Context context, List<Note> notes) {
        this.context = context;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.note, null);

        Note currentNote = getItem(i);
        TextView title = view.findViewById(R.id.title);
        title.setText(currentNote.getTitle());

        TextView definition = view.findViewById(R.id.definition);
        definition.setText(currentNote.getDefinition());
        return view;
    }

    public void updateList() {
        this.notifyDataSetChanged();
    }
}
