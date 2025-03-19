package com.example.pcconfiguratorassignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pcconfiguratorassignment.Adapter.LoadedConfigurationAdapter;
import com.example.pcconfiguratorassignment.databinding.ActivityConfiguratorMenuBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

//ConmfiguratorMenu activity class

public class ConfiguratorMenu extends AppCompatActivity {
    private ActivityConfiguratorMenuBinding binding;

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initializing binding and getting XML elements
        binding=ActivityConfiguratorMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //getting user and database
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database=FirebaseDatabase.getInstance();
        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }

        //initializing configurations from database
        initConfigurations();
        //initializing buttons and actions
        SetVariable();
    }

    private void initConfigurations() {
        //getting list of configurations from Database for current user
        DatabaseReference userConfigurationsRef = FirebaseDatabase.getInstance()
                .getReference("configurations")
                .child(user.getUid());

        userConfigurationsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> configurations = new ArrayList<>();
                int index = 1;
                //if configurations in database adding them to the list
                for(DataSnapshot issue: snapshot.getChildren()) {
                    configurations.add("Configuration " + index++);

                }
                //put all configuration to adapter
                LoadedConfigurationAdapter adapter = new LoadedConfigurationAdapter(configurations, new LoadedConfigurationAdapter.OnLoadButtonClickListener() {
                    @Override
                    //load button for each configuration
                    public void onLoadButtonClick(int position) {
                        if (user != null) {
                            //opens a configuration Page for chosen configuration
                            Intent intent = new Intent(getApplicationContext(), FullConfigurationActivity.class);
                            intent.putExtra("position", position);
                            startActivity(intent);

                        } else {
                            Intent loginIntent = new Intent(getApplicationContext(), Login.class);
                            startActivity(loginIntent);

                        }

                    }
                });
                System.out.println(configurations);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                binding.confRecyclerView.setLayoutManager(layoutManager);
                binding.confRecyclerView.setAdapter(adapter);
                binding.confProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void SetVariable() {
        //Opens a AutoConfiguratorActivity by pressing the AutomaticBtn
        binding.AutomaticBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AutoConfiguratorActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Opens a ManualConfiguratorActivity by pressing the ManualBtn
        binding.ManualBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ManualConfiguratorActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //Opens MainMenu page by pressing Back Button
        binding.backButtonDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}