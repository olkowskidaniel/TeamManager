package com.olkowskidaniel.teammanager.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.olkowskidaniel.teammanager.R;
import com.olkowskidaniel.teammanager.viewmodels.LoginViewModel;
import com.olkowskidaniel.teammanager.views.base.BaseActivity;

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

        //TODO: get email from firebaselogin

        final Observer<String> loginViewModelMessageObserver = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.equals("startBaseActivity")) {
                    loginFailureMessageTV.setText("");
                    Intent baseIntent = new Intent(LoginActivity.this, BaseActivity.class);
                    startActivity(baseIntent);
                }

                if(s.equals("startRegisterActivity")) {
                    Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(registerIntent);
                }
            }
        };

        final Observer<String> firebaseMessageObserver = new Observer<String>() {
            @Override
            public void onChanged(String s) {
            }
        };

        final Observer<String> firebaseFailureMessageObserver = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(!s.equals("defaultMessage")) {
                    loginFailureMessageTV.setText(s);
                }
            }
        };


        loginViewModel.getLoginViewModelMessage().observe(this, loginViewModelMessageObserver);
        loginViewModel.getFirebaseMessageLiveData().observe(this, firebaseMessageObserver);
        loginViewModel.getFirebaseFailureMesageLiveData().observe(this, firebaseFailureMessageObserver);
    }

    @OnClick(R.id.loginSendBtn)
    void loginSendBtnClicked() {
        loginViewModel.onLoginSendButtonClicked(loginEmailET.getText().toString().trim(), loginPasswordET.getText().toString().trim());
    }
    @OnClick(R.id.loginRegisterBtn)
    void loginRegisterBtnClicked() {
        loginViewModel.onLoginRegisterButtonClicked();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginViewModel.onActivityDestroy();
    }
}
