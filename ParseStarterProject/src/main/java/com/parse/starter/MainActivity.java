/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

    boolean logInModeActive = true;
    TextView textView;
    EditText usernameEditText;
    EditText passwordEditText;
    ImageView logo;
    ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        textView.setOnClickListener(this);

        passwordEditText = findViewById(R.id.passwordEditText);
        passwordEditText.setOnKeyListener(this);

        logo = findViewById(R.id.logoImageView);
        logo.setOnClickListener(this);

        layout = findViewById(R.id.constraintLayout);
        layout.setOnClickListener(this);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.textView) {
            Button logInButton = findViewById(R.id.button);

            if (logInModeActive) {
                logInModeActive = false;
                logInButton.setText("Sign Up");
                textView.setText("Already have an account? Login");
            } else {
                logInModeActive = true;
                logInButton.setText("Login");
                textView.setText("Create an account anonymously");
            }

        } else if (view.getId() == R.id.logoImageView || view.getId() == R.id.constraintLayout) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void logIn(View view) {
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (username.matches("") || password.matches("")) {
            Toast.makeText(this, "A username and password are required", Toast.LENGTH_LONG).show();
        } else if (logInModeActive = false) {
            ParseUser user = new ParseUser();
            user.setUsername(username);
            user.setPassword(password);

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Log.i("Signup", "Success");
                    } else {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            ParseUser.logInInBackground(username, password, new LogInCallback() {
                @Override
                public void done(ParseUser parseUser, ParseException e) {
                    if (parseUser != null) {
                        Log.i("Login", "ok!");
                    } else {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {

        if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            logIn(view);
        }

        return false;
    }
}