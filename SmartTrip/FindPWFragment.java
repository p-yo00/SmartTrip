package com.example.projectui_yeon;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FindPWFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FindPWFragment extends Fragment{

    Button btCancelPW;
    Button btFindPW;
    EditText etFindPW;
    TextView txtFindPW;
    TextView txtFindPW1;
    TextView txtFindPW2;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FindPWFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FindPWFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FindPWFragment newInstance(String param1, String param2) {
        FindPWFragment fragment = new FindPWFragment();
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
        View view=inflater.inflate(R.layout.fragment_find_p_w, container, false);
        btCancelPW=(Button)view.findViewById(R.id.btCancelPW);
        btFindPW=view.findViewById(R.id.btFindPW);
        etFindPW=view.findViewById(R.id.etFindPW);
        txtFindPW=view.findViewById(R.id.txtFindPW);
        txtFindPW1=view.findViewById(R.id.txtFindPW1);
        txtFindPW2=view.findViewById(R.id.txtFindPW2);
        ArrayList<Member> memDB = FindInfoActivity.memDB;

        btFindPW.setOnClickListener(new View.OnClickListener(){
            String email;
            String passwd;

            public void onClick(View view){
                for(int i=0; i<memDB.size();i++){
                    if(memDB.get(i).getEmail().equals(etFindPW.getText().toString())){
                        email=memDB.get(i).getEmail();
                        passwd = memDB.get(i).getPasswd();
                        Log.d("찾은 아이디, 비밀번호: ",email+passwd);
                        break;
                    }
                }
                String header = "[Smart Trip]비밀번호 찾기 결과";
                String context = "회원님의 비밀번호는 "+passwd+" 입니다. ";

                subThread sub = new subThread(email,header,context);
                try {
                    Log.d("1","sleep");
                    sub.sleep(1000);
                }catch(Exception e){}
                sub.start();

            }
        });

        btCancelPW.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent=new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);

                getActivity().finish();
            }
        });

        return view;
    }

    public class subThread extends Thread{
        String email;
        String header;
        String context;

        public subThread(String email, String header, String context){
            this.email=email;
            this.header=header;
            this.context=context;
        }

        @Override
        public void run() {
            Log.d("2","run");
            try {
                MailSender mailSender = new MailSender();

                mailSender.sendEmail(email, header, context);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

    }

}