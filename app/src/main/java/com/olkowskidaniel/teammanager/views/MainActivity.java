package com.olkowskidaniel.teammanager.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.olkowskidaniel.teammanager.R;
import com.olkowskidaniel.teammanager.utils.Activities;
import com.olkowskidaniel.teammanager.viewmodels.MainViewModel;
import com.olkowskidaniel.teammanager.views.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private MainViewModel mainViewModel;

    //Views
    @BindView(R.id.mainLoginBtn)
    Button mainLoginBtn;
    @BindView(R.id.mainBaseBtn)
    Button mainBaseBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        mainViewModel.getIsUserLoggedLiveData().observe(this, bool -> mainViewModel.isUserLogged(bool));

        mainViewModel.getStartActivityEvent().observe(this, this::startActivityRequest);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainViewModel.onActivityStarted();
    }

    @OnClick(R.id.mainLoginBtn)
    void onMainLoginBtnClicked() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
    }

    @OnClick(R.id.mainBaseBtn)
    void onMainBaseBtnClicked() {
        Intent baseIntent = new Intent(MainActivity.this, BaseActivity.class);
        startActivity(baseIntent);
    }

    private void startActivityRequest(Activities activityName) {
        switch (activityName) {
            case Login:
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                Log.d(TAG, "Starting LoginActivity");
                break;
            case Base:
                Intent baseIntent = new Intent(MainActivity.this, BaseActivity.class);
                startActivity(baseIntent);
                Log.d(TAG, "Starting BaseActivity");
                break;
        }
    }
}
