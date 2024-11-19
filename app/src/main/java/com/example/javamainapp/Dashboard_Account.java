package com.example.javamainapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Dashboard_Account extends AppCompatActivity {
    TextView usernameTextView,totalBalance,incomeView,expenseView;
    Button viewTransection,addTransections;
    Databasehelper db;
    int UserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard_account);

        Intent intent=getIntent();
        String id=intent.getStringExtra("Id");
        UserId=Integer.parseInt(id);
        String username=intent.getStringExtra("username");
        Toast.makeText(Dashboard_Account.this,username,Toast.LENGTH_SHORT).show();

        usernameTextView=findViewById(R.id.usernameTextView);
        totalBalance=findViewById(R.id.totalBalance);
        incomeView=findViewById(R.id.totalIncome);
        expenseView=findViewById(R.id.totalExpenses);

        db=new Databasehelper(Dashboard_Account.this);
        Double[] calculatedValues=db.transectionAmountCalculations(UserId);
        totalBalance.setText("Total Balance :$ "+calculatedValues[0]);
        incomeView.setText("Income :$ "+calculatedValues[1]);
        expenseView.setText("Expense :$ "+calculatedValues[2]);


        viewTransection=findViewById(R.id.viewTransactionsButton);
        addTransections=findViewById(R.id.addTransactionButton);
        usernameTextView.setText(usernameTextView.getText()+" "+username);


        viewTransection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToViewTransections =new Intent(Dashboard_Account.this, Viewtransections.class);
                goToViewTransections.putExtra("userId",id);
                startActivity(goToViewTransections);
            }
        });
        addTransections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToAddTransactions =new Intent(Dashboard_Account.this, AddTransections.class);
                goToAddTransactions.putExtra("userId",id);
                startActivityForResult(goToAddTransactions,1);
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Refresh data in the dashboard when returning from AddTransections
            refreshTransactionData();
        }
    }

    private void refreshTransactionData() {
        Double[] calculatedValues = db.transectionAmountCalculations(UserId);
        totalBalance.setText("Total Balance :$ " + calculatedValues[0]);
        incomeView.setText("Income :$ " + calculatedValues[1]);
        expenseView.setText("Expense :$ " + calculatedValues[2]);
    }
}