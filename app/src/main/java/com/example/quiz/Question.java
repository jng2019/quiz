package com.example.quiz;

public class Question {
    private boolean answer;
    private String question;

    public Question(boolean answer, String question) {
        this.answer = answer;
        this.question = question;
    }

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }


    @Override
    public String toString() {
        return "Question{" +
                "answer=" + answer +
                ", question='" + question + '\'' +
                '}';
    }
}
