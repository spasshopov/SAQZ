package com.example.voltron.quiz_game;

/**
 * Classs to represent question entity
 */
public class Question {
    public int id;
    public String question;
    public String[] answer = new String[4];
    public int correctAnswer;
    public int reports;
}
