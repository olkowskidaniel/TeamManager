package com.olkowskidaniel.teammanager.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import com.olkowskidaniel.teammanager.R;
import com.olkowskidaniel.teammanager.viewmodels.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;

    //Views

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.onActivityCreated();

        final Observer<String> mainViewModelMessageObserver = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.equals("startLoginActivity")) {
                    Intent loginIntent = new Intent (MainActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                }
            }
        };
        mainViewModel.getMainViewModelMessage().observe(this, mainViewModelMessageObserver);
    }
}
