package com.olkowskidaniel.teammanager.views.base;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.olkowskidaniel.teammanager.R;
import com.olkowskidaniel.teammanager.model.User;
import com.olkowskidaniel.teammanager.viewmodels.base.BaseViewModel;
import com.olkowskidaniel.teammanager.views.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {

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

        final Observer<String> baseViewModelMessageObserver = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                switch (s){
                    case "startMainActivity":
                        Intent mainIntent = new Intent(BaseActivity.this, MainActivity.class);
                        startActivity(mainIntent);
                        break;
                    case "startHomeFragment":
                        getSupportFragmentManager().beginTransaction().replace(R.id.base_fragment_container, new HomeFragment()).commit();
                        break;
                    case "startPersonnelFragment":
                        getSupportFragmentManager().beginTransaction().replace(R.id.base_fragment_container, new PersonnelFragment()).commit();
                        break;
                    case "startTasksFragment":
                        getSupportFragmentManager().beginTransaction().replace(R.id.base_fragment_container, new TasksFragment()).commit();
                        break;
                    case "startScheduleFragment":
                        getSupportFragmentManager().beginTransaction().replace(R.id.base_fragment_container, new ScheduleFragment()).commit();
                        break;
                }
            }
        };

        final Observer<User> currentUserObserver = new Observer<User>() {
            @Override
            public void onChanged(User user) {
                //TODO: empty here
            }
        };

        baseViewModel.getBaseViewModelMessage().observe(this, baseViewModelMessageObserver);
        baseViewModel.getCurrentUserLiveData().observe(this, currentUserObserver);
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
        baseViewModel.onActivityStarted();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_options_base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.baseOptionsMenu_logout:
                baseViewModel.onLogoutButtonClicked();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        baseViewModel.removeObservers();
    }
}
