package com.example.thuchanhcuoiki;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class ActivityLogin extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button btnSigIn;
    private TextView txtRegister;
    private FirebaseAuth math;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        math = FirebaseAuth.getInstance();
        email = findViewById(R.id.emailLogin);
        password = findViewById(R.id.passwordLogin);
        btnSigIn = findViewById(R.id.btnSignIn);
        txtRegister = findViewById(R.id.txtRegister);

        btnSigIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActivityLogin.this,ActivityRegister.class);
                startActivity(i);
            }
        });

    }
    private void login(){
        String emailLG,passwordLG;
        emailLG = email.getText().toString();
        passwordLG = password.getText().toString();

        if(!Patterns.EMAIL_ADDRESS.matcher(emailLG).matches()){
            Toast.makeText(this, "Vui long nhap email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(passwordLG)){
            Toast.makeText(this, "Vui long nhap password", Toast.LENGTH_SHORT).show();
            return;
        }

        math.signInWithEmailAndPassword(emailLG,passwordLG).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Dang nhap thanh cong", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ActivityLogin.this,ListMainActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(), "Dang nhap khong thanh cong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}