package com.example.voltron.quiz_game;

/**
 * Created by Voltron on 14.2.2015 г..
 */
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class Db {

    Connection con;

    public Connection connect() {
        Connection conn = null;
        String url = "jdbc:mysql://127.0.0.1:3306/";
        String dbName = "quiz_db";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "";
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/" + dbName, userName, password);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Exception:",e.getMessage());
            return null;
        }
    }

    public boolean init(){
        this.con = connect();
        if (con == null) {
            return false;
        }else {
            return true;
        }
    }

    public boolean createUser(String email, String nickname, String password){
        try {
            String sql = "INSERT INTO `user`(`username`, `nickname`, `points`, `password`) VALUES ('"+email+"','"+nickname+"','"+0+"',MD5('"+password+"'))";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet getUser(String email, String password) {

        try {
            String query = "SELECT * FROM `user` WHERE username = '"+email+"' AND password = MD5('"+password+"');";
            PreparedStatement statement = con.prepareStatement(query);

            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
