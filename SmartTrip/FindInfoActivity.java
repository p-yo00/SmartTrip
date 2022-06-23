package com.example.projectui_yeon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindInfoActivity extends AppCompatActivity {
    static ArrayList<Member> memDB = new ArrayList<>();
    private DatabaseReference mRef= FirebaseDatabase.getInstance().getReference().child("Member");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_info);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("아이디/비밀번호 찾기");

        TabLayout tab=findViewById(R.id.findTabLayout);

        ViewPager2 vp=findViewById(R.id.findviewpager);
        findVPAdapter adapter=new findVPAdapter(this);
        vp.setAdapter(adapter);

        mRef.child("User").addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    user.setUid(snapshot.getKey());
                    if(user!=null)
                        memDB.add(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mRef.child("Host").addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Host user = snapshot.getValue(Host.class);
                    user.setUid(snapshot.getKey());
                    memDB.add(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mRef.child("Admin").addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Admin user = snapshot.getValue(Admin.class);
                    user.setUid(snapshot.getKey());
                    memDB.add(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        final List<String> tabElement= Arrays.asList("아이디 찾기","비밀번호 찾기");

        new TabLayoutMediator(tab, vp, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                TextView textView=new TextView(FindInfoActivity.this);
                textView.setText(tabElement.get(position));
                tab.setCustomView(textView);
            }
        }).attach();
    }

}