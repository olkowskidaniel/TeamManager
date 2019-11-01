package com.olkowskidaniel.teammanager.views.base;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.olkowskidaniel.teammanager.R;
import com.olkowskidaniel.teammanager.model.Employee;
import com.olkowskidaniel.teammanager.utils.Fragments;
import com.olkowskidaniel.teammanager.viewmodels.base.PersonnelViewModel;

import java.util.List;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonnelFragment extends Fragment {

    @BindView(R.id.personnelEmployeeListRV)
    RecyclerView personnelEmployeeListRV;
    private EmployeeListRecyclerViewAdapter employeeListRecyclerViewAdapter;
    private RecyclerView.LayoutManager personnelEmployeeListRVLayoutManager;

    @BindView(R.id.personnelAddEmployeeBtn)
    Button personnelAddEmployeeBtn;

    private PersonnelViewModel personnelViewModel;

    public static PersonnelFragment newInstance() {
        return new PersonnelFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personnel_fragment, container, false);
        ButterKnife.bind(this, view);
        personnelEmployeeListRV.setHasFixedSize(true);
        personnelEmployeeListRVLayoutManager = new LinearLayoutManager(this.getActivity());
        personnelEmployeeListRV.setLayoutManager(personnelEmployeeListRVLayoutManager);
        employeeListRecyclerViewAdapter = new EmployeeListRecyclerViewAdapter();
        personnelEmployeeListRV.setAdapter(employeeListRecyclerViewAdapter);

        personnelViewModel = ViewModelProviders.of(this).get(PersonnelViewModel.class);

        personnelViewModel.getStartFragmentEvent().observe(this, this::onStartFragmentRequest);

        personnelViewModel.getEmployeesListLiveData().observe(this, this::employeesListUpdate);

        personnelViewModel.onFragmentStarted();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @OnClick(R.id.personnelAddEmployeeBtn)
    void personnelAddEmployeeBtnClicked() {
        personnelViewModel.onAddEmplBtnClicked();
    }

    private void onStartFragmentRequest(Fragments fragmentName) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.base_fragment_container, new AddNewEmplFragment()).commit();
    }

    private void employeesListUpdate(List<Employee> employeesList) {
        employeeListRecyclerViewAdapter.setEmployeeList(employeesList);
        employeeListRecyclerViewAdapter.notifyDataSetChanged();
    }
}
