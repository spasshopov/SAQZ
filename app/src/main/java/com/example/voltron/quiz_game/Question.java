package com.example.voltron.quiz_game;

import java.io.Serializable;

/**
 * Classs to represent question entity
 */
public class Question implements Serializable{
    public int id;
    public String question;
    public String[] answer = new String[4];
    public int correctAnswer;
    public int reports;
}
