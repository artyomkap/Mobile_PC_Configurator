package com.example.pcconfiguratorassignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pcconfiguratorassignment.databinding.ActivityFullConfigurationBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

//FullConfigurationActivity
public class FullConfigurationActivity extends AppCompatActivity {

    ActivityFullConfigurationBinding binding;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;
    private int config_position;
    private int num=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initializing binding and getting XML elements
        binding=ActivityFullConfigurationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //getting user and database
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }

        getIntentExtra();
        setVariable();
        //initializing actions and button
        setButton();

    }

    private void setButton() {
        //returns user to the previous page
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //callback which return detail Titles and Prices
    interface DetailTitlesCallback {
        void onDetailTitlesLoaded(List<String> detailTitles, List<Integer> detailPrices);
    }


    //method which gets all Items Titles and Prices from Database
    private void getDetailTitlesFromDatabase(List<Integer> itemIds, FullConfigurationActivity.DetailTitlesCallback callback) {
        DatabaseReference detailsReference = database.getReference("Details");
        List<String> detailTitles = new ArrayList<>();
        List<Integer> detaisPrices = new ArrayList<>();
        boolean containsEmpty = itemIds.contains(-1);

        detailsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (Integer itemId : itemIds) {
                    if (itemId == -1) {
                        detailTitles.add("Empty");
                        detaisPrices.add(0);
                    } else {
                        for (DataSnapshot detailSnapshot : dataSnapshot.getChildren()) {
                            Integer detailId = detailSnapshot.child("Id").getValue(Integer.class);
                            String detailTitle = detailSnapshot.child("Title").getValue(String.class);
                            Integer price = detailSnapshot.child("Price").getValue(Integer.class);
                            //adding all Titles and Prices to the lists
                            if (itemId.equals(detailId)) {
                                detailTitles.add(detailTitle);
                                detaisPrices.add(price);
                            }
                        }
                    }
                }
                //returns lists
                callback.onDetailTitlesLoaded(detailTitles, detaisPrices);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void setVariable() {
        //getting all configuration for current user from Database
        DatabaseReference userConfigurationsRef = database.getInstance()
                .getReference("configurations")
                .child(user.getUid());

        userConfigurationsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int currentPosition = 0;
                for(DataSnapshot configSnapshot: snapshot.getChildren()) {
                    if (currentPosition == config_position) {
                        List<Integer> components = new ArrayList<>();
                        for (DataSnapshot componentSnapshot : configSnapshot.getChildren()) {
                            String componentString = componentSnapshot.getValue(String.class);
                            int component = Integer.parseInt(componentString);
                            components.add(component);
                        }
                        System.out.println("COMPONENTS: " + components);
                        getDetailTitlesFromDatabase(components, new DetailTitlesCallback() {
                            //if all item Names were loaded the goes to next stage
                            @Override
                            public void onDetailTitlesLoaded(List<String> detailTitles, List<Integer> detailPrices) {
                                int count = Math.min(detailTitles.size(), 5);

                                //setting all textView for all Titles
                                for (int i = 0; i < count; i++) {
                                    TextView textView = null;
                                    switch (i) {
                                        case 0:
                                            textView = binding.GraphicCardTxt;
                                            break;
                                        case 1:
                                            textView = binding.RamTxt;
                                            break;
                                        case 2:
                                            textView = binding.CpuTxt;
                                            break;
                                        case 3:
                                            textView = binding.MotherBoardTxt;
                                            break;
                                        case 4:
                                            textView = binding.StorageTxt;
                                            break;
                                        default:
                                            break;
                                    }
                                    if (textView != null) {
                                        textView.setText(detailTitles.get(i));
                                    }
                                }

                                //counting the total price for all items
                                int totalPrice = 0;
                                for (int price : detailPrices) {
                                    totalPrice += price;
                                }
                                binding.priceTxt.setText("£" + totalPrice);
                            }
                        });
                        break;

                    }

                    currentPosition++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //getting all information from previous Intent
    private void getIntentExtra() {
        config_position = getIntent().getIntExtra("position", -1);
    }


}