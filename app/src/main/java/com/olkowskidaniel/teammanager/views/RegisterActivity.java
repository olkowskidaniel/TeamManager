package com.olkowskidaniel.teammanager.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
    }

    @OnClick(R.id.registerSendBtn)
    void registerSendBtnClicked() {
        registerViewModel.onRegisterSendBtnClicked(registerEmailET.getText().toString().trim(), registerPasswordET.getText().toString().trim(), registerRePasswordET.getText().toString().trim());
    }
}
