package com.example.pcconfiguratorassignment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pcconfiguratorassignment.Adapter.DetailsListAdapter;
import com.example.pcconfiguratorassignment.Domain.Details;
import com.example.pcconfiguratorassignment.databinding.ActivityDetailsListBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetailsList extends AppCompatActivity {
    ActivityDetailsListBinding binding;
    private RecyclerView.Adapter adapterDetailsList;
    private int categoryId;
    private String categoryName;
    private String searchText;
    private boolean isSearch;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initializing binding and getting XML elements
        binding=ActivityDetailsListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //initializing database
        database=FirebaseDatabase.getInstance();

        getIntentExtra();
        initList();
    }

    private void initList() {
        DatabaseReference myReference=database.getReference("Details");
        binding.DetailsProgressBar.setVisibility(View.VISIBLE);
        ArrayList<Details> list = new ArrayList<>();

        Query query;
        if(isSearch) {
            //if user searched details, checking details in DB from searchText
            query=myReference.orderByChild("Title").startAt(searchText).endAt(searchText+'\uf8ff');
        }else{
            //if user clicked on the Category, getting all items for chosen category
            query=myReference.orderByChild("CategoryId").equalTo(categoryId);
        }
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot issue : snapshot.getChildren()){
                        //adding all items from DB to the list
                        list.add(issue.getValue(Details.class));
                    }
                    if(list.size()>0){
                        //putting the list with items to the DetailsListAdapter
                        binding.DetailsListView.setLayoutManager(new GridLayoutManager(DetailsList.this, 2));
                        adapterDetailsList=new DetailsListAdapter(list);
                        binding.DetailsListView.setAdapter(adapterDetailsList);
                    }
                    binding.DetailsProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //getting information from previous Intent
    private void getIntentExtra() {
        categoryId=getIntent().getIntExtra("CategoryId", 0);
        categoryName=getIntent().getStringExtra("CategoryName");
        searchText=getIntent().getStringExtra("text");
        isSearch=getIntent().getBooleanExtra("isSearch", false);

        binding.titleTxt.setText(categoryName);
        binding.backButtonDetails.setOnClickListener(v -> finish());
    }
}