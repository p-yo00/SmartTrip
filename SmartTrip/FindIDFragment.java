package com.example.projectui_yeon;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FindIDFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FindIDFragment extends Fragment {
    Button btCancelID;
    Button btFindID;
    EditText etFindID;
    TextView txtFindID;
    TextView txtFindID1;
    TextView txtFindID2;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FindIDFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FindIDFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FindIDFragment newInstance(String param1, String param2) {
        FindIDFragment fragment = new FindIDFragment();
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
        View view=inflater.inflate(R.layout.fragment_find_i_d, container, false);
        btCancelID=(Button)view.findViewById(R.id.btCancelID);
        btFindID=view.findViewById(R.id.btFindID);
        etFindID=view.findViewById(R.id.etFindID);
        txtFindID=view.findViewById(R.id.txtFindID);
        txtFindID1=view.findViewById(R.id.txtFindID1);
        txtFindID2=view.findViewById(R.id.txtFindID2);
        ArrayList<Member> memDB = FindInfoActivity.memDB;
        DatabaseReference mRef= FirebaseDatabase.getInstance().getReference().child("Member");


        btFindID.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                for(int i=0; i<memDB.size();i++){
                    Log.d("find1",memDB.get(i).getEmail());
                    if(memDB.get(i).phoneNumber.equals(etFindID.getText().toString())){
                        Log.d("find2",memDB.get(i).getEmail());
                        txtFindID.setText(memDB.get(i).getEmail());
                        txtFindID1.setVisibility(View.VISIBLE);
                        txtFindID2.setVisibility(View.VISIBLE);
                        break;
                    }
                }
            }
        });

        btCancelID.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent=new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);

                getActivity().finish();
            }
        });


        return view;
    }

}
