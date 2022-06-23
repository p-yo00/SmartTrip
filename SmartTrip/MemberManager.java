package com.example.projectui_yeon;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class MemberManager {
    DatabaseReference mReference= FirebaseDatabase.getInstance().getReference().child("Member");

    public MemberManager(){
    }

    public void Register(String uid,String email, String passwd, int role, String name, Boolean gender, String phoneNum
                            ,String businessN, String businessP, Boolean valid){
        Map<String,Object> userMap;

        if(role==1) {
            User user = new User(email, passwd, phoneNum, name, gender);
            user.uid=uid;
            userMap = user.toMap();
            mReference.child("User").child(uid).setValue(userMap);

        }
        else if(role==3){
            Host host=new Host(email,passwd,phoneNum,name,gender,businessN,businessP,valid);
            host.uid=uid;
            userMap=host.toMap();
            mReference.child("Host").child(uid).setValue(userMap);
        }
    }
}
