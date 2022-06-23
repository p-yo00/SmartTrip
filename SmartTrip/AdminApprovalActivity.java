package com.example.projectui_yeon;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//Admin의 메인화면으로 Host의 가입신청, 정보수정을 승인 또는 거절한다.
public class AdminApprovalActivity extends AppCompatActivity {
    TextView txtLogout;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_approval);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("관리자 페이지");

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser(); // mUser에 현재 로그인 회원 저장

        txtLogout=findViewById(R.id.txtLogout2);
        TabLayout tab=findViewById(R.id.adminTabLayout);

        ViewPager2 vp=findViewById(R.id.viewpager);
        VPAdapter adapter=new VPAdapter(this);
        vp.setAdapter(adapter);

        //페이지가 바뀔 때 원래화면이 onPause, 새화면이 onResume이 뜨도록함
        vp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                AdminFragment1 frag1 = new AdminFragment1();
                AdminFragment2 frag2 = new AdminFragment2();
                if(position==0){
                    frag2.setUserVisibleHint(false);
                    frag2.onPause();
                    frag1.setUserVisibleHint(true);
                    frag1.onResume();
                }
                else{
                    frag1.setUserVisibleHint(false);
                    frag1.onPause();
                    frag2.setUserVisibleHint(true);
                    frag2.onResume();
                }

            }
        });

        //탭의 타이틀 text를 지워지지 않고 유지
        final List<String> tabElement= Arrays.asList("가입요청 승인","정보수정 승인");
        new TabLayoutMediator(tab, vp, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                TextView textView=new TextView(AdminApprovalActivity.this);
                textView.setText(tabElement.get(position));
                tab.setCustomView(textView);
            }
        }).attach();

        //로그아웃 버튼
        txtLogout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                mAuth.signOut();
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);

                finish();
            }
        });


    }
}