package com.example.projectui_yeon;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class HostListAdapter extends ArrayAdapter {

    private Context context;
    private List<Host> hostList;
    ListView listView;

    public HostListAdapter(@NonNull Context context, int resource, @NonNull List<Host> hostList) {
        super(context, resource, hostList);
        this.hostList=hostList;
        this.context=context;
    }

    @Override
    public int getCount() {
        return hostList.size();
    }

    @Override
    public Object getItem(int i) {
        return hostList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.hostinfo, null);

        TextView hostID = (TextView) v.findViewById(R.id.txtEmail);
        TextView bName = (TextView) v.findViewById(R.id.txtBN);
        TextView bPhone = (TextView) v.findViewById(R.id.txtBP);
        listView=v.findViewById(R.id.list);

        hostID.setText(hostList.get(i).getEmail());
        bName.setText(hostList.get(i).getBusinessName());
        bPhone.setText(hostList.get(i).getBusinessPhoneNum());

        return v;

    }




}
