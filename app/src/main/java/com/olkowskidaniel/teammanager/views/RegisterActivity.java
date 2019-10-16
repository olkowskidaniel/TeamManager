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

        final Observer<String> registerViewModelMessageObserver = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d("RegisterActivity", s);
                switch (s){
                    case "startLoginActivity":
                        Toast.makeText(RegisterActivity.this, "Registered successfully", Toast.LENGTH_LONG).show();
                        Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(loginIntent);
                        break;
                    case "PasswordMissmatch":
                        registerFailureMessageTV.setText(R.string.passwordMissmatchMessage);
                        break;
                    case "clearMessageTV":
                        registerFailureMessageTV.setText("");
                        break;
                        default:
                            if (!s.equals("defaultMessage")) {
                                registerFailureMessageTV.setText(s);
                            }
                            break;
                }
            }
        };

        registerViewModel.getRegisterViewModelMessageLiveData().observe(this, registerViewModelMessageObserver);
    }

    @OnClick(R.id.registerSendBtn)
    void registerSendBtnClicked() {
        registerViewModel.onRegisterSendBtnClicked(registerEmailET.getText().toString().trim(), registerPasswordET.getText().toString().trim(), registerRePasswordET.getText().toString().trim());
    }
}
