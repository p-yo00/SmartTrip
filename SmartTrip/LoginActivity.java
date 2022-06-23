package com.example.projectui_yeon;

import static android.widget.Toast.LENGTH_SHORT;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class LoginActivity extends AppCompatActivity {
    Button btLogin;
    TextView tvRegister;
    TextView tvFindInfo;
    EditText etEmail;
    EditText etPasswd;

    private FirebaseAuth mAuth;
    private DatabaseReference mReference= FirebaseDatabase.getInstance().getReference().child("Member");
    private ArrayList<Member> memberDB=new ArrayList<>();
    private Map<String,Member> memberMap=new HashMap<>();
    //Map<String,Host> hostMap=new HashMap<>();

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser=mAuth.getInstance().getCurrentUser();

        mReference.child("User").addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    if(currentUser!=null && currentUser.getUid().equals(snapshot.getKey())) {
                        LogManager.user=snapshot.getValue(Member.class);
                        LogManager.user.uid=currentUser.getUid();

                        Intent intent = new Intent(getApplicationContext(), ChangeUserInfoActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        mReference.child("Host").addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    if(currentUser!=null &&currentUser.getUid().equals(snapshot.getKey())) {
                        LogManager.user=snapshot.getValue(Host.class);
                        LogManager.user.uid=currentUser.getUid();

                        Intent intent = new Intent(getApplicationContext(), ChangeUserInfoActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mReference.child("Admin").addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    if(currentUser!=null &&currentUser.getUid().equals(snapshot.getKey())) {
                        LogManager.user=snapshot.getValue(Member.class);

                        Intent intent = new Intent(getApplicationContext(), AdminApprovalActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btLogin=findViewById(R.id.btLogin);
        tvRegister=findViewById(R.id.tvRegister);
        tvFindInfo=findViewById(R.id.tvFindInfo);
        etEmail=findViewById(R.id.etEmail);
        etPasswd=findViewById(R.id.etPasswd);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("로그인");

        mAuth=FirebaseAuth.getInstance();

        tvRegister.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);

                finish();
            }
        });

        tvFindInfo.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent=new Intent(getApplicationContext(),FindInfoActivity.class);
                startActivity(intent);

                finish();
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                if(validation())
                    login(etEmail.getText().toString(),etPasswd.getText().toString());
            }
        });


        mReference.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Member mem = snapshot.getValue(Member.class);
                    mem.setUid(snapshot.getKey());
                    memberMap.put(snapshot.getKey(),mem);
                    memberDB.add(mem);
                    }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mReference.child("Host").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Host mem = snapshot.getValue(Host.class);
                    //Member mem = snapshot.getValue(Host.class);

                    mem.setUid(snapshot.getKey());
                    memberMap.put(snapshot.getKey(),mem);
                    memberDB.add(mem);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mReference.child("Admin").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Admin mem = snapshot.getValue(Admin.class);
                    mem.setUid(snapshot.getKey());
                    memberMap.put(snapshot.getKey(),mem);
                    memberDB.add(mem);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mReference.child("Host").addChildEventListener(new ChildEventListener(){
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                mReference.child("Host").addListenerForSingleValueEvent(new ValueEventListener(){
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            Host mem = snapshot.getValue(Host.class);
                            mem.setUid(snapshot.getKey());
                            //Member mem = snapshot.getValue(Member.class);
                            memberMap.put(snapshot.getKey(),mem);
                            memberDB.add(mem);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public boolean validation(){
        if(TextUtils.isEmpty(etEmail.getText())){
            Toast.makeText(getApplicationContext(),"이메일을 입력해야합니다.",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(etPasswd.getText())){
            Toast.makeText(getApplicationContext(),"비밀번호를 입력해야합니다.",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void login(String email,String passwd){
        mAuth.signInWithEmailAndPassword(email,passwd)
                .addOnCompleteListener(this, task -> {
                    if(task.isSuccessful()){
                        FirebaseUser Fuser=mAuth.getCurrentUser();
                        Member mem = memberMap.get(Fuser.getUid());
                        if(Fuser.isEmailVerified()) {
                            if(mem.getRole()==3){   //host
                                Host memh = (Host)memberMap.get(Fuser.getUid());

                                if(memh.getValid()){
                                    Log.d("Host 로그인:", Fuser.getUid());
                                    LogManager.user=(Member)memh;
                                    LogManager.user.uid=Fuser.getUid();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);

                                    finish();
                                }
                                else{
                                    Toast.makeText(LoginActivity.this, "관리자 승인이 필요합니다.", LENGTH_SHORT).show();
                                    mAuth.signOut();
                                }
                            }
                            else {  //user
                                Toast.makeText(LoginActivity.this, "로그인 성공", LENGTH_SHORT).show();
                                LogManager.user = mem;
                                LogManager.user.uid=Fuser.getUid();

                                Log.d("User 로그인:", Fuser.getUid());
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);

                                finish();
                            }
                        }
                        else {
                            if(mem.getRole()==2){
                                Log.d("Admin 로그인:", Fuser.getUid());
                                LogManager.user = mem;
                                LogManager.user.uid=Fuser.getUid();
                                Intent intent = new Intent(getApplicationContext(), AdminApprovalActivity.class);
                                startActivity(intent);

                                finish();
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "이메일 인증이 필요합니다.", LENGTH_SHORT).show();
                                mAuth.signOut();
                            }
                        }

                    }
                    else{
                        Toast.makeText(LoginActivity.this,"로그인 실패",LENGTH_SHORT).show();

                    }
                });

    }

}