package com.example.voltron.quiz_game;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;


public class QuizStartActivity extends ActionBarActivity {

    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_start);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            this.user = (User)bundle.getSerializable("user");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz_start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startQuizAction(View v) {
        EditText quizIdentifierField = (EditText) findViewById(R.id.quizIdentifier);
        EditText passwordField = (EditText) findViewById(R.id.passwordtext);

        final String quizIdentifier = quizIdentifierField.getText().toString();
        final String password = passwordField.getText().toString();

        final Db db = new Db();
        class getQuiz extends AsyncTask<String, Void, Quiz> {
            @Override
            protected Quiz doInBackground(String... params) {
                ResultSet result = null;
                Quiz quiz = null;
                if(db.init()){
                    result = db.getQuiz(quizIdentifier, password);
                    try {
                        while (result.next()) {
                            quiz = new Quiz();
                            quiz.id = result.getInt("id");
                            quiz.name = result.getString("name");
                            quiz.identifier = result.getString("identifier");
                            quiz.userId = result.getInt("userId");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                    }
                }

                return quiz;
            }

            @Override
            protected void onPostExecute(Quiz quiz) {
                if(quiz!=null) {
                    QuizStartActivity.this.startQuizActivity(quiz);
                }else{
                    Toast.makeText(getBaseContext(),
                            "Wrong quiz identifier or/and password", Toast.LENGTH_LONG)
                            .show();
                }
            }

            @Override
            protected void onPreExecute() {
            }

            @Override
            protected void onProgressUpdate(Void... values) {
            }
        }

        new getQuiz().execute("");
    }

    private void startQuizActivity(Quiz quiz) {
        Toast.makeText(getBaseContext(),
                "Starting quiz: "+quiz.name, Toast.LENGTH_LONG)
                .show();
    }
}
