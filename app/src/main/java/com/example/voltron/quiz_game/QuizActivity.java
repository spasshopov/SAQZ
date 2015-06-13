package com.example.voltron.quiz_game;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;


public class QuizActivity extends ActionBarActivity {

    private RadioButton a;
    private RadioButton b;
    private RadioButton c;
    private RadioButton d;
    TextView q;
    TextView a_answer;
    TextView b_answer;
    TextView c_answer;
    TextView d_answer;

    private User user;
    private Quiz quiz;
    private int numberOfQuestions;
    private Question loadedQuestion;
    private QuizHistory quizHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        q = (TextView) findViewById(R.id.questionText);
        a_answer = (TextView) findViewById(R.id.answer_row_a);
        b_answer = (TextView) findViewById(R.id.answer_row_b);
        c_answer = (TextView) findViewById(R.id.answer_row_c);
        d_answer = (TextView) findViewById(R.id.answer_row_d);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            this.user = (User)bundle.getSerializable("user");
            this.quiz = (Quiz)bundle.getSerializable("quiz");
            quizHistory = new QuizHistory();
            quizHistory.userId = user.id;
            quizHistory.quizId = quiz.id;
            quizHistory.successRate = 0;
            quizHistory.wrongAnswered = "";
        } else {
            return;
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
                        QuizActivity.this.numberOfQuestions = rowcount;
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
                    QuizActivity.this.loadNextQuestion();
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
        initiateRadioButtons();
    }

    private void loadNextQuestion() {
        Log.d("Questions: ", " "+quiz.questions.length);
        if (quiz.questions.length > 0) {
            this.q.setText(quiz.questions[0].question);
            this.a_answer.setText(quiz.questions[0].answer[0]);
            this.b_answer.setText(quiz.questions[0].answer[1]);
            this.c_answer.setText(quiz.questions[0].answer[2]);
            this.d_answer.setText(quiz.questions[0].answer[3]);
            this.loadedQuestion = quiz.questions[0];
            Question[] questions = new Question[quiz.questions.length - 1];
            for (int i = 1; i < quiz.questions.length; i++) {
                questions[i - 1] = quiz.questions[i];
            }

            quiz.questions = questions;
        } else {
            quizHistory.successRate = (int) ((quizHistory.successRate*100)/numberOfQuestions);
            Toast.makeText(getBaseContext(),
                    "Success rate: "+quizHistory.successRate+"%\n Wrong: \n"+quizHistory.wrongAnswered, Toast.LENGTH_LONG)
                    .show();

            quizHistory.quizName = quiz.name;
            saveQuizHistory(quizHistory);
            startQuizInfoActivity(quizHistory);
            return;
        }
    }

    private void startQuizInfoActivity(QuizHistory quizHistory) {
        Intent intent = new Intent(this, QuizInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        bundle.putSerializable("quizHistory", quizHistory);
        intent.putExtras(bundle);
        finish();
        startActivity(intent);
    }

    private void saveQuizHistory(final QuizHistory quizHistory) {
        final Db db = new Db();
        class saveQuizHistory extends AsyncTask<String, Void, Boolean> {
            @Override
            protected Boolean doInBackground(String... params) {
                ResultSet result = null;
                if(db.init()){
                    try {
                      db.saveQuizHistory(quizHistory);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                }

                return true;
            }

            private Question getAnswersForQuestion(int id, Db db) {
                return db.getAnswersForQuestion(id);
            }

            @Override
            protected void onPostExecute(Boolean success) {
            }

            @Override
            protected void onPreExecute() {
            }

            @Override
            protected void onProgressUpdate(Void... values) {
            }
        }

        new saveQuizHistory().execute("");
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

    public void initiateRadioButtons(){
        a = (RadioButton) findViewById(R.id.answer_a);
        b = (RadioButton) findViewById(R.id.answer_b);
        c = (RadioButton) findViewById(R.id.answer_c);
        d = (RadioButton) findViewById(R.id.answer_d);

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a.setChecked(true);
                b.setChecked(false);
                c.setChecked(false);
                d.setChecked(false);
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.setChecked(true);
                a.setChecked(false);
                c.setChecked(false);
                d.setChecked(false);
            }
        });

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.setChecked(true);
                b.setChecked(false);
                a.setChecked(false);
                d.setChecked(false);
            }
        });

        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.setChecked(true);
                b.setChecked(false);
                c.setChecked(false);
                a.setChecked(false);
            }
        });
    }

    public void answerQuestion(View v) {

        boolean correct = false;
        if (this.loadedQuestion.correctAnswer == 0 && a.isChecked()) {
            correct = true;
        }

        if (this.loadedQuestion.correctAnswer == 1 && b.isChecked()) {
            correct = true;
        }

        if (this.loadedQuestion.correctAnswer == 2 && c.isChecked()) {
            correct = true;
        }

        if (this.loadedQuestion.correctAnswer == 3 && d.isChecked()) {
            correct = true;
        }

        if (correct) {
            quizHistory.successRate++;
        } else {
            quizHistory.wrongAnswered += loadedQuestion.question+"\n";
        }
        loadNextQuestion();
    }
}
