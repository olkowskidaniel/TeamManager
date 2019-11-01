package com.olkowskidaniel.teammanager.views.base;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.olkowskidaniel.teammanager.R;
import com.olkowskidaniel.teammanager.viewmodels.base.AddNewEmplViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddNewEmplFragment extends Fragment {

    @BindView(R.id.addNewEmplNameET)
    EditText addNewEmplNameET;
    @BindView(R.id.addNewEmplLastNameET)
    EditText addNewEmplLastNameET;


    private AddNewEmplViewModel addNewEmplViewModel;

    public static AddNewEmplFragment newInstance() {
        return new AddNewEmplFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_empl, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addNewEmplViewModel = ViewModelProviders.of(this).get(AddNewEmplViewModel.class);
    }

    @OnClick(R.id.addNewEmplAddBtn)
    void addNewEmplAddBtnClicked() {
        if (addNewEmplNameET.getText().toString().trim().length() > 0 && addNewEmplLastNameET.getText().toString().trim().length() > 0) {
            String name = addNewEmplNameET.getText().toString().trim();
            String lastName = addNewEmplLastNameET.getText().toString().trim();

            addNewEmplViewModel.onAddNewEmplBtnClicked(name, lastName);
        } else {
            Toast.makeText(getActivity(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
        }
    }

}
