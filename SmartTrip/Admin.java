package com.example.projectui_yeon;

import java.util.HashMap;
import java.util.Map;

public class Admin extends Member{
    public Admin(String email, String passwd, String pNum, String name, Boolean gender) {
        super(email, passwd,pNum, 2, name, gender);
    }
    public Admin(){}

    public Map<String,Object> toMap(){
        HashMap<String,Object> userMap=new HashMap<>();
        userMap.put("email",email);
        userMap.put("passwd",passwd);
        userMap.put("role",role);
        userMap.put("name",name);
        userMap.put("gender",gender);
        userMap.put("phoneNumber",phoneNumber);
        return userMap;
    }
}
