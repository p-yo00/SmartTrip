package com.example.projectui_yeon;

import static android.widget.Toast.LENGTH_SHORT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.core.Tag;

import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    EditText etNameR;
    EditText etEmailR;
    EditText etPWR;
    EditText etPWreR;
    RadioButton rbman;
    RadioButton rbwoman;
    RadioGroup radioGroup;
    EditText etPhoneR;
    CheckBox cbHost;
    TextView txtBName;
    TextView txtBPhone;
    EditText etBName;
    EditText etBPhone;
    Button btRegister;
    Button btCancelRegister;
    LottieAnimationView lottie;
    ImageView img;

    MemberManager memberManager=new MemberManager();
    FirebaseAuth mAuth;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNameR=findViewById(R.id.etNameR);
        etEmailR=findViewById(R.id.etEmailR);
        etPWR=findViewById(R.id.etPWR);
        etPWreR=findViewById(R.id.etPWreR);
        rbman=findViewById(R.id.rbMan);
        rbwoman=findViewById(R.id.rbWoman);
        radioGroup=findViewById(R.id.radioGroup);
        etPhoneR=findViewById(R.id.etPhoneR);
        cbHost=findViewById(R.id.cbHost);
        txtBName=findViewById(R.id.txtBNR);
        txtBPhone=findViewById(R.id.txtBPR);
        etBName=findViewById(R.id.etBNR);
        etBPhone=findViewById(R.id.etBPR);
        btRegister=findViewById(R.id.btRegister);
        btCancelRegister=findViewById(R.id.btCancelRegister);
        radioGroup=findViewById(R.id.radioGroup);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("회원가입");

        cbHost.setOnClickListener(new CheckBox.OnClickListener(){
            public void onClick(View v){
                if(cbHost.isChecked()){
                    txtBName.setVisibility(View.VISIBLE);
                    txtBPhone.setVisibility(View.VISIBLE);
                    etBName.setVisibility(View.VISIBLE);
                    etBPhone.setVisibility(View.VISIBLE);
                }
                else{
                    txtBName.setVisibility(View.INVISIBLE);
                    txtBPhone.setVisibility(View.INVISIBLE);
                    etBName.setVisibility(View.INVISIBLE);
                    etBPhone.setVisibility(View.INVISIBLE);
                }
            }
        });

        btRegister.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                if(validation()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);

                    builder.setTitle("회원가입").setMessage("이메일 링크 인증 후 로그인이 가능합니다.\n 가입 하시겠습니까?");

                    builder.setPositiveButton("네", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int id)
                        {
                            String name=etNameR.getText().toString();
                            String email=etEmailR.getText().toString();
                            String passwd=etPWR.getText().toString();
                            String phoneNumber=etPhoneR.getText().toString();
                            Boolean gender;
                            if(rbman.isChecked()) gender=Boolean.FALSE;
                            else gender=Boolean.TRUE;
                            int role;
                            if(cbHost.isChecked()) role=3;
                            else role=1;
                            String businessName="";
                            String businessPhone="";
                            if(cbHost.isChecked()){
                                businessName=etBName.getText().toString();
                                businessPhone=etBPhone.getText().toString();
                            }

                            mAuth=FirebaseAuth.getInstance();
                            mAuth.createUserWithEmailAndPassword(email,passwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                                    Toast.makeText(getApplicationContext(),"가입이 완료되었습니다", LENGTH_SHORT);

                                    lottie = findViewById(R.id.lottie);
                                    img=findViewById(R.id.img);
                                    lottie.setAnimation("loading.json");
                                    img.setVisibility(View.VISIBLE);
                                    img.bringToFront();
                                    lottie.bringToFront();
                                    lottie.setVisibility(View.VISIBLE);
                                    lottie.playAnimation();
                                    lottie.loop(true);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),"가입실패. 다시 시도해주십시오", LENGTH_SHORT);
                                }
                            });

                            Handler handler=new Handler();
                            String finalBusinessName = businessName;
                            String finalBusinessPhone = businessPhone;

                            handler.postDelayed(new Runnable(){
                                @Override
                                public void run() {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    uid = user.getUid();
                                    user.sendEmailVerification();
                                    memberManager.Register(uid, email, passwd,role,name,gender,phoneNumber, finalBusinessName, finalBusinessPhone,false);
                                    mAuth.signOut();

                                    lottie.setVisibility(View.GONE);
                                    finish();
                                }
                            },35000);

                            Handler handler2=new Handler();
                            handler2.postDelayed(new Runnable(){
                                @Override
                                public void run() {
                                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                                    Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                                    startActivity(intent);
                                }
                            },10000);
                        }
                    });

                    builder.setNegativeButton("아니오", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int id)
                        {
                            Toast.makeText(getApplicationContext(), "취소되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }
            }
        });


        btCancelRegister.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);

                finish();
            }
        });
    }

    public boolean validation(){
        if(TextUtils.isEmpty(etNameR.getText())){
            Toast.makeText(getApplicationContext(),"이름을 입력해야합니다.", LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(etEmailR.getText())){
            Toast.makeText(getApplicationContext(),"이메일을 입력해야합니다.", LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(etPWR.getText())){
            Toast.makeText(getApplicationContext(),"비밀번호를 입력해야합니다.", LENGTH_SHORT).show();
            return false;
        }
        if(etPWR.getText().length()<6){
            Toast.makeText(getApplicationContext(),"비밀번호가 6자리 이상이어야합니다.", LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(etPWreR.getText())){
            Toast.makeText(getApplicationContext(),"비밀번호 확인을 입력해야합니다.", LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(etPhoneR.getText())){
            Toast.makeText(getApplicationContext(),"전화번호를 입력해야합니다.", LENGTH_SHORT).show();
            return false;
        }
        if(!(rbman.isChecked())&&!(rbwoman.isChecked())){
            Toast.makeText(getApplicationContext(),"성별을 체크해야합니다.", LENGTH_SHORT).show();
            return false;
        }
        if(cbHost.isChecked()){
            if(TextUtils.isEmpty(etBName.getText()))
                Toast.makeText(getApplicationContext(),"업체명을 입력해야합니다.", LENGTH_SHORT).show();
            if(TextUtils.isEmpty(etBName.getText()))
                Toast.makeText(getApplicationContext(),"업체 전화번호을 입력해야합니다.", LENGTH_SHORT).show();
        }
        return true;
    }




}