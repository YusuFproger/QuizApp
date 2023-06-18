package com.example.quizenglish;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Grammar extends AppCompatActivity {

    private List<QuizData> quizData;
    private int currentQuestionIndex;
    private int correctAnswersCount;

    private TextView questionTextView;
    private RadioGroup optionsRadioGroup;
    private Button button;
    private ProgressBar progressBar;

    private Dialog congratulationsDialog;
    private Button exitButton;
    private Button retryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammar);

        quizData = new ArrayList<>();
        addQuizMethod();

        questionTextView = findViewById(R.id.questionTextView);
        optionsRadioGroup = findViewById(R.id.optionsRadioGroup);
        button = findViewById(R.id.button);
        progressBar = findViewById(R.id.progressBar);

        showQuestion(currentQuestionIndex);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedOptionId = optionsRadioGroup.getCheckedRadioButtonId();
                if (selectedOptionId != -1) {
                    RadioButton selectedOption = findViewById(selectedOptionId);
                    int selectedOptionIndex = optionsRadioGroup.indexOfChild(selectedOption);
                    int correctOptionIndex = quizData.get(currentQuestionIndex).getCorrectAnsver();

                    if (selectedOptionIndex == correctOptionIndex) {
                        // Правильный ответ
                        Toast.makeText(Grammar.this, "Правильно!", Toast.LENGTH_SHORT).show();
                        correctAnswersCount++;
                        progressBar.setProgress(correctAnswersCount);
                        // Отмена отображения текущего Toast
                        Toast toast = Toast.makeText(Grammar.this, "Правильно!", Toast.LENGTH_SHORT);
                        toast.show();
                        toast.cancel();

                        // Переход к следующему вопросу
                        currentQuestionIndex++;
                        if (currentQuestionIndex < quizData.size()) {
                            showQuestion(currentQuestionIndex);
                        } else {
                            // Все вопросы пройдены
                            showCongratulationsDialog();
                        }
                    } else {
                        // Неправильный ответ
                        Toast.makeText(Grammar.this, "Неправильно. Попробуйте еще раз.", Toast.LENGTH_SHORT).show();
                    }

                    // Сброс выбора радиокнопок
                    optionsRadioGroup.clearCheck();
                } else {
                    // Ни один вариант не выбран
                    Toast.makeText(Grammar.this, "Выберите вариант ответа.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void showQuestion(int index) {
        if (index < quizData.size()) {
            QuizData currentQuestion = quizData.get(index);
            questionTextView.setText(currentQuestion.getQuestText());
            ((RadioButton) optionsRadioGroup.getChildAt(0)).setText(currentQuestion.getOption1());
            ((RadioButton) optionsRadioGroup.getChildAt(1)).setText(currentQuestion.getOption2());
            ((RadioButton) optionsRadioGroup.getChildAt(2)).setText(currentQuestion.getOption3());
        }
    }

    private void addQuizMethod() {
        quizData.add(new QuizData("Какой правильный вариант в прошедшем времени для глагола \"говорить\"?", "sayed","spoke","told",1));
        quizData.add(new QuizData("Какое предложение является грамматически верным?", "I has a dog.","She have blue eyes.","They have gone to the beach.",2));
        quizData.add(new QuizData("Какой вариант является правильной формой глагола \"быть\" в настоящем времени для местоимения \"они\"?", "is","am","are",2));
        quizData.add(new QuizData("Какое предложение использует правильную форму глагола в настоящем продолженном времени?","I am study English.","She is studying English.","They is study English.",1));
        quizData.add(new QuizData("Какой правильный вариант вопросительного местоимения для обозначения места?","what","where","when",1));
        quizData.add(new QuizData("Какое предложение использует правильную форму сравнительной степени прилагательного \"высокий\"?","This building is more higher.","This building is high.","This building is higher.",2));
        quizData.add(new QuizData("Какой правильный вариант местоимения для обозначения третьего лица единственного числа в винительном падеже?","me","him","her",2));
        quizData.add(new QuizData("Какое предложение использует правильную форму глагола в отрицательном предложении в прошедшем времени?","I didn't go to the party.","I didn't went to the party.","I didn't went to the party.",0));
        quizData.add(new QuizData("Какой правильный вариант неопределенного артикля?"," an","the","a",2));
        quizData.add(new QuizData("Какое предложение использует правильный порядок слов в вопросительном предложении?","He where lives?","Where he lives?","Lives he where?",1));
    }

    private void showCongratulationsDialog() {
        congratulationsDialog = new Dialog(this);
        congratulationsDialog.setContentView(R.layout.dialog_congratulations);
        congratulationsDialog.setCancelable(false);

        exitButton = congratulationsDialog.findViewById(R.id.exitButton);
        retryButton = congratulationsDialog.findViewById(R.id.retryButton);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                congratulationsDialog.dismiss();
                Intent intent = new Intent(Grammar.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                congratulationsDialog.dismiss();
                // Сброс состояния вопросов и прогресса
                currentQuestionIndex = 0;
                correctAnswersCount = 0;
                progressBar.setProgress(correctAnswersCount);
                showQuestion(currentQuestionIndex);
            }
        });

        congratulationsDialog.show();

    }

}