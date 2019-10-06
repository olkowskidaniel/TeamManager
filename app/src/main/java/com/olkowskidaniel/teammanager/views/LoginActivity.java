package com.olkowskidaniel.teammanager.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.olkowskidaniel.teammanager.R;
import com.olkowskidaniel.teammanager.viewmodels.LoginViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @BindView(R.id.loginEmailET)
    EditText loginEmailET;
    @BindView(R.id.loginPasswordET)
    EditText loginPasswordET;
    @BindView(R.id.loginSendBtn)
    Button loginSendBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        final Observer<String> loginViewModelMessageObserver = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.equals("userLoginFailure")) {
                    loginSendBtn.setBackgroundColor(Color.RED);
                }
            }
        };

        final Observer<String> firebaseLoginMessageObserver = new Observer<String>() {
            @Override
            public void onChanged(String s) {
            }
        };

        final Observer<String> firebaseLoginFailureMessageObserver = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d("LoginViewModel", s);
                if(!s.equals("defaultMessage")) {
                    loginSendBtn.setText(s);
                }
            }
        };

        loginViewModel.getLoginViewModelMessage().observe(this, loginViewModelMessageObserver);
        loginViewModel.getFirebaseLoginMessageLiveData().observe(this, firebaseLoginMessageObserver);
        loginViewModel.getFirebaseLoginFailureMesageLiveData().observe(this, firebaseLoginFailureMessageObserver);
    }

    @OnClick(R.id.loginSendBtn)
    void onLoginSendBtnClicked() {
        loginViewModel.onLoginSendButtonClicked(loginEmailET.getText().toString().trim(), loginPasswordET.getText().toString().trim());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginViewModel.onActivityDestroy();
    }
}
