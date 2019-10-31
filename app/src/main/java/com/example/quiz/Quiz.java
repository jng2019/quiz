package com.example.quiz;

import java.util.List;

public class Quiz {
    private List<Question> listOfQuestions;
    private int score = 0;
    private int currentQuestion;

    public Quiz(List<Question> listOfQuestions) {
        this.listOfQuestions = listOfQuestions;
        currentQuestion = listOfQuestions.size();
    }

    public void setCurrentQuestion(int currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public List<Question> getListOfQuestions() {
        return listOfQuestions;
    }

    public int getScore() {
        return score;
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void checkAnswer(boolean answer){
        if (currentQuestion > 0) {
            if (answer == listOfQuestions.get(currentQuestion - 1).isAnswer()) {
                score++;
            }
        }
    }
}
