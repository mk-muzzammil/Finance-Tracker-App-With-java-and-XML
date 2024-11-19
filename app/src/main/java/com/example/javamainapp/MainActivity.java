package com.example.javamainapp;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText username,email,password;
    Button signupBtn,loginBtn;
    Databasehelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username=findViewById(R.id.username);
        email=findViewById(R.id.email);
        password=findViewById(R.id.edtPassword);
        signupBtn=findViewById(R.id.btnSignUp);
        loginBtn=findViewById(R.id.btnLogin);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameText=username.getText().toString();
                String emailText=email.getText().toString();
                String passwordText=password.getText().toString();

                if(usernameText.isEmpty() || emailText.isEmpty() || passwordText.isEmpty()){
                    Toast.makeText(MainActivity.this,"Please fill all fields",Toast.LENGTH_SHORT).show();
                }
                else{
                    db=new Databasehelper(MainActivity.this);
                    boolean isValidated=db.registerUser(usernameText,emailText,passwordText);
                    if(isValidated){
                        Toast.makeText(MainActivity.this,"Validated True",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this,"validated as False",Toast.LENGTH_SHORT).show();
                    }
                    if(isValidated){
                        Intent goTologinPage=new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(goTologinPage);
                    }else{
                        Toast.makeText(getApplicationContext(),"User with this credentials already exists",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToLogin=new Intent(MainActivity.this, LoginActivity.class);
                startActivity(goToLogin);
            }
        });




    }





}
