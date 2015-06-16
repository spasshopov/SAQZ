package com.example.voltron.quiz_game;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


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
    private CountDownTimer counter = null;
    private TextView countDownField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        countDownField = (TextView) findViewById(R.id.timerText);
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

            if (counter == null) {
                runTimer();
            }

            quiz.questions = questions;
        } else {
            if (counter != null) {
                counter.cancel();
            }

            if (numberOfQuestions == 0) {
                Toast.makeText(getBaseContext(),
                        "quiz is empty", Toast.LENGTH_LONG)
                        .show();
                quizHistory.successRate = 100;
                quizHistory.wrongAnswered = "No questions in this quiz!";
            } else {
                quizHistory.successRate = (int) ((quizHistory.successRate * 100) / numberOfQuestions);
                Toast.makeText(getBaseContext(),
                        "Success rate: " + quizHistory.successRate + "%\n Wrong: \n" + quizHistory.wrongAnswered, Toast.LENGTH_LONG)
                        .show();

                quizHistory.quizName = quiz.name;
                sendSimpleMessage(quizHistory);
                saveQuizHistory(quizHistory);
            }

            startQuizInfoActivity(quizHistory);
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
        quizHistory.quizName = quiz.name;
        boolean correct = false;
        if (this.loadedQuestion.correctAnswer == 0 && a.isChecked()) {
            a.clearFocus();
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

    private void runTimer() {
        long miliSeconds = quiz.time*60*1000;
        counter = new CountDownTimer(miliSeconds, 1000*60) {

            public void onTick(long millisUntilFinished) {
                String counterText;

                if ((millisUntilFinished / (1000*60)) == 1) {
                    runOneMinuteTimer();
                } else {
                    counterText = (millisUntilFinished / (1000 * 60)) + " minutes remaining...";
                    countDownField.setText(counterText);
                }
            }

            public void onFinish() {
                Toast.makeText(getBaseContext(),
                        "Times up!", Toast.LENGTH_SHORT)
                        .show();
                quizHistory.successRate = (int) ((quizHistory.successRate * 100) / numberOfQuestions);
                quizHistory.quizName = quiz.name;
                startQuizInfoActivity(quizHistory);
                finish();
            }
        }.start();

    }

    private void runOneMinuteTimer() {
        counter.cancel();
        counter = new CountDownTimer(60*1000, 1000) {

            public void onTick(long millisUntilFinished) {
                String counterText;
                counterText = millisUntilFinished / (1000) + " seconds remaining...";
                countDownField.setText(counterText);
            }

            public void onFinish() {
                Toast.makeText(getBaseContext(),
                        "Times up!", Toast.LENGTH_SHORT)
                        .show();
                quizHistory.successRate = (int) ((quizHistory.successRate * 100) / numberOfQuestions);
                quizHistory.quizName = quiz.name;
                saveQuizHistory(quizHistory);
                sendSimpleMessage(quizHistory);
                startQuizInfoActivity(quizHistory);
                finish();
            }
        }.start();
    }

    public void sendSimpleMessage(final QuizHistory quizHistory) {
        Log.d("Sending mail to: ", quiz.creatorEmail);
        class sendMail extends AsyncTask<String, Void, Boolean> {
            @Override
            protected Boolean doInBackground(String... params) {
                // Create a new HttpClient and Post Header
                DefaultHttpClient httpclient = new DefaultHttpClient();

                httpclient.getCredentialsProvider().setCredentials(
                        new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
                        new UsernamePasswordCredentials("api", "key-98bc77afcfee5376f6e5b56267f7ea9b"));

                HttpPost httppost = new HttpPost("https://api.mailgun.net/v3/sandbox7dd69c6cbfc74eb49cb33d1fd570176a.mailgun.org/messages");

                try {
                    // Add your data
                    Log.d("Sending mail 2 to: ", QuizActivity.this.quiz.creatorEmail);
                    Log.d("Quiz taken from: ", QuizActivity.this.user.nickname);
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("from", "SAQZ Sandbox <s.v.shopov@gmail.com>"));
                    nameValuePairs.add(new BasicNameValuePair("to", "Quiz Creator <"+QuizActivity.this.quiz.creatorEmail+">"));
                    nameValuePairs.add(new BasicNameValuePair("subject", QuizActivity.this.user.email+"<"+QuizActivity.this.user.nickname+"> took quiz: "+quizHistory.quizName));
                    nameValuePairs.add(new BasicNameValuePair("text", "Success rate: "+quizHistory.successRate+"%\n"+"Wrong answerd\n: "+quizHistory.wrongAnswered));
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    // Execute HTTP Post Request
                    HttpResponse response = httpclient.execute(httppost);

                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                } catch (IOException e) {
                    // TODO Auto-generated catch block
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

        new sendMail().execute("");

    }
}
