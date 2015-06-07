package com.example.voltron.quiz_game;

/**
 * Created by Voltron on 14.2.2015 г..
 */
import android.util.Log;

import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Db {

    Connection con;

    public Connection connect() {
        Connection conn = null;
        String encoding = "?useUnicode=true&characterEncoding=UTF-8";
        String url = "jdbc:mysql://10.0.2.2:3306/";
//        String url = "jdbc:mysql://87.97.216.220:65415/";
        String dbName = "quiz_db";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "";
//        String userName = "project";
//        String password = "SAQZpass";
        try {
            Class.forName(driver).newInstance();
            Log.d("Connection: ", "Before");
            conn = ConnectionWithTimeout.getConnection(url + dbName + encoding, userName, password,driver,4);
            //conn = DriverManager.getConnection(url + dbName + encoding, userName, password);
            Log.d("Connection: ", "After");

            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d("Exception mysql:",e.getMessage());
            return null;
        } catch (Exception ee) {
            ee.printStackTrace();
            Log.d("Exception:",ee.getMessage());
            return null;
        }
    }

    public boolean init(){
        this.con = connect();
        if (con == null) {
            Log.d("Connection: ", "NO!");
            return false;
        }else {
            Log.d("Connection: ", "YES!");
            try {
                con.getAutoCommit();
            } catch (SQLException sqlE) {
                return false;
            }
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

    public int createQuestion(String questionText, int userId) throws Exception{
        try {
            String sql = "INSERT INTO `questions`(`question`, `user_id`) VALUES ('"+questionText+"','"+userId+"')";
            PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
            ResultSet result;
            result = statement.getGeneratedKeys();

            if(result.next() && result != null){
                return result.getInt(1);
            } else {
                throw new Exception("not inserted");
            }

//            statement.execute();

        } catch (SQLException e) {
            throw new Exception("not inserted");
        }
    }

    public boolean createAnswer(String answerText, int questionId, boolean correct, int index){
        try {
            String sql = "INSERT INTO `answers`(`question_id`, `correct`, `answer`, `index`) VALUES ('"+questionId+"',"+correct+",'"+answerText+"','"+index+"')";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Question getQuestionForUser(User user) {
        try {
            String query = "SELECT * \n" +
                    "FROM questions, answers\n" +
                    "WHERE questions.id = answers.question_id\n" +
                    "AND questions.user_id != "+user.id+"\n" +
                    "AND questions.id NOT IN (SELECT user_answered.question_id FROM user_answered WHERE user_answered.user_id = "+user.id+")\n" +
                    "LIMIT 4;";

            PreparedStatement statement = con.prepareStatement(query);

            ResultSet result = statement.executeQuery();
            Question question = new Question();

            while(result.next()) {
                question.question = result.getString("question");
                question.answer[result.getInt("index")] = result.getString("answer");
                question.id = result.getInt("question_id");
                question.reports = result.getInt("reports");
                if (result.getInt("correct") == 1) {
                    question.correctAnswer = result.getInt("index");
                }
            }

            if(question.question == null) {
                return null;
            }

            return  question;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String correctAnswerUpdate(User user, Question question) {
        try {
            String sql = "UPDATE `user` SET points = "+user.points+" WHERE id = "+user.id+";";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.execute();

            sql = "UPDATE `user` SET number_answered = "+user.number_answered+" WHERE id = "+user.id+";";
            statement = con.prepareStatement(sql);
            statement.execute();

            sql = "INSERT INTO `user_answered`(`question_id`, `user_id`) VALUES ("+question.id+","+user.id+")";
            statement = con.prepareStatement(sql);
            statement.execute();

            sql = "SELECT username FROM user LIMIT 1;";
            PreparedStatement statementUser = con.prepareStatement(sql);

            ResultSet result = statementUser.executeQuery();
            String userName = null;
            while(result.next()) {
                userName = result.getString("username");;
            }

            return  userName;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateUserPoints(User user) {
        try {
            String sql = "UPDATE `user` SET points = "+user.points+" WHERE id = "+user.id+";";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void falseAnswerUpdate(User user, Question question) {
        try {
            String sql = "INSERT INTO `user_answered`(`question_id`, `user_id`) VALUES ("+question.id+","+user.id+")";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.execute();

            sql = "UPDATE `user` SET points = "+user.points+" WHERE id = "+user.id+";";
            statement = con.prepareStatement(sql);
            statement.execute();

            sql = "UPDATE `user` SET number_answered = "+user.number_answered+" WHERE id = "+user.id+";";
            statement = con.prepareStatement(sql);
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void reportQuestion(Question question) {
        try {
            String sql = ";";
            if(question.reports <  5) {
                sql = "UPDATE `questions` SET reports = " + question.reports + " WHERE id = " + question.id + ";";
            } else {
                sql = "DELETE FROM `questions` WHERE id = "+question.id+";";
            }
            PreparedStatement statement = con.prepareStatement(sql);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<User> getAllUsers(){
        ArrayList<User> users = new ArrayList<User>();
        try {
            String query = "SELECT * FROM `user` ORDER BY points DESC";
            PreparedStatement statement = con.prepareStatement(query);

            ResultSet result = statement.executeQuery();

            while(result.next()){
                User user = new User();
                user.id = result.getInt("id");
                user.points = result.getInt("points");
                user.nickname = result.getString("nickname");
                user.email = result.getString("username");
                users.add(user);
            }

            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<User> getAllUsersForChart(int number_answered) {
        ArrayList<User> users = new ArrayList<User>();
        try {
            float min_answered = (float) (number_answered - 0.1*number_answered);
            float max_answered = (float) (number_answered + 0.1*number_answered);
            String query = "SELECT * FROM `user` WHERE number_answered < "+max_answered+" AND number_answered > "+min_answered+" ORDER BY points DESC";
            PreparedStatement statement = con.prepareStatement(query);

            ResultSet result = statement.executeQuery();

            while(result.next()){
                User user = new User();
                user.id = result.getInt("id");
                user.points = result.getInt("points");
                user.nickname = result.getString("nickname");
                user.email = result.getString("username");
                users.add(user);
            }

            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet createQuiz(String quizName, String quizIdentifier, String password, int userId) {
        try {
            String sql = "INSERT INTO `quiz` (`userId`, `name`, `identifier`, `password`) VALUES ("+userId+", '"+quizName+"', '"+quizIdentifier+"', MD5('"+password+"'));";
            PreparedStatement statement = con.prepareStatement(sql);
            Log.d("SQL: ", sql);
            statement.execute();

            String query = "SELECT * FROM `quiz` WHERE identifier = '"+quizIdentifier+"' AND password = MD5('"+password+"');";
            Log.d("SQL: ", query);
            statement = con.prepareStatement(query);

            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int createQuizQuestion(String questionText, int userId, int quizId) throws Exception {
        try {
            String sql = "INSERT INTO `questions`(`question`, `user_id`, `quiz_id`) VALUES ('"+questionText+"','"+userId+"','"+quizId+"')";
            PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
            ResultSet result;
            result = statement.getGeneratedKeys();

            if(result.next() && result != null){
                return result.getInt(1);
            } else {
                throw new Exception("not inserted");
            }

//            statement.execute();

        } catch (SQLException e) {
            throw new Exception("not inserted");
        }
    }
}
