package com.example.voltron.quiz_game;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class RegisterActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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

    public void registerUser(View v){
        EditText etemail = (EditText) findViewById(R.id.emailtext);
        EditText etnickname = (EditText) findViewById(R.id.nicknametext);
        EditText etpassword = (EditText) findViewById(R.id.passwordtext);
        EditText etpasswordConfirm = (EditText) findViewById(R.id.passwordconfirm );

        final String email = etemail.getText().toString();
        final String nickname = etnickname.getText().toString();
        final String password = etpassword.getText().toString();
        final String passwordConfirm = etpasswordConfirm.getText().toString();

        if (!password.equals(passwordConfirm)) {
            Toast.makeText(getBaseContext(),
                    "Password doesn't match!", Toast.LENGTH_LONG)
                    .show();
            return;
        }

        final Db db = new Db();
        class register extends AsyncTask<String, Void, Boolean> {
            @Override
            protected Boolean doInBackground(String... params) {
                boolean result = false;
                if(db.init()){
                     result = db.createUser(email,nickname,password);
                };

                return result;
            }

            @Override
            protected void onPostExecute(Boolean result) {

                if(result){
                    Toast.makeText(getBaseContext(),
                            "User created", Toast.LENGTH_LONG)
                            .show();
                }else {
                    Toast.makeText(getBaseContext(),
                            "User NOT created", Toast.LENGTH_LONG)
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

        new register().execute("");
    }


}
