package com.parse.starter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        ListView listView = findViewById(R.id.userListView);
        ArrayList<String> usernames = new ArrayList<String>();
        usernames.add("test bitch");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, usernames);
        listView.setAdapter(arrayAdapter);

        ParseQuery<ParseUser> query = ParseUser.getQuery();

        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
    }
}
