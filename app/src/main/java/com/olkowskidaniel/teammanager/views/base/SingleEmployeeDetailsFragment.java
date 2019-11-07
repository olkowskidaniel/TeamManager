package com.olkowskidaniel.teammanager.views.base;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.olkowskidaniel.teammanager.R;
import com.olkowskidaniel.teammanager.model.Employee;
import com.olkowskidaniel.teammanager.viewmodels.base.SingleEmployeeDetailsViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SingleEmployeeDetailsFragment extends Fragment {

    private SingleEmployeeDetailsViewModel singleEmployeeDetailsViewModel;
    private static final String TAG = "SingleEmployeeDetailsFragment";


    @BindView(R.id.singleEmployeeDetailsTitleNameTV)
    TextView titleTV;
    @BindView(R.id.singleEmployeeDetailsNameTV)
    TextView employeeNameTV;
    @BindView(R.id.singleEmployeeDetailsNameEditBtn)
    Button nameEditBtn;
    @BindView(R.id.singleEmployeeDetailsNameET)
    TextView employeeNameET;
    @BindView(R.id.singleEmployeeDetailsNameSaveBtn)
    Button nameSaveBtn;
    @BindView(R.id.singleEmployeeDetailsNameCancelBtn)
    Button nameCancelBtn;
    @BindView(R.id.singleEmployeeDetailsLastNameTV)
    TextView employeeLastNameTV;
    @BindView(R.id.singleEmployeeDetailsLastNameEditBtn)
    Button lastNameEditBtn;
    @BindView(R.id.singleEmployeeDetailsLastNameET)
    TextView employeeLastNameET;
    @BindView(R.id.singleEmployeeDetailsLastNameSaveBtn)
    Button lastNameSaveBtn;
    @BindView(R.id.singleEmployeeDetailsLastNameCancelBtn)
    Button lastNameCancelBtn;


    private Employee clickedEmployee;

    public static SingleEmployeeDetailsFragment newInstance() {
        return new SingleEmployeeDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_employee_details, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        singleEmployeeDetailsViewModel = ViewModelProviders.of(this).get(SingleEmployeeDetailsViewModel.class);
        Bundle bundle = getArguments();
        singleEmployeeDetailsViewModel.onFragmentCreated(bundle.getSerializable("employee"));
        clickedEmployee = (Employee) bundle.getSerializable("employee");
        singleEmployeeDetailsViewModel.getFragmentTitleLiveData().observe(this, title -> titleTV.setText(title));
        singleEmployeeDetailsViewModel.getEmployeeNameLiveData().observe(this, name -> employeeNameTV.setText(name));
        singleEmployeeDetailsViewModel.getEmployeeLastNameLiveData().observe(this, lastName -> employeeLastNameTV.setText(lastName));

        singleEmployeeDetailsViewModel.getEmployeeNameUpdatedEvent().observe(this, this::onEmployeeNameUpdated);
    }

    @OnClick(R.id.singleEmployeeDetailsNameEditBtn)
    void onNameEditBtnClicked() {
        employeeNameTV.setVisibility(View.GONE);
        nameEditBtn.setVisibility(View.GONE);

        employeeNameET.setVisibility(View.VISIBLE);
        employeeNameET.setText(clickedEmployee.getName());
        nameSaveBtn.setVisibility(View.VISIBLE);
        nameCancelBtn.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.singleEmployeeDetailsNameSaveBtn)
    void nameSaveBtnClicked() {
        singleEmployeeDetailsViewModel.onNameSaveButtonClicked(clickedEmployee.getEmplId(), employeeNameET.getText().toString().trim());
        Log.d(TAG, "name save button clicked");
    }

    @OnClick(R.id.singleEmployeeDetailsNameCancelBtn)
    void onNameCancelBtnClicked() {
        employeeNameTV.setVisibility(View.VISIBLE);
        nameEditBtn.setVisibility(View.VISIBLE);

        employeeNameET.setVisibility(View.GONE);
        nameSaveBtn.setVisibility(View.GONE);
        nameCancelBtn.setVisibility(View.GONE);
    }


    private void onEmployeeNameUpdated(String name) {
        employeeNameTV.setVisibility(View.VISIBLE);
        nameEditBtn.setVisibility(View.VISIBLE);

        employeeNameET.setVisibility(View.GONE);
        nameSaveBtn.setVisibility(View.GONE);
        nameCancelBtn.setVisibility(View.GONE);

        employeeNameTV.setText(name);
    }

    @OnClick(R.id.singleEmployeeDetailsLastNameEditBtn)
    void onLastNameEditBtnClicked() {
        employeeLastNameTV.setVisibility(View.GONE);
        lastNameEditBtn.setVisibility(View.GONE);

        employeeLastNameET.setVisibility(View.VISIBLE);
        employeeLastNameET.setText(clickedEmployee.getLastName());
        lastNameSaveBtn.setVisibility(View.VISIBLE);
        lastNameCancelBtn.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.singleEmployeeDetailsLastNameCancelBtn)
    void onLastNameCancelBtnClicked() {
        employeeLastNameTV.setVisibility(View.VISIBLE);
        lastNameEditBtn.setVisibility(View.VISIBLE);

        employeeLastNameET.setVisibility(View.GONE);
        lastNameSaveBtn.setVisibility(View.GONE);
        lastNameCancelBtn.setVisibility(View.GONE);
    }
}
