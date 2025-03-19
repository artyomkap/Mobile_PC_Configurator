package com.example.pcconfiguratorassignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pcconfiguratorassignment.Adapter.BestDetailsAdapter;
import com.example.pcconfiguratorassignment.Adapter.CategoryAdapter;
import com.example.pcconfiguratorassignment.Domain.Category;
import com.example.pcconfiguratorassignment.Domain.Details;
import com.example.pcconfiguratorassignment.databinding.ActivityCatalogBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

//Catalog Activity
public class Catalog extends AppCompatActivity {
    private ActivityCatalogBinding binding;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initializing binding and getting XML elements
        binding=ActivityCatalogBinding.inflate(getLayoutInflater());
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

        // Initialize best details section
        initBestDetails();
        // Initialize categories section
        initCategories();
        // Initialize click listeners and actions
        initSetVariable();
    }


    //// Initialize search and back button click listeners
    private void initSetVariable() {
        //// Search button click listener
        binding.searchButton.setOnClickListener(v -> {
            //getting the text for search from textEdit element
            String text=binding.SearchEditText.getText().toString();
            if (!text.isEmpty()) {
                //starting new activity based on search
                Intent intent = new Intent(Catalog.this, DetailsList.class);
                intent.putExtra("text", text);
                intent.putExtra("isSearch", true);
                startActivity(intent);
            }

        });
        //back button listener
        binding.backButtonDetails.setOnClickListener(v -> {
            Intent intent = new Intent(Catalog.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    // Initialize best details section
    private void initBestDetails() {
        //getting the list of details from database
        DatabaseReference myReference = database.getReference("Details");
        binding.BestToConfigureProgressBar.setVisibility(View.VISIBLE);
        ArrayList<Details> list = new ArrayList<>();
        //taking details only with True "BestDetails"
        Query query = myReference.orderByChild("BestDetails").equalTo(true);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for(DataSnapshot issue: snapshot.getChildren()) {
                        list.add(issue.getValue(Details.class));
                    }
                    if(list.size() > 0) {
                        //put all selected items into the BestDetailsAdapter
                        binding.BestRecyclerView.setLayoutManager(new LinearLayoutManager(Catalog.this, LinearLayoutManager.HORIZONTAL, false));
                        RecyclerView.Adapter adapter= new BestDetailsAdapter(list);
                        binding.BestRecyclerView.setAdapter(adapter);
                    }
                    binding.BestToConfigureProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // Initialize categories section
    private void initCategories() {
        //selecting all categories from database
        DatabaseReference myReference = database.getReference("Category");
        binding.CategoryProgressBar.setVisibility(View.VISIBLE);
        ArrayList<Category> list = new ArrayList<>();
        myReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for(DataSnapshot issue: snapshot.getChildren()) {
                        list.add(issue.getValue(Category.class));
                    }
                    if(list.size() > 0) {
                        //put all categories into the CategoryAdapter
                        binding.CategoryRecyclerView.setLayoutManager(new LinearLayoutManager(Catalog.this, LinearLayoutManager.HORIZONTAL, false));
                        RecyclerView.Adapter adapter= new CategoryAdapter(list);
                        binding.CategoryRecyclerView.setAdapter(adapter);
                    }
                    binding.CategoryProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}