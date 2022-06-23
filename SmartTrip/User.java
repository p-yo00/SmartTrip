package com.example.projectui_yeon;

import java.util.HashMap;
import java.util.Map;

public class User extends Member {
    public User(String email, String passwd, String pNum, String name, Boolean gender) {
        super(email, passwd, pNum,1, name, gender);
    }

    public User(){}

    @Override
    public String getUid() {
        return super.getUid();
    }

    @Override
    public void setUid(String uid) {
        super.setUid(uid);
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }

    @Override
    public String getPasswd() {
        return super.getPasswd();
    }

    @Override
    public void setPasswd(String passwd) {
        super.setPasswd(passwd);
    }

    @Override
    public String getPhoneNumber() {
        return super.getPhoneNumber();
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        super.setPhoneNumber(phoneNumber);
    }

    @Override
    public int getRole() {
        return super.getRole();
    }

    @Override
    public void setRole(int role) {
        super.setRole(role);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public Boolean getGender() {
        return super.getGender();
    }

    @Override
    public void setGender(Boolean gender) {
        super.setGender(gender);
    }

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
