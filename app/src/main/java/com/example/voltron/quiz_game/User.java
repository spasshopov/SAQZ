package com.example.voltron.quiz_game;

import java.io.Serializable;

/**
 * Created by Voltron on 22.2.2015 г..
 */
public class User implements Serializable{

    private static final long serialVersionUID = 230L;
    public String nickname;
    public String email;
    public int points;
    public int id;

}
