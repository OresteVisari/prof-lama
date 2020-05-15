package fr.pbenoit.proflama.controller;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import fr.pbenoit.proflama.R;
import fr.pbenoit.proflama.activities.MainActivity;
import fr.pbenoit.proflama.models.Note;
import fr.pbenoit.proflama.models.Question;
import fr.pbenoit.proflama.models.TestStatus;
import fr.pbenoit.proflama.models.TrainingMode;
import fr.pbenoit.proflama.repositories.JsonFileRepository;

public class TrainingController {

    private  MainActivity activity;

    private FloatingActionButton floatingActionButton;
    private List<Note> notes;
    private TrainingMode trainingMode;

    private LinearLayout linearLayoutAnswer1;
    private LinearLayout linearLayoutAnswer2;
    private LinearLayout linearLayoutAnswer3;

    private TextView textTrainingWord;

    private TextView answer1;
    private TextView answer2;
    private TextView answer3;

    private TextView textTrainingResultSuccess;
    private TextView textTrainingResultFailure;


    public TrainingController(final MainActivity activity, FloatingActionButton floatingActionButton, List<Note> notes) {
        this.activity = activity;
        this.floatingActionButton = floatingActionButton;
        this.notes = notes;
        this.textTrainingWord = activity.findViewById(R.id.textTrainingWord);
        this.answer1 = activity.findViewById(R.id.answer1);
        this.answer2 = activity.findViewById(R.id.answer2);
        this.answer3 = activity.findViewById(R.id.answer3);
        this.linearLayoutAnswer1 = activity.findViewById(R.id.layoutAnswer1);
        this.linearLayoutAnswer2 = activity.findViewById(R.id.layoutAnswer2);
        this.linearLayoutAnswer3 = activity.findViewById(R.id.layoutAnswer3);

        this.textTrainingResultSuccess = activity.findViewById(R.id.textTrainingResultSuccess);
        this.textTrainingResultFailure = activity.findViewById(R.id.textTrainingResultFailure);

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

        this.trainingMode = new TrainingMode(notes);
    }

    public void runQuiz() {
        if (this.trainingMode.isQuizFinish()) {
            this.floatingActionButton.hide();
            displayQuizResult();
            return;
        }
        setDefaultBackgroundOnAnswers();
        this.floatingActionButton.hide();
        this.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runQuiz();
            }
        });

        Question question = trainingMode.getNextQuestion();
        if (!question.isSolved()) {
            textTrainingWord.setText(question.getNote().getTitle());
            answer1.setText(question.getAnswers().get(0).getDefinitionToDisplay().toString());
            answer2.setText(question.getAnswers().get(1).getDefinitionToDisplay().toString());
            answer3.setText(question.getAnswers().get(2).getDefinitionToDisplay().toString());
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
            this.floatingActionButton.show();
        } else {
            linearLayoutAnswer.setBackgroundResource(R.drawable.shape_invalid);
        }
    }

    private void displayQuizResult() {
        LinearLayout trainingLayout = activity.findViewById(R.id.trainingLayout);
        LinearLayout trainingResultLayout = activity.findViewById(R.id.trainingResultLayout);
        trainingLayout.setVisibility(View.GONE);
        trainingResultLayout.setVisibility(View.VISIBLE);

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
}
