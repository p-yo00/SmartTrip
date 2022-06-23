package com.example.projectui_yeon;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginPopupActivity extends Activity {
    Button btConfirm;
    Button btCancel;
    EditText etEmailpopup;
    EditText etPasswdpopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login_popup);

        btConfirm=findViewById(R.id.btConfirm);
        btCancel=findViewById(R.id.btCancel);
        etEmailpopup=findViewById(R.id.etEmailpopup);
        etPasswdpopup=findViewById(R.id.etPasswdpopup);

        btConfirm.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                boolean check=true;
                if(TextUtils.isEmpty(etEmailpopup.getText())){
                    Toast.makeText(getApplicationContext(),"이메일을 입력해야합니다.",Toast.LENGTH_SHORT).show();
                    check=false;
                }
                if(TextUtils.isEmpty(etPasswdpopup.getText())){
                    Toast.makeText(getApplicationContext(),"비밀번호를 입력해야합니다.",Toast.LENGTH_SHORT).show();
                    check=false;
                }
                if(check){
                    Intent intent=new Intent();
                    intent.putExtra("email",etEmailpopup.getText().toString());
                    intent.putExtra("passwd",etPasswdpopup.getText().toString());
                    setResult(RESULT_OK,intent);

                    finish();
                }
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent=new Intent();
                setResult(RESULT_CANCELED,intent);

                finish();
            }
        });


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) { //popup 외부 터치 불가
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        return;
    } //뒤로가기 불가


}