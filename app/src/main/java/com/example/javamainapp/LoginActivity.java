package com.example.javamainapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {
    EditText email,password;
    Button loginBtn;
    Databasehelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        email=findViewById(R.id.loginEmail);
        password=findViewById(R.id.loginPassword);
        loginBtn=findViewById(R.id.btnLogin);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailText=email.getText().toString();
                String passwordText=password.getText().toString();

                if(emailText.isEmpty() || passwordText.isEmpty()){
                    Toast.makeText(LoginActivity.this,"Please fill all fields",Toast.LENGTH_SHORT).show();
                }
                else{
                    db=new Databasehelper(LoginActivity.this);
                    String[] userResponse=db.loginUser(emailText,passwordText);
                    if(userResponse != null ){
                        Intent goToDashboard=new Intent(LoginActivity.this, Dashboard_Account.class);
                        goToDashboard.putExtra("Id",userResponse[0]);
                        goToDashboard.putExtra("username",userResponse[1]);
                        startActivity(goToDashboard);
                    }else{
                        Toast.makeText(LoginActivity.this,"Invalid credentials",Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });




    }
}