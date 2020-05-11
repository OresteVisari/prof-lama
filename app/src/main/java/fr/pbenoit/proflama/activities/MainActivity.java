package fr.pbenoit.proflama.activities;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import fr.pbenoit.proflama.NotificationAlarmReceiver;
import fr.pbenoit.proflama.ProfLama;
import fr.pbenoit.proflama.R;
import fr.pbenoit.proflama.adapters.NoteAdapter;
import fr.pbenoit.proflama.dialogs.AddNoteDialog;
import fr.pbenoit.proflama.dialogs.UpdateNoteDialog;
import fr.pbenoit.proflama.models.Note;
import fr.pbenoit.proflama.models.Question;
import fr.pbenoit.proflama.models.TestStatus;
import fr.pbenoit.proflama.repositories.JsonFileRepository;
import fr.pbenoit.proflama.utilities.NotesUtils;
import fr.pbenoit.proflama.utilities.TrainingMode;
import fr.pbenoit.proflama.view.MenuView;

public class MainActivity extends AppCompatActivity implements AddNoteDialog.AddNoteDialogListener, UpdateNoteDialog.UpdateNoteDialogListener {

    // GLOBAL
    private FloatingActionButton floatingActionButton;

    private MenuView menuView;

    // LAYOUT
    private RelativeLayout mainLayout;
    private LinearLayout trainingLayout;
    private LinearLayout trainingResultLayout;

    // MAIN SCREEN
    private List<Note> notes;
    private ListView notesView;

    // QUIZ
    private TrainingMode trainingMode;
    private TextView word;

    LinearLayout linearLayoutAnswer1;
    LinearLayout linearLayoutAnswer2;
    LinearLayout linearLayoutAnswer3;

