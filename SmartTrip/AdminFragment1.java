package com.example.projectui_yeon;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
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
 * Use the {@link AdminFragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminFragment1 extends Fragment {
    Button btReject;
    Button btApprove;
    CheckBox cb;
    int i=0;
    ArrayList<Boolean> checkBox=new ArrayList<>();
    private ListView listView;
    private HostListAdapter listAdapter;
    ArrayList<Host> hostDB=new ArrayList<>();

    private DatabaseReference mReference;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminFragment1() {
        // Required empty public constructor
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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminFragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminFragment1 newInstance(String param1, String param2) {
        AdminFragment1 fragment = new AdminFragment1();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_admin1, container, false);

        mReference=FirebaseDatabase.getInstance().getReference().child("Member");
        listView = (ListView) v.findViewById(R.id.list);
        btReject=v.findViewById(R.id.btReject);
        btApprove=v.findViewById(R.id.btApprove);
        cb=v.findViewById(R.id.checkBox2);

        mReference.child("Host").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                i=0;
                hostDB.clear();
                checkBox.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Host mem = snapshot.getValue(Host.class);
                    mem.setUid(snapshot.getKey());
                    Log.d("host의 키:",snapshot.getKey());
                    if(!mem.getValid()) {
                        hostDB.add(i,mem);
                        checkBox.add(i,false);

                        i++;
                    }
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

                Log.d("host","count "+count);
                for(int i=0; i<count; i++){
                    if(checkBox.get(i)){
                        Map<String,Object> map=new HashMap<>();
                        map.put("valid",true);
                        mReference.child("Host").child(hostDB.get(i).getUid()).updateChildren(map).addOnCompleteListener(
                                new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Log.d("요청 성공","");
                                    }
                                }
                        );
                        hostDB.remove(i);
                        listView.clearChoices();
                        listAdapter.notifyDataSetChanged();

                    }
                }
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
                int count;
                count=listView.getCount();

                for(int i=0; i<count;i++){
                    if(checkBox.get(i)){
                        Host host = hostDB.get(i);

                        //관리자 계정을 -> 삭제할 Host 계정으로 전환해서 삭제
                        FirebaseAuth mAuth=FirebaseAuth.getInstance();
                        mAuth.signOut();

                        mAuth.signInWithEmailAndPassword(host.getEmail(),host.getPasswd()).addOnCompleteListener(
                                new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        FirebaseUser deleteUser=mAuth.getCurrentUser();
                                        deleteUser.delete();
                                    }
                                }
                        );
                        mReference.child("Host").child(hostDB.get(i).getUid()).removeValue();

                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable(){
                            @Override
                            public void run() {
                                mAuth.signInWithEmailAndPassword(LogManager.user.getEmail(),LogManager.user.getPasswd());
                            }
                        },3000);

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