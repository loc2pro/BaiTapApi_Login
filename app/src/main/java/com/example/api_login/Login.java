package com.example.api_login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    Button btnLogin;
    EditText etEmail, etPass;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.eEmail);
        etPass = findViewById(R.id.ePassword);

        btnLogin = findViewById(R.id.btnDangNhap);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = etEmail.getText().toString().trim();
                String pass = etPass.getText().toString().trim();

                auth.signInWithEmailAndPassword(user,pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()){
                                    Toast.makeText(Login.this, "Login Faild", Toast.LENGTH_SHORT).show();
                                }else {
                                    Intent intent = new Intent(Login.this,MainActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });

            }
        });
    }

}