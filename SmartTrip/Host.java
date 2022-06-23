package com.example.projectui_yeon;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Host extends Member implements Serializable {
    private String businessName;
    private String businessPhoneNum;
    private Boolean valid;

    public Host(String email, String passwd, String pNum, String name, Boolean gender, String bn, String bp, boolean valid) {
        super(email, passwd, pNum, 3, name, gender);
        this.businessName=bn;
        this.businessPhoneNum=bp;
        this.valid=valid;
    }

    public Host(){
    }

    public Map<String,Object> toMap(){
        HashMap<String,Object> userMap=new HashMap<>();
        userMap.put("email",email);
        userMap.put("passwd",passwd);
        userMap.put("role",role);
        userMap.put("name",name);
        userMap.put("gender",gender);
        userMap.put("phoneNumber",phoneNumber);
        userMap.put("businessName",businessName);
        userMap.put("businessPhoneNum",businessPhoneNum);
        userMap.put("valid",valid);
        return userMap;
    }

    @Override
    public String getUid() {
        return super.getUid();
    }

    @Override
    public void setUid(String uid) {
        super.setUid(uid);
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

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessPhoneNum() {
        return businessPhoneNum;
    }

    public void setBusinessPhoneNum(String businessPhoneNum) {
        this.businessPhoneNum = businessPhoneNum;
    }

}
