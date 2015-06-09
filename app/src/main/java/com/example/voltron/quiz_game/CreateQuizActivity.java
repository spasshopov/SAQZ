package com.example.voltron.quiz_game;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CreateQuizActivity extends ActionBarActivity {
    private User user = null;
    private Quiz quiz = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            this.user = (User)bundle.getSerializable("user");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_quiz, menu);
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

    public void createQuizAction(View v) {
        EditText quizNameText = (EditText) findViewById(R.id.quiznametext);
        EditText quizIdentifierText = (EditText) findViewById(R.id.quizIdentifierText);
        EditText paswordText = (EditText) findViewById(R.id.passwordtext);
        EditText paswordReapeatText = (EditText) findViewById(R.id.passwordtextreapeat);

        final String quizName = quizNameText.getText().toString();
        final String quizIdentifier = quizIdentifierText.getText().toString();
        final String password = paswordText.getText().toString();
        final String passwordReapeat = paswordReapeatText.getText().toString();
        Log.d("CQ: ", quizName);
        if (!password.equals(passwordReapeat)) {
            Toast.makeText(getBaseContext(),
                    "Password doesn't match!", Toast.LENGTH_LONG)
                    .show();
        } else {
            final Db db = new Db();
            Log.d("CQ: ", "creating quiz");
            class createQuiz extends AsyncTask<String, Void, Quiz> {
                @Override
                protected Quiz doInBackground(String... params) {
                    ResultSet result = null;
                    if(db.init()){
                        result = db.createQuiz(quizName, quizIdentifier, password, user.id);
                        Log.d("CQ: ", "sdfsd");
                        Quiz quiz = null;
                        try {
                            while (result.next()) {
                                quiz = new Quiz();
                                quiz.id = result.getInt("id");
                                quiz.identifier = result.getString("identifier");
                                quiz.userId = result.getInt("userId");
                            }
                            return quiz;
                        } catch (SQLException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }

                    return quiz;
                }

                @Override
                protected void onPostExecute(Quiz quiz) {
                    if(quiz!=null){
                        Intent intent = new Intent(CreateQuizActivity.this, QuizOptionsActivity.class);
                        Bundle bundle = new Bundle();
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        bundle.putSerializable("user", user);
                        bundle.putSerializable("quiz", quiz);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }

                @Override
                protected void onPreExecute() {
                }

                @Override
                protected void onProgressUpdate(Void... values) {
                }
            }

            new createQuiz().execute("");
        }

    }
}
