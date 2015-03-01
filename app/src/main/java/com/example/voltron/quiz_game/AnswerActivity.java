package com.example.voltron.quiz_game;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


public class AnswerActivity extends ActionBarActivity {

    private User user;

    private Db db = new Db();

    private RadioButton a;
    private RadioButton b;
    private RadioButton c;
    private RadioButton d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            this.user = (User) bundle.getSerializable("user");
            
            TextView a = (TextView) findViewById(R.id.answer_row_a);
            a.setText("test");

            TextView b = (TextView) findViewById(R.id.answer_row_b);
            b.setText("test 1");

            TextView c = (TextView) findViewById(R.id.answer_row_c);
            c.setText("test 2");

            TextView d = (TextView) findViewById(R.id.answer_row_d);
            d.setText("test 3");

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
                a.setChecked(true);
            }
        });
    }

    public void answerQuestion(View v) {
        Toast.makeText(getBaseContext(),
                "A: " + a.isChecked(), Toast.LENGTH_LONG)
                .show();
    }
}
