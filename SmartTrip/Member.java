package com.example.projectui_yeon;

public class Member {
    protected String uid;
    protected String email;
    protected String passwd;
    protected String phoneNumber;
    protected int role;
    protected String name;
    protected Boolean gender;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }



    public Member(String email, String passwd, String pNum,int role, String name, Boolean gender) {
        this.email=email;
        this.passwd=passwd;
        this.phoneNumber=pNum;
        this.role=role;
        this.name=name;
        this.gender=gender;
    }

    public Member(){}

}
