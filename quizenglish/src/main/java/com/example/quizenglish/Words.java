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

public class Words extends AppCompatActivity {

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
        setContentView(R.layout.activity_words);

        quizData = new ArrayList<>();
        addQuizMethod();

        questionTextView = findViewById(R.id.wordsTextView);
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
                        Toast.makeText(Words.this, "Правильно!", Toast.LENGTH_SHORT).show();
                        correctAnswersCount++;
                        progressBar.setProgress(correctAnswersCount);
                        // Отмена отображения текущего Toast
                        Toast toast = Toast.makeText(Words.this, "Правильно!", Toast.LENGTH_SHORT);
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
                        Toast.makeText(Words.this, "Неправильно. Попробуйте еще раз.", Toast.LENGTH_SHORT).show();
                    }

                    // Сброс выбора радиокнопок
                    optionsRadioGroup.clearCheck();
                } else {
                    // Ни один вариант не выбран
                    Toast.makeText(Words.this, "Выберите вариант ответа.", Toast.LENGTH_SHORT).show();
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

    private void addQuizMethod(){
        quizData.add(new QuizData("House",
                "Дом", "Жилище", "Квартира", 0));

        quizData.add(new QuizData("Car",
                "Машина", "Автомобиль", "Транспортное средство", 1));

        quizData.add(new QuizData("Book",
                "Том", "Сборник", "Книга", 2));

        quizData.add(new QuizData("Dog",
                "Собака", "Пёс", "Шавка", 0));

        quizData.add(new QuizData("Computer",
                "Компьютер", "ПК (Персональный компьютер)", "Электронная машина", 0));

        quizData.add(new QuizData("Tree",
                "Лес", "Растение", "Дерево", 2));

        quizData.add(new QuizData("Chair",
                "Кресло", "Стул", "Табуретка", 1));

        quizData.add(new QuizData("Water",
                "Вода", "Жидкость", "Аква", 0));

        quizData.add(new QuizData("Sun",
                "Сияние", "Свет", "Солнце", 2));

        quizData.add(new QuizData("Food",
                "Пища", "Еда", "Пища", 1));

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
                Intent intent = new Intent(Words.this, MainActivity.class);
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