package com.example.voltron.quiz_game;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;


public class UserChartActivity extends ActionBarActivity {

    private User user;
    private ArrayList<User> allUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chart);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            this.user = (User)bundle.getSerializable("user");
            getAllUsers();
        }else{

        }
    }

    public void getAllUsers(){
        class getAllUsersAsync extends AsyncTask<Void, Void, ArrayList<User>> {
            Db db = new Db();
            @Override
            protected ArrayList<User> doInBackground(Void... params) {
                if(db.init()){
                    try {
                        File file = new File(Environment.getExternalStorageDirectory() + "/SAQZ/" + UserChartActivity.this.user.email + ".usr");
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return db.getAllUsers();
                }
                return  null;
            }

            @Override
            protected void onPostExecute(ArrayList<User> result) {

                if(result != null){
                    allUsers = result;
                    ListView userChartListView = (ListView) findViewById(R.id.userChartList);

                    CustomListAdapter customAdapter = new CustomListAdapter(UserChartActivity.this, R.layout.list_view_item, allUsers,user);

                    userChartListView .setAdapter(customAdapter);

                }else {
                    Toast.makeText(getBaseContext(),
                            "Fail", Toast.LENGTH_LONG)
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

        new getAllUsersAsync().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_chart, menu);
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
}
