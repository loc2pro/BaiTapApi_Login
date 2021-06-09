package com.example.api_login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class EmployeeAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Employee> empls;
    private int layout;

    public EmployeeAdapter(Context context, ArrayList<Employee> empls, int layout) {
        this.context = context;
        this.empls = empls;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return empls.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(layout,parent,false);
        TextView tvId = view.findViewById(R.id.tvId);
        TextView tvName = view.findViewById(R.id.tvName);

        tvId.setText(empls.get(position).getId()+"");
        tvName.setText(empls.get(position).getName());


        return view;
    }
}
