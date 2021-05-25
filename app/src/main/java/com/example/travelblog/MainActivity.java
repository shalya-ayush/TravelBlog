package com.example.travelblog;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    private TextInputLayout textUserNameLayout;
    private TextInputLayout textPasswordLayout;
    private Button loginButton;
    private ProgressBar progressBar;
    private  BlogPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = new BlogPreferences(this);
        if(preferences.isLoggedIn()){
            startAnotherActivity();
            finish();
            return;
        }
        setContentView(R.layout.activity_main);
        textUserNameLayout = findViewById(R.id.textUserNameLayout);
        textPasswordLayout = findViewById(R.id.textPasswordLayout);
        progressBar = findViewById(R.id.progressBar);
        textUserNameLayout.getEditText().addTextChangedListener(createTextWatcher(textUserNameLayout));
        textPasswordLayout.getEditText().addTextChangedListener(createTextWatcher(textPasswordLayout));
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(view -> onLoginClicked());
    }

    private TextWatcher createTextWatcher(TextInputLayout textUserNameLayout) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textUserNameLayout.setError(null);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }

    private void onLoginClicked() {
        String username = textUserNameLayout.getEditText().getText().toString();
        String password = textPasswordLayout.getEditText().getText().toString();
        if (username.isEmpty()) {
            textUserNameLayout.setError("User name can't be Empty");
        } else if (password.isEmpty() || password.length() < 6) {
            textPasswordLayout.setError("Password must be of 6 characters");
        } else if (!username.equals("admin") || !password.equals("123456")) {
            showErrorDialog();
        } else {
            performLogin();
        }
    }

    private void showErrorDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Login Failed")
                .setMessage("Username or Password Mismatched")
                .setPositiveButton("OK", ((dialogInterface, i) -> dialogInterface.dismiss()))
                .show();
    }

    private void performLogin() {
        preferences.setLoggedIn(true);
        textUserNameLayout.setEnabled(false);
        textPasswordLayout.setEnabled(false);
        loginButton.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        Handler handler = new Handler();
        handler.postDelayed(() ->{
            startAnotherActivity();
            finish();

        },2000);
    }

    private void startAnotherActivity() {
        Intent i = new Intent(this,SecondActivity.class);
        startActivity(i);
    }


}