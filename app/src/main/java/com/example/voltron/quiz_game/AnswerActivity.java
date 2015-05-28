package com.example.voltron.quiz_game;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class AnswerActivity extends ActionBarActivity {

    private User user;

    private Db db = new Db();

    private RadioButton a;
    private RadioButton b;
    private RadioButton c;
    private RadioButton d;
    private Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            this.user = (User) bundle.getSerializable("user");
            this.question = new Question();

            TextView _q = (TextView) findViewById(R.id.questionText);
            TextView _a_answer = (TextView) findViewById(R.id.answer_row_a);
            TextView _b_answer = (TextView) findViewById(R.id.answer_row_b);
            TextView _c_answer = (TextView) findViewById(R.id.answer_row_c);
            TextView _d_answer = (TextView) findViewById(R.id.answer_row_d);

            class GetQuestion extends AsyncTask<Void, Void, Boolean> {
                private TextView q;
                private TextView a_answer;
                private TextView b_answer;
                private TextView c_answer;
                private TextView d_answer;

                private GetQuestion(TextView _q, TextView _a_answer, TextView _b_answer, TextView _c_answer, TextView _d_answer) {
                    this.q = _q;
                    this.a_answer = _a_answer;
                    this.b_answer = _b_answer;
                    this.c_answer = _c_answer;
                    this.d_answer = _d_answer;
                }
                @Override
                protected Boolean doInBackground(Void... params) {
                    final Db db = new Db();
                    boolean result = false;
                    if (db.init()) {
                        Log.d("Answer: ", "Getting from db");
                        try {
                            Log.d("User: ", "Try to update");
                            File file = new File(Environment.getExternalStorageDirectory() + "/SAQZ/" + AnswerActivity.this.user.email + ".usr");
                            if (file.isFile()) {
                                Log.d("User file:", file.getName());
                                FileInputStream fileIn = new FileInputStream(file);
                                ObjectInputStream in = new ObjectInputStream(fileIn);
                                User updateUser = (User) in.readObject();
                                db.updateUserPoints(updateUser);
                                file.delete();
                                in.close();
                                fileIn.close();
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            Log.d("Answer init: ", "IOE Exception");

                        } catch (ClassNotFoundException c) {
                            c.printStackTrace();
                            Log.d("Answer init: ", "Class not found Exception");

                        } catch (Exception sqlEx) {
                            Log.d("Answer init: ", "Exception");
                        }

                        try {
                            question = db.getQuestionForUser(user);
                            if(question == null){
                                Log.d("Answer: ", "Getting cached");
                                File folder = new File(Environment.getExternalStorageDirectory() + "/SAQZ/"+AnswerActivity.this.user.email);
                                File[] files = folder.listFiles();

                                for (int i = 0; i < files.length; i++) {
                                    if (files[i].isDirectory()) {
                                        try {
                                            File questionFile = new File(files[i].getAbsolutePath()+"/"+files[i].getName()+".qsn");
                                            FileInputStream fileIn = new FileInputStream(questionFile);
                                            ObjectInputStream in = new ObjectInputStream(fileIn);
                                            question = (Question) in.readObject();
                                            questionFile.delete();
                                            files[i].delete();
                                            in.close();
                                            fileIn.close();
                                            result = true;
                                            break;
                                        } catch (IOException ex) {
                                            ex.printStackTrace();
                                            return null;
                                        } catch (ClassNotFoundException c) {
                                            c.printStackTrace();
                                            return null;
                                        }
                                    }
                                }
                            } else {
                                result = true;
                            }
                        }catch (Exception e){
                            result = false;
                            Toast.makeText(getBaseContext(),
                                    "Failure!", Toast.LENGTH_LONG)
                                    .show();
                        }
                    } else {
                        Log.d("Answer: ", "Getting cached");
                        File folder = new File(Environment.getExternalStorageDirectory() + "/SAQZ/"+AnswerActivity.this.user.email);
                        File[] files = folder.listFiles();

                        for (int i = 0; i < files.length; i++) {
                            if (files[i].isDirectory()) {
                                try {
                                    File questionFile = new File(files[i].getAbsolutePath()+"/"+files[i].getName()+".qsn");
                                    FileInputStream fileIn = new FileInputStream(questionFile);
                                    ObjectInputStream in = new ObjectInputStream(fileIn);
                                    question = (Question) in.readObject();
                                    questionFile.delete();
                                    files[i].delete();
                                    in.close();
                                    fileIn.close();
                                    result = true;
                                    break;
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                    return null;
                                } catch (ClassNotFoundException c) {
                                    c.printStackTrace();
                                    return null;
                                }
                            }
                        }
                    }

                    return result;
                }

                @Override
                protected void onPostExecute(Boolean result) {
                    if (!result){
                        Toast.makeText(getBaseContext(),
                                "No more question available!", Toast.LENGTH_LONG)
                                .show();
                        goToOptionsActivity();
                    } else {
                        this.q.setText(question.question);
                        this.a_answer.setText(question.answer[0]);
                        this.b_answer.setText(question.answer[1]);
                        this.c_answer.setText(question.answer[2]);
                        this.d_answer.setText(question.answer[3]);
                    }
                }

                @Override
                protected void onPreExecute() {
                }

                @Override
                protected void onProgressUpdate(Void... values) {
                }
            }

            new GetQuestion(_q, _a_answer, _b_answer, _c_answer, _d_answer).execute();
            initiateRadioButtons();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_answer, menu);
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
        class AnswerQuestion extends AsyncTask<Void, Void, Boolean> {

            @Override
            protected Boolean doInBackground(Void... params) {
                final Db db = new Db();
                boolean result = false;
                if(db.init()){
                    try {
                        if (question.correctAnswer == 0 && a.isChecked()) {
                            user.points = user.points+1;
                            db.correctAnswerUpdate(user, question);
                            return true;
                        }

                        if (question.correctAnswer == 1 && b.isChecked()) {
                            user.points = user.points+1;
                            db.correctAnswerUpdate(user, question);
                            return true;
                        }

                        if (question.correctAnswer == 2 && c.isChecked()) {
                            user.points = user.points+1;
                            db.correctAnswerUpdate(user, question);
                            return true;
                        }

                        if (question.correctAnswer == 3 && d.isChecked()) {
                            user.points = user.points+1;
                            db.correctAnswerUpdate(user, question);
                            return true;
                        }
                        db.falseAnswerUpdate(user, question);
                        return false;

                    }catch (Exception e){
                        e.printStackTrace();
                        result = false;
                    }
                } else {
                    Log.d("Offline answering", "Answer offline");
                    if (question.correctAnswer == 0 && a.isChecked()) {
                        user.points = user.points+1;
                        result = true;
                    }

                    if (question.correctAnswer == 1 && b.isChecked()) {
                        user.points = user.points+1;
                        result = true;
                    }

                    if (question.correctAnswer == 2 && c.isChecked()) {
                        user.points = user.points+1;
                        result = true;
                    }

                    if (question.correctAnswer == 3 && d.isChecked()) {
                        user.points = user.points+1;;
                        result = true;
                    }
                    try {
                        File file = new File(Environment.getExternalStorageDirectory() + "/SAQZ/" + AnswerActivity.this.user.email + ".usr");
                        if(!file.createNewFile()) {
                            Log.d("User file", "Not created");
                        }
                        FileOutputStream fileOut = new FileOutputStream(file);
                        ObjectOutputStream userStream = new ObjectOutputStream(fileOut);
                        userStream.writeObject(user);
                        userStream.close();
                        fileOut.close();
                    } catch (IOException er) {
                            er.printStackTrace();
                            return false;
                    }
                }

                return result;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if(!result) {
                    Toast.makeText(getBaseContext(),
                            "Wrong Answer!", Toast.LENGTH_LONG)
                            .show();
                }
                restartMe();
            }

            @Override
            protected void onPreExecute() {
            }

            @Override
            protected void onProgressUpdate(Void... values) {
            }
        }

        new AnswerQuestion().execute();
    }

    public void reportQuestion(View v) {
        class ReportQuestion extends AsyncTask<Void, Void, Boolean> {

            @Override
            protected Boolean doInBackground(Void... params) {
                final Db db = new Db();
                boolean result = true;
                if(db.init()){
                    try {
                        question.reports++;
                        db.falseAnswerUpdate(user, question);
                        db.reportQuestion(question);
                        return true;
                    }catch (Exception e){
                        e.printStackTrace();
                        result = false;
                    }
                }
                return result;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if(result) {
                    Toast.makeText(getBaseContext(),
                            "Question reported!", Toast.LENGTH_LONG)
                            .show();
                }
                restartMe();
            }

            @Override
            protected void onPreExecute() {
            }

            @Override
            protected void onProgressUpdate(Void... values) {
            }
        }

        new ReportQuestion().execute();
    }

    private void restartMe()
    {
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        intent.putExtras(bundle);
        finish();
        startActivity(intent);
    }

    public void goToOptionsActivity(){
        Intent intent = new Intent(this, OptionsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        intent.putExtras(bundle);
        finish();
        startActivity(intent);
    }
}
