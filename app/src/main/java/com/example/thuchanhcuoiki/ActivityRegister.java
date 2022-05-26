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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityRegister extends AppCompatActivity {
    private EditText email_register;
    private EditText password_register;
    private EditText type_password_register;
    private Button btnRegister;
    private TextView txSignin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        email_register = findViewById(R.id.emailRegister);
        password_register = findViewById(R.id.passwordRegister);
        type_password_register = findViewById(R.id.typePasswordRegister);
        btnRegister = findViewById(R.id.btnRegister);
        txSignin = findViewById(R.id.txtSignIn);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
        txSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityRegister.this,ActivityLogin.class);
                startActivity(i);
            }
        });
    }

    private void register() {
        String email, password,typePassword;
        email = email_register.getText().toString();
        password = password_register.getText().toString();
        typePassword = type_password_register.getText().toString();
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this,"Vui long nhap email hop le!!!",Toast.LENGTH_SHORT).show();
            return;

        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Vui long nhap password !",Toast.LENGTH_SHORT).show();
            return;

        }
        if(!typePassword.equals(password)){
            Toast.makeText(this,"Password khong giong vui long nhap lai !",Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Tao tai khoan thanh cong!!!",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ActivityRegister.this,ActivityLogin.class);
                    startActivity(i);
                }else {
                    Toast.makeText(getApplicationContext(),"Tao tai khoan khong thanh cong!!!",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
