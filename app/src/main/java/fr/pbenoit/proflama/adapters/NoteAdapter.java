package fr.pbenoit.proflama.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import fr.pbenoit.proflama.R;

import java.util.List;

import fr.pbenoit.proflama.activities.MainActivity;
import fr.pbenoit.proflama.models.Note;

public class NoteAdapter extends BaseAdapter implements ListAdapter {

    private final MainActivity activity;

    private final List<Note> notes;

    private final LayoutInflater layoutInflater;

    public NoteAdapter(MainActivity activity, List<Note> notes) {
        this.activity = activity;
        this.notes = notes;
        this.layoutInflater = LayoutInflater.from(activity);
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
    public View getView(final int position, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.note, null);

        Note currentNote = getItem(position);
        TextView title = view.findViewById(R.id.title);
        title.setText(currentNote.getTitle());

        TextView definition = view.findViewById(R.id.definition);
        definition.setText(currentNote.getDefinitionToDisplay());

        TextView quote = view.findViewById(R.id.quote);
        quote.setText(currentNote.getQuote());

        TextView date = view.findViewById(R.id.date);

        ImageView editIcon = view.findViewById(R.id.note_edit_icon);
        if (!currentNote.getDefinition().isEmpty() && !currentNote.isShouldDisplayAllFields()) {
            editIcon.setVisibility(View.INVISIBLE);
        } else {
            editIcon.setVisibility(View.VISIBLE);
        }
        editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.openUpdateNoteDialog(position);
            }
        });

        definition.setVisibility(View.GONE);
        quote.setVisibility(View.GONE);

        if (currentNote.isShouldDisplayAllFields()) {
            if (currentNote.getDefinition() != null && !currentNote.getDefinition().isEmpty()) definition.setVisibility(View.VISIBLE);
            if (currentNote.getQuote() != null && !currentNote.getQuote().isEmpty()) quote.setVisibility(View.VISIBLE);
        } else {
            definition.setVisibility(View.GONE);
            quote.setVisibility(View.GONE);
        }

        if (shouldDisplayGroupTitle(position)) {
            date.setVisibility(View.VISIBLE);
            date.setText(getGroupTitle(currentNote));
        } else {
            date.setVisibility(View.GONE);
        }

        return view;
    }

    private boolean shouldDisplayGroupTitle(final int position) {
        if (position == 0) {
            return true;
        }
        if (Note.ORDER_MODE == Note.DATE) {
            return !getItem(position - 1).getFormattedCreationDate().equals(getItem(position).getFormattedCreationDate());
        }
        return !getItem(position - 1).getFirstLetterOfFormattedTitle().equals(getItem(position).getFirstLetterOfFormattedTitle());
    }

    private String getGroupTitle(final Note note) {
        if (Note.ORDER_MODE == Note.DATE) {
            return note.getFormattedCreationDate();
        }
        return note.getFirstLetterOfFormattedTitle().toUpperCase();
    }

    public void updateList() {
        this.notifyDataSetChanged();
    }
}
