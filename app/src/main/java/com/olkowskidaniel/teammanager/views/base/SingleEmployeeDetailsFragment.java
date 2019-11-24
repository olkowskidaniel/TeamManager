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
    TextView nameTV;
    @BindView(R.id.singleEmployeeDetailsNameEditBtn)
    Button nameEditBtn;
    @BindView(R.id.singleEmployeeDetailsNameET)
    TextView nameET;
    @BindView(R.id.singleEmployeeDetailsNameSaveBtn)
    Button nameSaveBtn;
    @BindView(R.id.singleEmployeeDetailsNameCancelBtn)
    Button nameCancelBtn;
    @BindView(R.id.singleEmployeeDetailsLastNameTV)
    TextView lastNameTV;
    @BindView(R.id.singleEmployeeDetailsLastNameEditBtn)
    Button lastNameEditBtn;
    @BindView(R.id.singleEmployeeDetailsLastNameET)
    TextView lastNameET;
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
        clickedEmployee = (Employee) bundle.getSerializable("employee");
        singleEmployeeDetailsViewModel.onFragmentCreated(bundle.getSerializable("employee"));
        singleEmployeeDetailsViewModel.getFragmentTitleLiveData().observe(this, title -> titleTV.setText(title));
        singleEmployeeDetailsViewModel.getEmployeeNameUpdatedEvent().observe(this, this::onEmployeeNameUpdated);
        singleEmployeeDetailsViewModel.getEmployeeLastNameUpdatedEvent().observe(this, this::onEmployeeLastNameUpdated);

        singleEmployeeDetailsViewModel.getEmployeeNameLiveData().observe(this, name -> nameTV.setText(name));
        singleEmployeeDetailsViewModel.getEmployeeLastNameLiveData().observe(this, lastName -> lastNameTV.setText(lastName));
    }


    @OnClick({R.id.singleEmployeeDetailsNameEditBtn, R.id.singleEmployeeDetailsNameSaveBtn, R.id.singleEmployeeDetailsNameCancelBtn,
    R.id.singleEmployeeDetailsLastNameEditBtn, R.id.singleEmployeeDetailsLastNameSaveBtn, R.id.singleEmployeeDetailsLastNameCancelBtn})
    void buttonClicked(Button button){
        switch (button.getId()) {
            case R.id.singleEmployeeDetailsNameEditBtn:
                nameTV.setVisibility(View.GONE);
                nameEditBtn.setVisibility(View.GONE);
                nameET.setVisibility(View.VISIBLE);
                nameSaveBtn.setVisibility(View.VISIBLE);
                nameCancelBtn.setVisibility(View.VISIBLE);
                nameET.setText(clickedEmployee.getName());
                break;
            case R.id.singleEmployeeDetailsNameSaveBtn:
                singleEmployeeDetailsViewModel.onNameSaveButtonClicked(clickedEmployee.getEmplId(), nameET.getText().toString());
                break;
            case R.id.singleEmployeeDetailsNameCancelBtn:
                nameTV.setVisibility(View.VISIBLE);
                nameEditBtn.setVisibility(View.VISIBLE);
                nameET.setVisibility(View.GONE);
                nameSaveBtn.setVisibility(View.GONE);
                nameCancelBtn.setVisibility(View.GONE);
                break;
            case R.id.singleEmployeeDetailsLastNameEditBtn:
                lastNameTV.setVisibility(View.GONE);
                lastNameEditBtn.setVisibility(View.GONE);
                lastNameET.setVisibility(View.VISIBLE);
                lastNameSaveBtn.setVisibility(View.VISIBLE);
                lastNameCancelBtn.setVisibility(View.VISIBLE);
                lastNameET.setText(clickedEmployee.getLastName());
                break;
            case R.id.singleEmployeeDetailsLastNameSaveBtn:
                singleEmployeeDetailsViewModel.onLastNameSaveButtonClicked(clickedEmployee.getEmplId(), lastNameET.getText().toString());
                break;
            case R.id.singleEmployeeDetailsLastNameCancelBtn:
                lastNameTV.setVisibility(View.VISIBLE);
                lastNameEditBtn.setVisibility(View.VISIBLE);
                lastNameET.setVisibility(View.GONE);
                lastNameSaveBtn.setVisibility(View.GONE);
                lastNameCancelBtn.setVisibility(View.GONE);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + button.getId());
        }
    }

    private void onEmployeeNameUpdated(String name) {
        nameTV.setVisibility(View.VISIBLE);
        nameEditBtn.setVisibility(View.VISIBLE);
        nameET.setVisibility(View.GONE);
        nameSaveBtn.setVisibility(View.GONE);
        nameCancelBtn.setVisibility(View.GONE);
        nameTV.setText(name);
    }

    private void onEmployeeLastNameUpdated(String lastName) {
        lastNameTV.setVisibility(View.VISIBLE);
        lastNameEditBtn.setVisibility(View.VISIBLE);
        lastNameET.setVisibility(View.GONE);
        lastNameSaveBtn.setVisibility(View.GONE);
        lastNameCancelBtn.setVisibility(View.GONE);
        lastNameTV.setText(lastName);
    }
}
