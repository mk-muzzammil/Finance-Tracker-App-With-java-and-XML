package com.example.javamainapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class AddTransections extends AppCompatActivity {
    EditText amount, category;
    Button addBtn;
    String selectedType;
    Databasehelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_transections);
        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");
        if (userId == null || userId.isEmpty()) {
            Toast.makeText(this, "User ID is missing", Toast.LENGTH_SHORT).show();
            return; // Exit if userId is invalid
        }
        int userIdInt = Integer.parseInt(userId);

        amount = findViewById(R.id.amountInput);
        category = findViewById(R.id.categoryInput);
        addBtn = findViewById(R.id.addButton);

        Spinner typeSpinner = findViewById(R.id.typeSpinner);
        String[] types = {"Income", "Expense"};

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        typeSpinner.setAdapter(adapter);

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get selected item text
                selectedType = parent.getItemAtPosition(position).toString();

                // Optional: Display a Toast message to show the selected item
                Toast.makeText(AddTransections.this, "Selected: " + selectedType, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedType = null;
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = new Databasehelper(AddTransections.this);
                if (amount.getText().toString().isEmpty() || category.getText().toString().isEmpty() || selectedType == null) {
                    Toast.makeText(AddTransections.this, "Please Fill All fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                float amountText = Float.parseFloat(amount.getText().toString());
                String categoryText = category.getText().toString();

                if (amountText > 0) {
                    if (db.addtransactions(amountText, categoryText, selectedType, userIdInt)) {
                        Toast.makeText(AddTransections.this, "Added successfully", Toast.LENGTH_SHORT).show();
                        Intent resultIntent = new Intent();
                        setResult(RESULT_OK, resultIntent); // Pass back the result to the previous activity
                        finish(); // Finish this activity to return to the Dashboard_Account
                    } else {
                        Toast.makeText(AddTransections.this, "Some issue occurred", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddTransections.this, "Amount must be greater than 0", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
