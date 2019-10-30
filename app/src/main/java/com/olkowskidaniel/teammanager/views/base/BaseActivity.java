package com.olkowskidaniel.teammanager.views.base;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.olkowskidaniel.teammanager.R;
import com.olkowskidaniel.teammanager.viewmodels.base.BaseViewModel;
import com.olkowskidaniel.teammanager.views.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";
    private BaseViewModel baseViewModel;

    @BindView(R.id.baseBottomNav)
    BottomNavigationView baseBottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ButterKnife.bind(this);
        baseViewModel = ViewModelProviders.of(this).get(BaseViewModel.class);
        baseBottomNav.setOnNavigationItemSelectedListener(baseBottomNavItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.base_fragment_container, new HomeFragment()).commit();

        baseViewModel.getIsUserLoggedLiveData().observe(this, bool -> baseViewModel.isUserLogged(bool));

        baseViewModel.getStartActivityEvent().observe(this, this::startActivityRequest);

        baseViewModel.getCurrentFirebaseUserLiveData().observe(this, user -> baseViewModel.setCurrentUser(user));

        baseViewModel.getStartFragmentEvent().observe(this, this::startFragmentRequest);

        baseViewModel.getDeleteUserConfirmationRequestEvent().observe(this, this::onDeleteUserConfirmationRequest);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener baseBottomNavItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            baseViewModel.onBottomNavItemClicked(menuItem.getItemId());
            return true;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_options_base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        baseViewModel.onOptionsButtonClicked(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    private void startActivityRequest(String activityName) {
        switch (activityName) {
            case "MainActivity":
                Intent mainIntent = new Intent(BaseActivity.this, MainActivity.class);
                startActivity(mainIntent);
                break;
        }
    }

    private void startFragmentRequest(String fragmentName) {
        switch (fragmentName) {
            case "HomeFragment":
                getSupportFragmentManager().beginTransaction().replace(R.id.base_fragment_container, new HomeFragment()).commit();
                break;
            case "PersonnelFragment":
                getSupportFragmentManager().beginTransaction().replace(R.id.base_fragment_container, new PersonnelFragment()).commit();
                break;
            case "TasksFragment":
                getSupportFragmentManager().beginTransaction().replace(R.id.base_fragment_container, new TasksFragment()).commit();
                break;
            case "ScheduleFragment":
                getSupportFragmentManager().beginTransaction().replace(R.id.base_fragment_container, new ScheduleFragment()).commit();
                break;
        }
    }

    private void onDeleteUserConfirmationRequest(Boolean aBoolean) {
        if(aBoolean) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure?").setPositiveButton("Yes", (dialogInterface, i) -> {
                baseViewModel.onDeleteAccountConfirmedByUser(true);
            }).setNegativeButton("No", ((dialogInterface, i) -> {
                baseViewModel.onDeleteAccountConfirmedByUser(false);
            }) ).show();
        }
    }
}
