package com.example.voltron.quiz_game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class QuizOptionsActivity extends ActionBarActivity {

    private User user = null;
    private Quiz quiz = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_options);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            this.user = (User)bundle.getSerializable("user");
            this.quiz = (Quiz)bundle.getSerializable("quiz");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz_options, menu);
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

    public void startCreateQuestionAction(View v) {
        Intent intent = new Intent(this, CreateQuizQuestionActivity.class);
        Bundle bundle = new Bundle();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        bundle.putSerializable("user", user);
        bundle.putSerializable("quiz", quiz);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void startDoneAction (View v) {
        Intent intent = new Intent(this, OptionsActivity.class);
        Bundle bundle = new Bundle();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        bundle.putSerializable("user", user);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
