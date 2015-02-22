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


public class LoginActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    public void startRegisterActivity(View v){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void logInAction(View V)
    {
        EditText etemail = (EditText) findViewById(R.id.emailtext);
        EditText etpassword = (EditText) findViewById(R.id.passwordtext);

        final String email = etemail.getText().toString();
        final String password = etpassword.getText().toString();
        final User user = new User();

        final Db db = new Db();
        class login extends AsyncTask<String, Void, Boolean> {
            @Override
            protected Boolean doInBackground(String... params) {
                ResultSet result = null;
                if(db.init()){
                    result = db.getUser(email,password);
                    try {
                        while (result.next()) {
                            user.email = result.getString("username");
                            user.id = result.getInt("id");
                            user.nickname = result.getString("nickname");
                            user.points = result.getInt("points");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();

                        return false;
                    }
                }

                return true;
            }

            @Override
            protected void onPostExecute(Boolean result) {

                if(result){
                    Toast.makeText(getBaseContext(),
                            "Logged in", Toast.LENGTH_LONG)
                            .show();

                    Toast.makeText(getBaseContext(),
                            "User: "+user.nickname+" Points: "+user.points, Toast.LENGTH_LONG)
                            .show();

                    //open some menu activity with options
                }else {
                    Toast.makeText(getBaseContext(),
                            "Not logged in", Toast.LENGTH_LONG)
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

        new login().execute("");
    }
}
