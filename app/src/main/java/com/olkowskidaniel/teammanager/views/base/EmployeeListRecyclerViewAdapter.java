package com.olkowskidaniel.teammanager.views.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.olkowskidaniel.teammanager.R;
import com.olkowskidaniel.teammanager.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeListRecyclerViewAdapter extends RecyclerView.Adapter<EmployeeListRecyclerViewAdapter.EmployeeListViewholder> {

    private List<Employee> employeeList = new ArrayList<>();

    private final EmployeesListClickListener clickListener;

    public EmployeeListRecyclerViewAdapter(EmployeesListClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public EmployeeListViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_employee_list, parent, false);
        return new EmployeeListViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeListViewholder holder, int position) {
        holder.bind(position, employeeList.get(position));
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public class EmployeeListViewholder extends RecyclerView.ViewHolder {
        TextView emplNumberTV;
        TextView emplNameTV;
        Button emplDeleteBtn;
        public EmployeeListViewholder(@NonNull View itemView) {
            super(itemView);
            emplNumberTV = itemView.findViewById(R.id.empListItemNumberTV);
            emplNameTV = itemView.findViewById(R.id.empListItemNameTV);
            emplDeleteBtn = itemView.findViewById(R.id.empListItemDeleteBtn);
        }

        public void bind(int position, Employee employee) {
            String name = employee.getLastName() + " " + employee.getName();
            String emplNumber = position + 1 + ".";
            emplNumberTV.setText(emplNumber);
            emplNameTV.setText(name);
            emplDeleteBtn.setOnClickListener(view -> clickListener.onDeleteButtonClicked(employee.getEmplId()));
            itemView.setOnClickListener(view -> clickListener.onListItemClicked(employee));
        }
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }
}
