package com.example.voltron.quiz_game;

import java.io.Serializable;

/**
 * Created by Voltron on 13.6.2015 г..
 */
public class QuizHistory implements Serializable {
    public int id;
    public int userId;
    public int quizId;
    public int successRate;
    public String wrongAnswered;
    public String quizName;
}
