package com.example.projectui_yeon;

import static android.widget.Toast.LENGTH_SHORT;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ChangeUserInfoActivity extends AppCompatActivity {
    Button btDelete;
    Button btModify;
    Button btCancel;
    EditText etNameC;
    TextView txtEmailC;
    EditText etPWC;
    EditText etPhoneC;
    RadioButton rbManC;
    RadioButton rbWomanC;
    EditText etBNC;
    EditText etBPC;
    TextView txtBNC;
    TextView txtBPC;
    TextView txtLogout;

    private String email, passwd;

    Member user = LogManager.user;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_info);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("회원정보수정");

        btDelete=findViewById(R.id.btDelete);
        btModify=findViewById(R.id.btModify);
        btCancel=findViewById(R.id.btCancelModify);
        etNameC=findViewById(R.id.etNameC);
        txtEmailC=findViewById(R.id.txtEmailC);
        etPWC=findViewById(R.id.etPWC);
        etPhoneC=findViewById(R.id.etPhoneC);
        rbManC=findViewById(R.id.rbMan);
        rbWomanC=findViewById(R.id.rbWoman);
        txtLogout=findViewById(R.id.txtLogout2);

        etNameC.setText(user.getName());
        txtEmailC.setText(user.getEmail());
        etPhoneC.setText(user.getPhoneNumber());
        if(user.getGender()==Boolean.FALSE) rbManC.setChecked(Boolean.TRUE);
        else rbWomanC.setChecked(Boolean.TRUE);

        if(user.getRole()==3){
            Host host = (Host)user;
            etBNC=findViewById(R.id.etBNC);
            etBPC=findViewById(R.id.etBPC);
            txtBNC=findViewById(R.id.txtBNC);
            txtBPC=findViewById(R.id.txtBPC);

            etBPC.setVisibility(View.VISIBLE);
            etBNC.setVisibility(View.VISIBLE);
            txtBNC.setVisibility(View.VISIBLE);
            txtBPC.setVisibility(View.VISIBLE);

            etBNC.setText(host.getBusinessName());
            etBPC.setText(host.getBusinessPhoneNum());
        }

        btDelete.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent=new Intent(getApplicationContext(), LoginPopupActivity.class);
                startActivityForResult(intent,1);

            }
        });

        btModify.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                DatabaseReference mRef=FirebaseDatabase.getInstance().getReference().child("Member");

                String name = etNameC.getText().toString();
                Boolean gender;
                if(rbManC.isChecked()) gender=false;
                else gender=true;
                String phoneNum=etPhoneC.getText().toString();
                String passwd;
                if(!TextUtils.isEmpty(etPWC.getText())){
                    if(etPWC.getText().length()<6)
                        Toast.makeText(ChangeUserInfoActivity.this, "비밀번호 수정실패. 6자리 이상 입력하세요", LENGTH_SHORT);
                    else{
                        passwd=etPWC.getText().toString();
                        mUser.updatePassword(passwd);
                        LogManager.user.setPasswd(passwd);
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("passwd",passwd);
                        if(LogManager.user.getRole()==1)
                            mRef.child("User").child(mUser.getUid()).updateChildren(map);
                        else
                            mRef.child("Host").child(mUser.getUid()).updateChildren(map);
                    }
                }
                user.setName(name);
                user.setGender(gender);
                user.setPhoneNumber(phoneNum);
                Map<String,Object> map= new HashMap<>();
                map.put("name",name);
                map.put("gender",gender);
                map.put("phoneNumber",phoneNum);

                if(user.getRole()==3){
                    String bName=etBNC.getText().toString();
                    String bPhone=etBPC.getText().toString();
                    mRef.child("Host").child(LogManager.user.getUid()).updateChildren(map);

                    map.put("email",user.getEmail());
                    map.put("businessName",bName);
                    map.put("businessPhoneNum",bPhone);
                    mRef.child("Host_request").child("Info").child(LogManager.user.getUid()).updateChildren(map);
                    Toast.makeText(getApplicationContext(),"관리자 승인 후 변경됩니다.", LENGTH_SHORT);
                }
                else{
                    mRef.child("User").child(LogManager.user.getUid()).updateChildren(map);
                    Toast.makeText(ChangeUserInfoActivity.this,"회원 정보가 수정되었습니다.", LENGTH_SHORT);
                }
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                finish();
            }
        });

        txtLogout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                mAuth.signOut();
                LogManager.user=null;
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                email=data.getStringExtra("email");
                passwd=data.getStringExtra("passwd");

                AuthCredential credential= EmailAuthProvider.getCredential(email,passwd);

                mUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String uid = LogManager.user.getUid();
                        int role = LogManager.user.getRole();
                        mUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
                                if(role==1){
                                    ref.child("Member").child("User").child(uid).removeValue();
                                }
                                else if(role==3){
                                    ref.child("Member").child("Host").child(uid).removeValue();
                                }

                                Toast.makeText(ChangeUserInfoActivity.this, "정상적으로 탈퇴되었습니다.", LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);

                                finish();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ChangeUserInfoActivity.this, "이메일과 패스워드가 맞지않습니다.", LENGTH_SHORT).show();
                    }
                });


            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(ChangeUserInfoActivity.this, "취소되었습니다.", LENGTH_SHORT).show();
            }
        }
    }
}