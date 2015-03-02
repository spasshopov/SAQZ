package com.example.voltron.quiz_game;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Voltron on 2.3.2015 г..
 */
public class CustomListAdapter extends ArrayAdapter<User> {

    private User loggeduser;

    public CustomListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public CustomListAdapter(Context context, int resource, ArrayList<User> items, User user) {
        super(context, resource, items);
        this.loggeduser = user;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {

            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_view_item, null);

        }

        User user = getItem(position);

        if (user != null) {

            if(loggeduser.id == user.id){
                LinearLayout ll = (LinearLayout) v.findViewById(R.id.row);
                ll.setBackgroundColor(Color.rgb(200,200,200));
            }
            TextView nickname = (TextView) v.findViewById(R.id.nickname_tv);
            TextView points = (TextView) v.findViewById(R.id.points_tv);

            if (nickname != null) {
                nickname.setText(user.nickname+"");
            }
            if (points != null) {
                points.setText(user.points+"");
            }
        }

        return v;

    }
}
