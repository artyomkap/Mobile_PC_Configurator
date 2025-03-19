package com.example.pcconfiguratorassignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pcconfiguratorassignment.databinding.ActivityAutoConfiguratorBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


//this is a class for AutoCofigurator activity which allow user to auto configure PC
public class AutoConfiguratorActivity extends AppCompatActivity {
    //getting an XML emelents using bnding
    ActivityAutoConfiguratorBinding binding;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initializing binding and getting XML elements
        binding=ActivityAutoConfiguratorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        //getting user and database
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }

        //initializing Spinners from values and layout

        // Budget spinner adapter
        ArrayAdapter<CharSequence> budgetAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_budget_states, R.layout.spinner_layout);
        budgetAdapter.setDropDownViewResource(R.layout.spinner_layout);
        binding.BudgetSpinner.setAdapter(budgetAdapter);
        // Purpose spinner adapter
        ArrayAdapter<CharSequence> purposeAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_purpose_states, R.layout.spinner_layout);
        purposeAdapter.setDropDownViewResource(R.layout.spinner_layout);
        binding.PurposesSpinner.setAdapter(purposeAdapter);

        //Get PC configuration based on user selection
        getConfiguration();
    }

    // Get PC configuration based on user selection
    private void getConfiguration() {
        // Navigate back to ConfiguratorMenu activity when back button is clicked
        binding.backButtonDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ConfiguratorMenu.class);
                startActivity(intent);
                finish();
            }
        });

        // Handle configuration button click event
        binding.ConfigurateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get selected budget and purpose
                String selectedBudget = binding.BudgetSpinner.getSelectedItem().toString();
                String selectedPurpose = binding.PurposesSpinner.getSelectedItem().toString();
                List<Integer> itemIds = new ArrayList<>();


                //Based on selected budget and purpose making a PC configuration
                if (selectedBudget.equals("Small")) {
                    if (selectedPurpose.equals("Gaming")) {
                        itemIds.add(3);
                        itemIds.add(10);
                        itemIds.add(22);
                        itemIds.add(29);
                        itemIds.add(35);
                    } else if (selectedPurpose.equals("Office")) {
                        itemIds.add(-1);
                        itemIds.add(8);
                        itemIds.add(22);
                        itemIds.add(30);
                        itemIds.add(35);
                    } else if (selectedPurpose.equals("Art (Video, 3D, Photo)")) {
                        itemIds.add(7);
                        itemIds.add(8);
                        itemIds.add(11);
                        itemIds.add(14);
                        itemIds.add(24);
                    }
                } else if (selectedBudget.equals("Medium")) {
                    if (selectedPurpose.equals("Gaming")) {
                        itemIds.add(5);
                        itemIds.add(11);
                        itemIds.add(14);
                        itemIds.add(24);
                        itemIds.add(37);
                    } else if (selectedPurpose.equals("Office")) {
                        itemIds.add(3);
                        itemIds.add(13);
                        itemIds.add(15);
                        itemIds.add(23);
                        itemIds.add(36);
                    } else if (selectedPurpose.equals("Art (Video, 3D, Photo)")) {
                        itemIds.add(2);
                        itemIds.add(12);
                        itemIds.add(20);
                        itemIds.add(29);
                        itemIds.add(33);
                    }
                } else if (selectedBudget.equals("Large")) {
                    if (selectedPurpose.equals("Gaming")) {
                        itemIds.add(0);
                        itemIds.add(12);
                        itemIds.add(21);
                        itemIds.add(29);
                        itemIds.add(32);
                    } else if (selectedPurpose.equals("Office")) {
                        itemIds.add(1);
                        itemIds.add(9);
                        itemIds.add(16);
                        itemIds.add(23);
                        itemIds.add(33);
                    } else if (selectedPurpose.equals("Art (Video, 3D, Photo)")) {
                        itemIds.add(0);
                        itemIds.add(9);
                        itemIds.add(17);
                        itemIds.add(24);
                        itemIds.add(32);
                    }
                }

                // Start FullAutoConfiguration activity with selected budget, purpose, and configuration
                Intent intent = new Intent(getApplicationContext(), FullAutoConfiguration.class);
                intent.putExtra("budget", selectedBudget);
                intent.putExtra("purpose", selectedPurpose);
                intent.putIntegerArrayListExtra("configuration", (ArrayList<Integer>) itemIds);
                startActivity(intent);
                finish();

                // Show toast with selected budget and purpose
                Toast.makeText(AutoConfiguratorActivity.this, "Selected Budget: " + selectedBudget + ", Selected Purpose: " + selectedPurpose, Toast.LENGTH_LONG).show();
            }
        });
    }
}