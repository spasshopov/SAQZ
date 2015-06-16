package com.example.voltron.quiz_game;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class QuizInfoActivity extends ActionBarActivity {

    private User user;
    private QuizHistory quizHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_info);
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            this.user = (User) bundle.getSerializable("user");
            this.quizHistory = (QuizHistory) bundle.getSerializable("quizHistory");

            TextView quizText = (TextView) findViewById(R.id.quiz);
            TextView rateText = (TextView) findViewById(R.id.rate);
            TextView wrongText = (TextView) findViewById(R.id.wrong);

            quizText.setText(quizHistory.quizName);
            rateText.setText(quizHistory.successRate+"%");
            wrongText.setText(quizHistory.wrongAnswered);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz_info, menu);
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

    public void startOptionsActivity(View v) {
        Intent intent = new Intent(this, OptionsActivity.class);
        Bundle bundle = new Bundle();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        bundle.putSerializable("user", user);
        intent.putExtras(bundle);
        finish();
        startActivity(intent);
    }
}
