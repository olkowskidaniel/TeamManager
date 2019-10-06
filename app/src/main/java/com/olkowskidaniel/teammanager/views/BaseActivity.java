package com.olkowskidaniel.teammanager.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.olkowskidaniel.teammanager.R;
import com.olkowskidaniel.teammanager.viewmodels.BaseViewModel;

import org.w3c.dom.Text;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BaseActivity extends AppCompatActivity {

    private BaseViewModel baseViewModel;

    @BindView(R.id.baseUserEmailTV)
    TextView baseUserEmailTV;
    @BindView(R.id.baseLogoutBtn)
    Button baseLogoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ButterKnife.bind(this);
        baseUserEmailTV.setText(getIntent().getExtras().getString("userEmail"));
        baseViewModel = ViewModelProviders.of(this).get(BaseViewModel.class);

        final Observer<String> baseViewModelMessageObserver = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.equals("startMainActivity")) {
                    Intent mainIntent = new Intent(BaseActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                }
            }
        };

        baseViewModel.getBaseViewModelMessage().observe(this, baseViewModelMessageObserver);
    }

    @Override
    protected void onStart() {
        super.onStart();
        baseViewModel.onActivityStarted();
    }

    @OnClick(R.id.baseLogoutBtn)
    void onLogoutButtonClicked() {
        baseViewModel.onLogoutButtonClicked();
    }
}
