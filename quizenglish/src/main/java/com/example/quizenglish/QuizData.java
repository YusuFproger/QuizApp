package com.example.quizenglish;

public class QuizData {
    private String questText, option1, option2, option3;
    private int correctAnsver;

    public QuizData(String questText, String option1, String option2, String option3, int correctAnsver) {
        this.questText = questText;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.correctAnsver = correctAnsver;
    }

    public String getQuestText() {
        return questText;
    }

    public void setQuestText(String questText) {
        this.questText = questText;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public int getCorrectAnsver() {
        return correctAnsver;
    }

    public void setCorrectAnsver(int correctAnsver) {
        this.correctAnsver = correctAnsver;
    }

}