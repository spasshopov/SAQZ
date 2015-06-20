package com.example.voltron.quiz_game;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class ChangePasswordActivity extends ActionBarActivity {

    private User user = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            this.user = (User)bundle.getSerializable("user");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_change_password, menu);
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

    public void changePassword(View v) {
        EditText etpassword = (EditText) findViewById(R.id.passwordtext);
        EditText etpasswordConfirm = (EditText) findViewById(R.id.passwordconfirm );

        final String password = etpassword.getText().toString();
        final String passwordConfirm = etpasswordConfirm.getText().toString();

        if (!password.equals(passwordConfirm)) {
            Toast.makeText(getBaseContext(),
                    "Password doesn't match!", Toast.LENGTH_LONG)
                    .show();
            return;
        }

        final Db db = new Db();
        class changePassword extends AsyncTask<String, Void, Boolean> {
            @Override
            protected Boolean doInBackground(String... params) {
                boolean result = false;
                try {
                    if (db.init()) {
                        db.setNewPassword(user.email, password);
                    } else {
                        return false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }

                return true;
            }

            @Override
            protected void onPostExecute(Boolean result) {

                if(result){
                    Toast.makeText(getBaseContext(),
                            "Password changed", Toast.LENGTH_LONG)
                            .show();
                    ChangePasswordActivity.this.startOptionsActivity();
                }else {
                    Toast.makeText(getBaseContext(),
                            "Password not changed", Toast.LENGTH_LONG)
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

        new changePassword().execute("");
    }

    public void startOptionsActivity() {
        Intent intent = new Intent(this, OptionsActivity.class);
        Bundle bundle = new Bundle();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        bundle.putSerializable("user", user);
        intent.putExtras(bundle);
        finish();
        startActivity(intent);
    }
}
