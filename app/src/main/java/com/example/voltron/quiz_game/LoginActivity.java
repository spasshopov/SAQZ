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
import java.util.Random;


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

    public void startOptionsActivity(User user) {
        Intent intent = new Intent(this, OptionsActivity.class);
        Bundle bundle = new Bundle();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        bundle.putSerializable("user", user);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void logInAction(View V)
    {
        EditText etemail = (EditText) findViewById(R.id.emailtext);
        EditText etpassword = (EditText) findViewById(R.id.passwordtext);

        final String email = etemail.getText().toString();
        final String password = etpassword.getText().toString();

        final Db db = new Db();
        class login extends AsyncTask<String, Void, User> {
            @Override
            protected User doInBackground(String... params) {
                ResultSet result = null;
                User user = null;
                if(db.init()){
                    result = db.getUser(email,password);
                    try {
                        while (result.next()) {
                            user = new User();
                            user.email = result.getString("username");
                            user.id = result.getInt("id");
                            user.nickname = result.getString("nickname");
                            user.points = result.getInt("points");
                            user.number_answered = result.getInt("number_answered");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                    }
                }

                return user;
            }

            @Override
            protected void onPostExecute(User user) {

                if(user!=null){
                    if( user.email != null && user.nickname!=null) {
                        LoginActivity.this.startOptionsActivity(user);
                    }else{
                        Toast.makeText(getBaseContext(),
                                "Something Went Wrong!", Toast.LENGTH_LONG)
                                .show();
                    }
                }else {
                    Toast.makeText(getBaseContext(),
                            "Wrong username or password!", Toast.LENGTH_LONG)
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

    public void sendNewPasswordAction(View v) {
        EditText etemail = (EditText) findViewById(R.id.emailtext);
        final String email = etemail.getText().toString();

        final Db db = new Db();
        class checkUser extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                ResultSet result = null;
                String emailFromDb = null;
                if(db.init()){
                    result = db.checkUserEmail(email);
                    try {
                        while (result.next()) {
                            emailFromDb = result.getString("username");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                    }
                }

                return emailFromDb;
            }

            @Override
            protected void onPostExecute(String email) {

                if(email!=null){
                    sendNewPasswordTo(email);
                }else {
                    Toast.makeText(getBaseContext(),
                            "Wrong username!", Toast.LENGTH_LONG)
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

        new checkUser().execute("");
    }

    private void sendNewPasswordTo(final String email) {
        final  String newPassword = generateString(new Random(), "abcdefghklmnoprtyusiapznq1234567890-=", 8);
        sendPasswordMail(email, newPassword);

        final Db db = new Db();
        class saveNewPassword extends AsyncTask<String, Void, Boolean> {
            @Override
            protected Boolean doInBackground(String... params) {
                ResultSet result = null;

                if(db.init()){
                    try {
                        db.setNewPassword(email, newPassword);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                }

                return true;
            }

            @Override
            protected void onPostExecute(Boolean success) {

                if(success){
                    Toast.makeText(getBaseContext(),
                            "Password sent!", Toast.LENGTH_LONG)
                            .show();
                }else {
                    Toast.makeText(getBaseContext(),
                            "Password not sent!", Toast.LENGTH_LONG)
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

        new saveNewPassword().execute("");
    }

    public static String generateString(Random rng, String characters, int length)
    {
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }

    public void sendPasswordMail(final String email, final String password) {
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
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("from", "SAQZ Sandbox <s.v.shopov@gmail.com>"));
                    nameValuePairs.add(new BasicNameValuePair("to", "User <"+email+">"));
                    nameValuePairs.add(new BasicNameValuePair("subject", "New password request."));
                    nameValuePairs.add(new BasicNameValuePair("text", "Your new password is: "+password));
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
