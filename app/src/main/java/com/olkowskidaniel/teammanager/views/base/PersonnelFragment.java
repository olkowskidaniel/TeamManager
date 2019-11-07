package com.olkowskidaniel.teammanager.views.base;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.olkowskidaniel.teammanager.R;
import com.olkowskidaniel.teammanager.model.Employee;
import com.olkowskidaniel.teammanager.utils.Fragments;
import com.olkowskidaniel.teammanager.viewmodels.base.PersonnelViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PersonnelFragment extends Fragment {

    private static final String TAG = "PersonnelFragment";

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
        View view = inflater.inflate(R.layout.fragment_personnel, container, false);
        ButterKnife.bind(this, view);
        personnelEmployeeListRV.setHasFixedSize(true);
        personnelEmployeeListRVLayoutManager = new LinearLayoutManager(this.getActivity());
        personnelEmployeeListRV.setLayoutManager(personnelEmployeeListRVLayoutManager);
        employeeListRecyclerViewAdapter = new EmployeeListRecyclerViewAdapter(new EmployeesListClickListener() {
            @Override
            public void onDeleteButtonClicked(String emplId) {
                Log.d(TAG, "emplId: " + emplId);
                personnelViewModel.onDeleteEmployeeBtnClicked(emplId);
            }
            @Override
            public void onListItemClicked(Employee employee) {
                personnelViewModel.onEmployeesListItemClicked(employee);
                Log.d(TAG, "Item clicked with employee: " + employee.getName());
            }
        });
                personnelEmployeeListRV.setAdapter(employeeListRecyclerViewAdapter);

        personnelEmployeeListRV.addItemDecoration(
                new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        personnelViewModel = ViewModelProviders.of(this).get(PersonnelViewModel.class);

        personnelViewModel.getStartFragmentEvent().observe(this, this::onStartFragmentRequest);

        personnelViewModel.getEmployeesListLiveData().observe(this, this::onEmployeesListUpdate);

        personnelViewModel.getDeleteUserConfirmationRequestEvent().observe(this, this::onDeleteEmployeeConfirmationRequest);

        personnelViewModel.getEmployeeListChangedEvent().observe(this, this::employeeListChangedEventTriggered);

        personnelViewModel.getStartFragmentPassingEmployeeEvent().observe(this, this::onStartFragmentPassingEmployeeRequest);

        personnelViewModel.onFragmentStarted();
    }


    @OnClick(R.id.personnelAddEmployeeBtn)
    void personnelAddEmployeeBtnClicked() {
        personnelViewModel.onAddEmplBtnClicked();
    }


    private void onStartFragmentRequest(Fragments fragmentName) {
        switch(fragmentName) {
            case AddNewEmpl:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.base_fragment_container, new AddNewEmplFragment()).commit();
                break;
        }
    }

    private void onStartFragmentPassingEmployeeRequest(Employee employee) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        SingleEmployeeDetailsFragment singleEmployeeDetailsFragment = new SingleEmployeeDetailsFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("employee", employee);
        singleEmployeeDetailsFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.base_fragment_container, singleEmployeeDetailsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void onEmployeesListUpdate(List<Employee> employeesList) {
        employeeListRecyclerViewAdapter.setEmployeeList(employeesList);
        employeeListRecyclerViewAdapter.notifyDataSetChanged();
    }


    private void onDeleteEmployeeConfirmationRequest(Boolean aBoolean) {
        if(aBoolean) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Are you sure to delete employee?")
                    .setPositiveButton("Yes", (dialogInterface, i) -> personnelViewModel.onDeleteEmployeeConfirmedByUser(true))
                    .setNegativeButton("No", ((dialogInterface, i) -> personnelViewModel.onDeleteEmployeeConfirmedByUser(false)))
                    .show();
        }
    }

    private void employeeListChangedEventTriggered(Boolean aBoolean) {
        if(aBoolean) {
            personnelViewModel.onEmployeeListChangedEventTriggered();
        }
    }
}
