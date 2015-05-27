package com.example.voltron.quiz_game;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;


public class ConnectionWithTimeout extends Thread {

    private static String _url;
    private static String _user;
    private static String _password;
    private static String _driver;

    private static volatile Connection _connection = null;
    private static volatile boolean _sleep = true;
    private static volatile SQLException _sqlException = null;
    private static volatile ClassNotFoundException _classNotFoundException = null;

    @Override
    public void run() {
        try {
            Class.forName(_driver);
            _connection = DriverManager.getConnection(_url, _user, _password);
        }
        catch (SQLException ex) {
            _sqlException = ex;
        }
        catch (ClassNotFoundException ex) {
            _classNotFoundException = ex;
        }
        _sleep = false;
    }

    public static Connection getConnection(String url,
                                           String user,
                                           String password,
                                           String driver,
                                           int timeoutInSeconds)
            throws SQLException, ClassNotFoundException {

        checkStringOrThrow(url,      "url");
        checkStringOrThrow(user,     "user");
        checkStringOrThrow(password, "password");
        checkStringOrThrow(driver,   "driver");

        if (timeoutInSeconds < 1) {
            throw new IllegalArgumentException(
                    "timeoutInSeconds must be positive");
        }

        _url = url;
        _user = user;
        _password = password;
        _driver = driver;

        ConnectionWithTimeout conn = new ConnectionWithTimeout();
        conn.start();

        try {
            for (int i = 0; i < timeoutInSeconds; i++) {
                if (_sleep) {
                    Thread.sleep(1000);
                }
            }
        }
        catch (InterruptedException ex) {
        }

        if (_sqlException != null) {
            throw _sqlException;
        }

        if (_classNotFoundException != null) {
            throw _classNotFoundException;
        }

        return _connection;
    }

    private static void checkStringOrThrow(String variable, String variableName) {
        if (variable == null) {
            throw new IllegalArgumentException(
                    "String is null: " + variableName);
        }
    }
}