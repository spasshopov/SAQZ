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
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class OptionsActivity extends ActionBarActivity {

    private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            this.user = (User)bundle.getSerializable("user");
            Toast.makeText(getBaseContext(),
                    "Id: " + this.user.id + " Email: " + this.user.email + " User: " + this.user.nickname + " Points: " + this.user.points, Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_options, menu);
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

    public void startCreateActivity(View v) {
        Intent intent = new Intent(this, CreateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void startAnswerActivity(View v) {
        Intent intent = new Intent(this, AnswerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    public void startUserChartActivity(View v) {
        Intent intent = new Intent(this, UserChartActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void prepareForOffline(View v) {
        final Db db = new Db();
        class prepareOfflineQuestions extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                Question question = null;
                User user = null;
                if(db.init()){
                    for (int i=0; i<2; i++) {
                        question = db.getQuestionForUser(OptionsActivity.this.user);
                        if (question == null && i==0) {
                            return "No more questions available!";
                        } else if(question == null) {
                            return "All questions cached!";
                        }
                        db.correctAnswerUpdate(OptionsActivity.this.user, question);
                        try {
                            File folder = new File(Environment.getExternalStorageDirectory() + "/SAQZ/"+OptionsActivity.this.user.email+"/"+question.id);
                            if (folder.mkdirs() ) {
                                File file = new File(Environment.getExternalStorageDirectory() + "/SAQZ/" + OptionsActivity.this.user.email + "/" + question.id + "/" + question.id + ".qsn");
                                file.createNewFile();
                                FileOutputStream fileOut = new FileOutputStream(file);
                                ObjectOutputStream questionStream = new ObjectOutputStream(fileOut);
                                questionStream.writeObject(question);
                                questionStream.close();
                                fileOut.close();
                            } else {
                                Log.d("Path", "Path: No path created");
                                return "Can't create cache, device incompatibility!";
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            return "Can't create cache, device incompatibility!";
                        }
                    }
                }

                return "Ready to play offline, when no connection is available!";
            }

            @Override
            protected void onPostExecute(String status) {
                Toast.makeText(getBaseContext(),
                        status, Toast.LENGTH_LONG)
                        .show();
            }

            @Override
            protected void onPreExecute() {
            }

            @Override
            protected void onProgressUpdate(Void... values) {
            }
        }

        new prepareOfflineQuestions().execute("");
    }
}
