package com.example.voltron.quiz_game;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;


public class QuizActivity extends ActionBarActivity {

    private User user;
    private Quiz quiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            this.user = (User)bundle.getSerializable("user");
            this.quiz = (Quiz)bundle.getSerializable("quiz");
        }

        final Db db = new Db();
        class getQuestionsForQuiz extends AsyncTask<String, Void, Quiz> {
            @Override
            protected Quiz doInBackground(String... params) {
                ResultSet result = null;
                if(db.init()){
                    result = db.getQuestionIdsForQuiz(QuizActivity.this.quiz.id);
                    try {
                        int rowcount = 0;
                        if (result.last()) {
                            rowcount = result.getRow();
                            result.beforeFirst();
                        }
                        QuizActivity.this.quiz.questions = new Question[rowcount];
                        rowcount = 0;
                        while (result.next()) {
                            Question question = this.getAnswersForQuestion(result.getInt("id"), db);
                            QuizActivity.this.quiz.questions[rowcount] = question;
                            rowcount++;
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                    }
                }

                return QuizActivity.this.quiz;
            }

            private Question getAnswersForQuestion(int id, Db db) {
                return db.getAnswersForQuestion(id);
            }

            @Override
            protected void onPostExecute(Quiz quiz) {
                if(quiz.questions !=null ){
                        Toast.makeText(getBaseContext(),
                                "Taking a quiz with: !"+quiz.questions.length+" questions", Toast.LENGTH_LONG)
                                .show();
                }else {
                    Toast.makeText(getBaseContext(),
                            "Quiz empty!", Toast.LENGTH_LONG)
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

        new getQuestionsForQuiz().execute("");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
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
}
