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
import com.olkowskidaniel.teammanager.viewmodels.base.PersonnelViewModel;

public class PersonnelFragment extends Fragment {

    private PersonnelViewModel personnelViewModel;

    public static PersonnelFragment newInstance() {
        return new PersonnelFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.personnel_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        personnelViewModel = ViewModelProviders.of(this).get(PersonnelViewModel.class);
        // TODO: Use the ViewModel
    }

}
