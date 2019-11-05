package com.olkowskidaniel.teammanager.views.base;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.olkowskidaniel.teammanager.R;
import com.olkowskidaniel.teammanager.viewmodels.base.TasksViewModel;

public class TasksFragment extends Fragment {

    private TasksViewModel tasksViewModel;

    public static TasksFragment newInstance() {
        return new TasksFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tasks, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tasksViewModel = ViewModelProviders.of(this).get(TasksViewModel.class);
    }

}
