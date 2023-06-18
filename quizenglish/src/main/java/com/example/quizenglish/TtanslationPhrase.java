package com.example.quizenglish;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TtanslationPhrase extends AppCompatActivity {

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
        setContentView(R.layout.activity_ttanslation_phrase);

        quizData = new ArrayList<>();
        addQuizMethod();

        questionTextView = findViewById(R.id.translationTextView);
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
                        Toast.makeText(TtanslationPhrase.this, "Правильно!", Toast.LENGTH_SHORT).show();
                        correctAnswersCount++;
                        progressBar.setProgress(correctAnswersCount);
                        // Отмена отображения текущего Toast
                        Toast toast = Toast.makeText(TtanslationPhrase.this, "Правильно!", Toast.LENGTH_SHORT);
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
                        Toast.makeText(TtanslationPhrase.this, "Неправильно. Попробуйте еще раз.", Toast.LENGTH_SHORT).show();
                    }

                    // Сброс выбора радиокнопок
                    optionsRadioGroup.clearCheck();
                } else {
                    // Ни один вариант не выбран
                    Toast.makeText(TtanslationPhrase.this, "Выберите вариант ответа.", Toast.LENGTH_SHORT).show();
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
        quizData.add(new QuizData("Can you pass me the salt?",
                "Можешь передать мне соль?", "Можешь подать мне соль?", "Можешь покинуть меня солью?", 0));

        quizData.add(new QuizData("I enjoy playing the piano.",
                "Я наслаждаюсь игрой на фортепиано.", "Мне нравится играть на пианино.", "Я наслаждаюсь игрой на гитаре.", 1));

        quizData.add(new QuizData("Where is the nearest bus stop?",
                "Как пройти к ближайшей автобусной остановке?", "Где я могу найти ближайшую остановку для автобусов?", "Где находится ближайшая автобусная остановка?", 2));

        quizData.add(new QuizData("She is studying medicine at university.",
                "Она изучает медицину в университете.", "Она занимается медициной в колледже.", "Она учится на медсестру в школе.", 0));

        quizData.add(new QuizData("Could you please repeat that?",
                "Пожалуйста, перескажи.", "Ты мог бы пожалуйста повторить это?", "Можешь повторить, пожалуйста?", 2));

        quizData.add(new QuizData("We went hiking in the mountains.",
                "Мы пошли на рыбалку в горах.", "Мы отправились в поход в горы.", "Мы отправились на пикник в горы.", 1));

        quizData.add(new QuizData("The movie starts at 8 p.m.",
                "Фильм начинается в 20:00.", "Фильм будет идти до 8 вечера.", "Фильм начинается в 8 утра.", 0));

        quizData.add(new QuizData("My favorite color is blue.",
                "Мой любимый цвет - красный.", "Мой любимый цвет - синий.", "Мой любимый цвет - зеленый.", 1));

        quizData.add(new QuizData("I need to buy groceries.",
                "Мне нужно купить продукты.", "Мне нужно купить одежду.", "Мне нужно купить мебель.", 0));

        quizData.add(new QuizData("Are you free tomorrow evening?",
                "Ты будешь занят завтра вечером?", "У тебя будет свободное время завтра вечером?", "Ты свободен завтра вечером?", 2));

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
                Intent intent = new Intent(TtanslationPhrase.this, MainActivity.class);
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