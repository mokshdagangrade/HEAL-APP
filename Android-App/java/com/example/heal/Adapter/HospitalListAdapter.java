package com.example.heal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.heal.R;
import com.example.heal.customerui.Hospital;

import java.util.ArrayList;

public class HospitalListAdapter extends ArrayAdapter<Hospital> {

    private static final String TAG = "HospitalListAdapter";
    private Context mContext;
    int mResource;

    public HospitalListAdapter(Context context, int resource, ArrayList<Hospital> objects){

        super(context,resource,objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        String name = getItem(position).getName();
        String address = getItem(position).getAddress();
        String phone = getItem(position).getPhone();

        Hospital hospital = new Hospital(name,address,phone);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView tvname = convertView.findViewById(R.id.hospname);
        TextView tvadd = convertView.findViewById(R.id.address);
        TextView tvphone = convertView.findViewById(R.id.phone);

        tvname.setText(name);
        tvadd.setText(address);
        tvphone.setText(phone);

        return convertView;
    }
}
