package com.olkowskidaniel.teammanager.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.olkowskidaniel.teammanager.R;
import com.olkowskidaniel.teammanager.utils.Activities;
import com.olkowskidaniel.teammanager.viewmodels.LoginViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private LoginViewModel loginViewModel;

    @BindView(R.id.loginEmailET)
    EditText loginEmailET;
    @BindView(R.id.loginPasswordET)
    EditText loginPasswordET;
    @BindView(R.id.loginSendBtn)
    Button loginSendBtn;
    @BindView(R.id.loginRegisterBtn)
    Button loginRegisterButton;
    @BindView(R.id.loginFailureMessageTV)
    TextView loginFailureMessageTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        loginViewModel.getIsUserLoggedLiveData().observe(this, bool -> loginViewModel.isUserLogged(bool));

        loginViewModel.getStartActivityEvent().observe(this, this::startActivityRequest);

        loginViewModel.getLoginFailureMessageLiveData().observe(this, message -> loginFailureMessageTV.setText(message));
        loginFailureMessageTV.setText("");
    }

    @OnClick(R.id.loginSendBtn)
    void loginSendBtnClicked() {
        String email = loginEmailET.getText().toString().trim();
        String password = loginPasswordET.getText().toString().trim();
        if(email.trim().length() == 0 || password.trim().length() == 0) {
            Toast.makeText(this, "Please, fill all the fields", Toast.LENGTH_SHORT).show();
        } else {
            loginViewModel.onLoginSendButtonClicked(email, password);
        }
    }

    @OnClick(R.id.loginRegisterBtn)
    void loginRegisterBtnClicked() {
        loginViewModel.onLoginRegisterButtonClicked();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Activity destroyed");
    }

    private void startActivityRequest(Activities activityName) {
        switch (activityName) {
            case Main:
                Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(mainIntent);
                break;
            case Register:
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                break;
        }
    }
}
