package com.example.projectui_yeon;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminFragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminFragment2 extends Fragment {
    private ListView listView;
    private HostListAdapter listAdapter;
    private MemberManager memberManager =new MemberManager();
    private ArrayList<Host> hostDB = new ArrayList<>();
    private DatabaseReference mReference;
    ArrayList<Boolean> checkBox=new ArrayList<>();
    int i=0;

    Button btReject;
    Button btApprove;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminFragment2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminFragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminFragment2 newInstance(String param1, String param2) {
        AdminFragment2 fragment = new AdminFragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(listView!=null && this.getUserVisibleHint()) {
            listView.setAdapter(null);

            listAdapter = new HostListAdapter(getActivity().getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, hostDB);
            listView.setAdapter(listAdapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_admin2, container, false);
        mReference= FirebaseDatabase.getInstance().getReference().child("Member");
        btReject=v.findViewById(R.id.btReject2);
        btApprove=v.findViewById(R.id.btApprove2);
        listView=v.findViewById(R.id.listview2);
        ViewPager2 vp=v.findViewById(R.id.viewpager);

        mReference.child("Host_request").child("Info").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hostDB.clear();
                checkBox.clear();
                i=0;
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Host mem = snapshot.getValue(Host.class);
                    mem.uid=snapshot.getKey();
                    hostDB.add(i,mem);
                    checkBox.add(i,false);

                    i++;
                }
                i=0;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listAdapter = new HostListAdapter(getActivity().getApplicationContext(),android.R.layout.simple_list_item_multiple_choice, hostDB);
                listView.setAdapter(listAdapter);
            }
        },3000);

        btApprove.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                int count=listView.getCount();

                for(int i=0; i<count; i++){
                    if(checkBox.get(i)){
                        Map<String,Object> map=new HashMap<>();
                        map.put("businessName",hostDB.get(i).getBusinessName());
                        map.put("businessPhoneNum",hostDB.get(i).getBusinessPhoneNum());

                        mReference.child("Host").child(hostDB.get(i).getUid()).updateChildren(map);
                        mReference.child("Host_request").child("Info").child(hostDB.get(i).getUid()).removeValue();

                        hostDB.remove(i);
                    }
                }
                listView.clearChoices();
                listAdapter.notifyDataSetChanged();

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(checkBox.get(i)) {
                    checkBox.set(i, false);
                    listView.setItemChecked(i,false);
                }
                else {
                    checkBox.set(i, true);
                    listView.setItemChecked(i,true);
                }
            }
        });

        btReject.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                int count = listView.getCount();

                for(int i=0; i<count;i++){
                    if(checkBox.get(i)){
                        mReference.child("Host_request").child("Info").child(hostDB.get(i).getUid()).removeValue();

                        hostDB.remove(i);
                        listView.clearChoices();
                        listAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        return v;
    }
}