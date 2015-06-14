package com.example.voltron.quiz_game;

import java.io.Serializable;

/**
 * Created by Voltron on 7.6.2015 г..
 */
public class Quiz implements Serializable{
    public int id;
    public int userId;
    public int time;
    public String creatorEmail;
    public String identifier;
    public String name;
    public Question[] questions;
}
