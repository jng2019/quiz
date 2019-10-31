package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Main_Activity extends AppCompatActivity {

    private TextView showQuestion;
    private TextView score;
    private Button trueButton;
    private Button falseButton;
    private Button playAgainButton;
    private Quiz game;

    public static final String TAG = Main_Activity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        // load the questions from the json file as a string
        InputStream XmlFileInputStream = getResources().openRawResource(R.raw.questions); //getting xml
        String jsonString = readTextFile(XmlFileInputStream);
        // create a gson object
        Gson gson = new Gson();
        // read your json file into an array of questions
        Question[] questions =  gson.fromJson(jsonString, Question[].class);
        // convert your array to a list using the Arrays utility class
        List<Question> questionList = Arrays.asList(questions);
        // verify that it read everything properly
        Log.d(TAG, "onCreate: " + questionList.toString());

        wirewidgets();
        game = new Quiz(questionList);
        setOnClickListener();
        updateDisplay();
    }

    private void checkForEnd() {
        if (game.getCurrentQuestion() < 0) {
            trueButton.setVisibility(View.INVISIBLE);
            falseButton.setVisibility(View.INVISIBLE);
            playAgainButton.setVisibility(View.VISIBLE);
            score.setVisibility(View.INVISIBLE);
            showQuestion.setText("Congratulations, your score was " + game.getScore() + " click the button below to play again");
        }
    }
    private void resetGame() {
        game.setScore(0);
        game.setCurrentQuestion(game.getListOfQuestions().size() - 1);
        updateDisplay();
        trueButton.setVisibility(View.VISIBLE);
        falseButton.setVisibility(View.VISIBLE);
        playAgainButton.setVisibility(View.GONE);
        score.setVisibility(View.VISIBLE);
    }

    private void updateDisplay() {
        if (game.getCurrentQuestion() > 0) {
            showQuestion.setText(String.valueOf(game.getListOfQuestions().get(game.getCurrentQuestion() - 1).getQuestion()));
            score.setText(String.valueOf(game.getScore()));
        }

    }

    private void setOnClickListener() {
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game.checkAnswer(true);
                game.setCurrentQuestion(game.getCurrentQuestion() - 1);
                if (game.isCorrect()) {
                    Toast.makeText(Main_Activity.this, "Correct!", Toast.LENGTH_SHORT).show();
                }
                if (!game.isCorrect()) {
                    Toast.makeText(Main_Activity.this, "Incorrect!", Toast.LENGTH_SHORT).show();
                }
                checkForEnd();
                if (game.getCurrentQuestion() > 0) {
                    updateDisplay();
                }
            }
        });
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game.checkAnswer(false);
                game.setCurrentQuestion(game.getCurrentQuestion() - 1);
                if (game.isCorrect()) {
                    Toast.makeText(Main_Activity.this, "Correct!", Toast.LENGTH_SHORT).show();
                }
                if (!game.isCorrect()) {
                    Toast.makeText(Main_Activity.this, "Incorrect!", Toast.LENGTH_SHORT).show();
                }
                checkForEnd();
                if (game.getCurrentQuestion() != 0) {
                    updateDisplay();
                }
            }
        });
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    private void wirewidgets() {
        showQuestion = findViewById(R.id.question_textView);
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        score = findViewById(R.id.textView_score_mainactivity);
        playAgainButton = findViewById(R.id.button_playAgain_MainActivity);
    }

    public String readTextFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {

        }
        return outputStream.toString();
    }


}
