package fr.pbenoit.proflama.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import fr.pbenoit.proflama.utilities.NotesUtils;

import static fr.pbenoit.proflama.models.Question.NUMBER_OF_ANSWER;

public class TrainingMode {

    public static int NUMBER_OF_WORD_IN_TRAINING = 5;

    private int questionIndex;

    List<Question> questions;

    public TrainingMode(List<Note> allNotes) {
        this.questions = new ArrayList<>();
        this.questionIndex = 0;
        List<Note> notesCompleted = NotesUtils.getCompletedNote(allNotes);
        List<Note> notesForTraining = NotesUtils.getNotesForTraining(allNotes);
        for (Note note : notesForTraining) {
            questions.add(buildQuestion(notesCompleted, note));
        }
        Collections.shuffle(questions);
    }

    public Question getNextQuestion() {
        this.questionIndex++;
        if (questionIndex == NUMBER_OF_WORD_IN_TRAINING) {
            questionIndex = 0;
        }

        Question currentQuestion = this.questions.get(questionIndex);
        if (!currentQuestion.isSolved()) {
            currentQuestion.shuffleAnswers();
            currentQuestion.setIsFirstTryForThisTurn(true);
            return currentQuestion;
        }
        return getNextQuestion();
    }

    private Question buildQuestion(List<Note> allNotesCompleted, Note note) {
        Question question = new Question(note);
        question.addAnswer(note);

        final Random rand = new Random();
        final Set<Integer> intSet = new HashSet<>();

        while (intSet.size() < NUMBER_OF_ANSWER) {
            intSet.add(rand.nextInt(allNotesCompleted.size()));
        }

        for (Integer i : intSet) {
            if (!allNotesCompleted.get(i.intValue()).getDefinition().equals(note.getDefinition())) {
                question.addAnswer(allNotesCompleted.get(i.intValue()));
            }
        }

        question.shuffleAnswers();
        return question;
    }

    public boolean isValidAnswer(String answer) {
        Question question = this.questions.get(questionIndex);
        if (question.getNote().getDefinitionToDisplay().toString().equals(answer)) {
            question.changeToSolved();
            return true;
        }
        question.firstTurnDone();
        return false;
    }

    public boolean isQuizFinish() {
        for (Question question : questions) {
            if (!question.isSolved()) {
                return false;
            }
        }
        return true;
    }

    public List<Question> getQuestions() {
        return this.questions;
    }
}
