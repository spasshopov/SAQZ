package com.example.voltron.quiz_game;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


public class CreateActivity extends ActionBarActivity {

    private User user = null;
    private RadioButton a;
    private RadioButton b;
    private RadioButton c;
    private RadioButton d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            this.user = (User)bundle.getSerializable("user");
            Toast.makeText(getBaseContext(),
                    "Id: " + this.user.id + " Email: " + this.user.email + " User: " + this.user.nickname + " Points: " + this.user.points, Toast.LENGTH_LONG)
                    .show();
        }

        initiateRadioButtons();
    }


    public void initiateRadioButtons(){
        a = (RadioButton) findViewById(R.id.correct_a);
        b = (RadioButton) findViewById(R.id.correct_b);
        c = (RadioButton) findViewById(R.id.correct_c);
        d = (RadioButton) findViewById(R.id.correct_d);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create, menu);
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

    public void saveQuestion(View v){

        EditText question = (EditText) findViewById(R.id.questionText);
        EditText ansA = (EditText) findViewById(R.id.answer_a);
        EditText ansB = (EditText) findViewById(R.id.answer_b);
        EditText ansC = (EditText) findViewById(R.id.answer_c);
        EditText ansD = (EditText) findViewById(R.id.answer_d);

        final String questionText = question.getText().toString();
        final String answerA = ansA.getText().toString();
        final String answerB = ansB.getText().toString();
        final String answerC = ansC.getText().toString();
        final String answerD = ansD.getText().toString();

        class saveNewQuestion extends AsyncTask<Void, Void, Boolean> {
            @Override
            protected Boolean doInBackground(Void... params) {
                final Db db = new Db();
                boolean result = false;
                if(db.init()){
                    try {
                        int qId = db.createQuestion(questionText, user.id);
                        result = result || db.createAnswer(answerA,qId,a.isChecked(),0);
                        result = result && db.createAnswer(answerB,qId,b.isChecked(),1);
                        result = result && db.createAnswer(answerC,qId,c.isChecked(),2);
                        result = result && db.createAnswer(answerD,qId,d.isChecked(),3);
                    }catch (Exception e){
                        e.printStackTrace();
                        result = false;
                    }
                }

                return result;
            }

            @Override
            protected void onPostExecute(Boolean result) {

                if(result){
                    Toast.makeText(getBaseContext(),
                            "Question created", Toast.LENGTH_LONG)
                            .show();
                    finish();
                }else {
                    Toast.makeText(getBaseContext(),
                            "Question NOT created", Toast.LENGTH_LONG)
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

        new saveNewQuestion().execute();
    }
}
