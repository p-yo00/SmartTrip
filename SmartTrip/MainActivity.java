package com.example.projectui_yeon;

import static android.widget.Toast.LENGTH_SHORT;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Button mypage;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                String email=data.getStringExtra("email");
                String passwd=data.getStringExtra("passwd");

                if(LogManager.user.getEmail().equals(email)&&LogManager.user.getPasswd().equals(passwd)) {
                    Intent intent=new Intent(getApplicationContext(), ChangeUserInfoActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MainActivity.this, "이메일과 패스워드가 맞지않습니다", LENGTH_SHORT).show();
                }
            }
            else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(MainActivity.this, "취소되었습니다.", LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mypage=findViewById(R.id.mypage);

        mypage.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent=new Intent(getApplicationContext(), LoginPopupActivity.class);
                startActivityForResult(intent,2);

            }
        });
    }
}