    private TextView answer1;
    private TextView answer2;
    private TextView answer3;
    private TextView textTrainingResultSuccess;
    private TextView textTrainingResultFailure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main_screen_wrapper);
        this.floatingActionButton = findViewById(R.id.fab);
        this.notesView = findViewById(R.id.notesListView);
        this.menuView = new MenuView(this, findViewById(R.id.textMenuAll), findViewById(R.id.textMenuTraining), findViewById(R.id.textMenuEdition));
        this.mainLayout = findViewById(R.id.mainLayout);
        this.trainingLayout = findViewById(R.id.trainingLayout);
        this.trainingLayout.setVisibility(View.GONE);
        this.trainingResultLayout = findViewById(R.id.trainingResultLayout);
        this.trainingResultLayout.setVisibility(View.GONE);

        notesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Note currentNote = notes.get(i);
                if (currentNote.getDefinition().isEmpty() && currentNote.getQuote().isEmpty()) {
                    openUpdateNoteDialog(i);
                } else {
                    toggleCurrentNote(i);
                }
            }
        });

        notesView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                openUpdateNoteDialog(i);
                return true;
            }
        });


        this.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddNoteDialog();
            }
        });

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancelAll();

        scheduleNotificationAlarm();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.notes = JsonFileRepository.getAllNotes();
        if (notes.isEmpty()) {
            Note defaultNote1 = new Note(getString(R.string.tutorial1title));
            defaultNote1.setDefinition(getString(R.string.tutorial1definition));
            defaultNote1.setQuote(getString(R.string.tutorial1quote));
            defaultNote1.setTestStatus(TestStatus.NOT_APPLICABLE);

            Note defaultNote2 = new Note(getString(R.string.tutorial2title));
            defaultNote2.setDefinition(getString(R.string.tutorial2definition));
            defaultNote2.setQuote(getString(R.string.tutorial2quote));
            defaultNote2.setTestStatus(TestStatus.NOT_APPLICABLE);

            notes.add(defaultNote1);
            notes.add(defaultNote2);
        }
        notesView.setAdapter(new NoteAdapter(this, notes));
    }

    private void scheduleNotificationAlarm() {
        AlarmManager alarmManager = ProfLama.getAppContext().getSystemService(AlarmManager.class);
        int request_code = 0;

        Calendar dateToSchedule = Calendar.getInstance();
        dateToSchedule.set(Calendar.HOUR_OF_DAY, 20);
        dateToSchedule.set(Calendar.MINUTE, 0);
        dateToSchedule.set(Calendar.SECOND, 0);

        Calendar now = Calendar.getInstance();
        if (now.after(dateToSchedule)) {
            dateToSchedule.add(Calendar.DATE, 1);
        }

        Intent intent = new Intent(this, NotificationAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ProfLama.getAppContext(), request_code, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC, dateToSchedule.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void openMainPage() {
        this.menuView.enableMenu(MenuView.ALL);
        this.mainLayout.setVisibility(View.VISIBLE);
        this.trainingLayout.setVisibility(View.GONE);
        this.trainingResultLayout.setVisibility(View.GONE);

        this.floatingActionButton.setImageResource(android.R.drawable.ic_input_add);
        this.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddNoteDialog();
            }
        });
    }

    public void openTrainingMode() {
        List<Note> notesForTraining = NotesUtils.getNotesForTraining(this.notes);
        if (notesForTraining.isEmpty()) {
            Toast.makeText(getApplicationContext(),"You need to have at least 10 complete words to unlock this mode.", Toast.LENGTH_SHORT).show();
            return;
        }

        this.menuView.enableMenu(MenuView.TRAINING);

        this.mainLayout.setVisibility(View.GONE);
        this.trainingResultLayout.setVisibility(View.GONE);
        this.trainingLayout.setVisibility(View.VISIBLE);

        this.floatingActionButton.setImageResource(android.R.drawable.ic_media_play);

        this.word = findViewById(R.id.textTrainingWord);
        this.answer1 = findViewById(R.id.answer1);
        this.answer2 = findViewById(R.id.answer2);
        this.answer3 = findViewById(R.id.answer3);

        this.linearLayoutAnswer1 = findViewById(R.id.layoutAnswer1);
        this.linearLayoutAnswer2 = findViewById(R.id.layoutAnswer2);
        this.linearLayoutAnswer3 = findViewById(R.id.layoutAnswer3);

        linearLayoutAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyQuiz(linearLayoutAnswer1, answer1);
            }
        });
        linearLayoutAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyQuiz(linearLayoutAnswer2, answer2);
            }
        });
        linearLayoutAnswer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyQuiz(linearLayoutAnswer3, answer3);
            }
        });

        this.trainingMode = new TrainingMode(this.notes);
        runQuiz();
    }

    private void runQuiz() {
        if (trainingMode.isQuizFinish()) {
            displayQuizResult();
            return;
        }
        setDefaultBackgroundOnAnswers();
        this.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"You need to found the good response to continue.", Toast.LENGTH_SHORT).show();
            }
        });
        Question question = trainingMode.getNextQuestion();
        if (!question.isSolved()) {
            word.setText(question.getNote().getTitle());
            answer1.setText(question.getAnswers().get(0));
            answer2.setText(question.getAnswers().get(1));
            answer3.setText(question.getAnswers().get(2));
        }

    }

    private void setDefaultBackgroundOnAnswers() {
        linearLayoutAnswer1.setBackgroundResource(R.drawable.shape);
        linearLayoutAnswer2.setBackgroundResource(R.drawable.shape);
        linearLayoutAnswer3.setBackgroundResource(R.drawable.shape);
    }

    private void verifyQuiz(final LinearLayout linearLayoutAnswer, TextView textView) {
        if (this.trainingMode.isValidAnswer(textView.getText().toString())) {
            linearLayoutAnswer.setBackgroundResource(R.drawable.shape_valid);
            this.floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    runQuiz();
                }
            });
        } else {
            linearLayoutAnswer.setBackgroundResource(R.drawable.shape_invalid);
        }
    }

    private void displayQuizResult() {
        this.trainingLayout.setVisibility(View.GONE);
        this.trainingResultLayout.setVisibility(View.VISIBLE);

        this.textTrainingResultSuccess = findViewById(R.id.textTrainingResultSuccess);
        this.textTrainingResultFailure = findViewById(R.id.textTrainingResultFailure);

        String success = "Bravo, tu connais bien les mots: ";
        String failure = "Il faudra cependant réviser les mots: ";

        for (Question question : trainingMode.getQuestions()) {
            if (question.getNote().getTestStatus() == TestStatus.SUCCESS) {
                success += question.getNote().getTitle() + " ";
            } else {
                failure += question.getNote().getTitle() + " ";
            }
        }

        this.textTrainingResultSuccess.setText(success);
        this.textTrainingResultFailure.setText(failure);

        JsonFileRepository.saveQuizResult(this.notes, this.trainingMode.getQuestions());
    }

    private void toggleCurrentNote(int i) {
        Note currentNote = notes.get(i);
        if (currentNote.isShouldDisplayAllFields()) {
            currentNote.setShouldDisplayAllFields(false);
        } else {
            currentNote.setShouldDisplayAllFields(true);
        }
        NoteAdapter adapter = (NoteAdapter) this.notesView.getAdapter();
        adapter.updateList();
    }

    private void openAddNoteDialog() {
        AddNoteDialog addNoteDialog = new AddNoteDialog();
        addNoteDialog.show(getSupportFragmentManager(), "add");
    }

    private void openUpdateNoteDialog(int i) {
        UpdateNoteDialog updateNoteDialog = new UpdateNoteDialog(i, notes.get(i));
        updateNoteDialog.show(getSupportFragmentManager(), "update");
    }

    @Override
    public void addNewNote(String title, String definition, String quote) {
        Note note = new Note(title);
        note.setDefinition(definition);
        note.setQuote(quote);
        notes.add(note);
        Collections.sort(notes);
        this.postUpdateAnyNoteModification();
    }

    @Override
    public void updateNote(int index, String title, String definition, String quote) {
        Note note = notes.get(index);
        note.setTitle(title);
        note.setDefinition(definition);
        note.setQuote(quote);
        this.postUpdateAnyNoteModification();
    }

    @Override
    public void deleteNote(int index) {
        notes.remove(index);
        this.postUpdateAnyNoteModification();
    }

    private void postUpdateAnyNoteModification() {
        NoteAdapter adapter = (NoteAdapter) this.notesView.getAdapter();
        adapter.updateList();
        JsonFileRepository.saveNotes(notes);
    }

}
