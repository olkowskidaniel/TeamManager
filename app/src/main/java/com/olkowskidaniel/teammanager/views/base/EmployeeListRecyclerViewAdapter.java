package com.olkowskidaniel.teammanager.views.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.olkowskidaniel.teammanager.R;
import com.olkowskidaniel.teammanager.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeListRecyclerViewAdapter extends RecyclerView.Adapter<EmployeeListRecyclerViewAdapter.EmployeeListViewholder> {

    private List<Employee> employeeList = new ArrayList<>();



    @NonNull
    @Override
    public EmployeeListViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_employee_list, parent, false);
        return new EmployeeListViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeListViewholder holder, int position) {
        holder.bind(employeeList.get(position));
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public class EmployeeListViewholder extends RecyclerView.ViewHolder {
        TextView emplNameTV;
        TextView emplLastNameTV;
        public EmployeeListViewholder(@NonNull View itemView) {
            super(itemView);
            emplNameTV = itemView.findViewById(R.id.empListItemNameTV);
            emplLastNameTV = itemView.findViewById(R.id.empListItemLastNameTV);
        }

        public void bind(Employee employee) {
            emplNameTV.setText(employee.getName());
            emplLastNameTV.setText(employee.getLastName());
        }
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }
}
