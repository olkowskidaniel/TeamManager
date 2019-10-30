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
import com.olkowskidaniel.teammanager.viewmodels.RegisterViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    private RegisterViewModel registerViewModel;

    @BindView(R.id.registerEmailET)
    EditText registerEmailET;
    @BindView(R.id.registerPasswordET)
    EditText registerPasswordET;
    @BindView(R.id.registerRePasswordET)
    EditText registerRePasswordET;
    @BindView(R.id.registerSendBtn)
    Button registerSendBtn;
    @BindView(R.id.registerFailureMessageTV)
    TextView registerFailureMessageTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);

        registerViewModel.getRegisterFailureMessageLiveData().observe(this, message -> registerFailureMessageTV.setText(message));
        registerViewModel.getUserRegisteredEventLiveData().observe(this, bool -> {
            if(bool) {
                registerViewModel.onUserRegisterSuccess();
                Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_LONG).show();
            }
        });
        registerViewModel.getStartActivityEvent().observe(this, this::startActivityRequest);
    }

    @OnClick(R.id.registerSendBtn)
    void registerSendBtnClicked() {
        Log.d("RegisterActivity", "Register button clicked");
        registerViewModel.onRegisterSendBtnClicked(registerEmailET.getText().toString().trim(), registerPasswordET.getText().toString().trim(), registerRePasswordET.getText().toString().trim());
    }

    private void startActivityRequest(Activities activityName) {
        switch (activityName) {
            case Main:
                Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(mainIntent);
                break;
        }
    }
}