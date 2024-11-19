package com.example.javamainapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class Viewtransections extends AppCompatActivity {
    Databasehelper db;
    ListView transectionsList;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_viewtransections);
        Intent intent=getIntent();
        String userId=intent.getStringExtra("userId");
        if(userId.isEmpty()){
            Toast.makeText(Viewtransections.this,"No User Id extracted",Toast.LENGTH_SHORT).show();

        }
        int userIdInt=Integer.parseInt(userId);
        db=new Databasehelper(Viewtransections.this);

        ArrayList<Transection> transections=db.loadTransectionsAccTOUSER(userIdInt);

        transectionsList=findViewById(R.id.transactionListView);
        adapter=new CustomAdapter(Viewtransections.this,transections);
        transectionsList.setAdapter(adapter);



    }
}