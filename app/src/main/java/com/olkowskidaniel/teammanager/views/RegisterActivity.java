package com.olkowskidaniel.teammanager.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.olkowskidaniel.teammanager.R;
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
        registerFailureMessageTV.setText("");
    }

    @OnClick(R.id.registerSendBtn)
    void registerSendBtnClicked() {
        Log.d("RegisterActivity", "Register button clicked");
        registerViewModel.onRegisterSendBtnClicked(registerEmailET.getText().toString().trim(), registerPasswordET.getText().toString().trim(), registerRePasswordET.getText().toString().trim());
    }
}