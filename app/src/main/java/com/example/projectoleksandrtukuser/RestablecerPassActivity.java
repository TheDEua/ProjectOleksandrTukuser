package com.example.projectoleksandrtukuser;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RestablecerPassActivity extends AppCompatActivity {
    private EditText mEmail;
    private Button sendPassword, login, register;
    private String email;
    private FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restablecer_pass);
        mEmail = (EditText) findViewById(R.id.editTextEmailAddressRes);
        login = (Button) findViewById(R.id.buttonIniciar);
        register = (Button) findViewById(R.id.buttonRegister);
        sendPassword = (Button) findViewById(R.id.buttonRestablecer);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RestablecerPassActivity.this, LoginActivity.class));
                finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RestablecerPassActivity.this, RegisterActivity.class));
                finish();
            }
        });
        sendPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }
    public void validate(){
        email = mEmail.getText().toString();
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(RestablecerPassActivity.this, R.string.invalid_email, Toast.LENGTH_SHORT).show();
            return;
        }
        sendEmail(email);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(RestablecerPassActivity.this, LoginActivity.class));
        finish();
    }

    public void sendEmail(String email) {
        fAuth = FirebaseAuth.getInstance();
        String emailAdress = email;
        fAuth.sendPasswordResetEmail(emailAdress).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RestablecerPassActivity.this, R.string.send_email, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RestablecerPassActivity.this, LoginActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(RestablecerPassActivity.this, R.string.invalid_email, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}