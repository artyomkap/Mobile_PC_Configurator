package com.example.pcconfiguratorassignment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pcconfiguratorassignment.Domain.ConfiguratorCart;
import com.example.pcconfiguratorassignment.databinding.ActivityFullAutoConfigurationBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//FullAutoConfiguration Activity
public class FullAutoConfiguration extends AppCompatActivity {
    ActivityFullAutoConfigurationBinding binding;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;
    private String chosenBudget;

    private String chosenPurpose;

    private List<Integer> itemIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initializing binding and getting XML elements
        binding=ActivityFullAutoConfigurationBinding.inflate(getLayoutInflater());
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
        initList();
        //initializing buttons and actions
        setVariables();
    }

    private void setVariables() {
        //returns user back to the AutoConfigurator Activity by clicking backBtn
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AutoConfiguratorActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //saves Configuration to the database by clicking save button
        binding.saveConfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting Map of itemIds and CategoryIds
                Map<Integer, Integer> cart_category_id = ConfiguratorCart.getCategoryToItemIdMap();
                String user_id = user.getUid();
                //going to the configuration "table" in the Database
                DatabaseReference configurationsRef = FirebaseDatabase.getInstance().getReference().child("configurations").child(user_id);
                String configurationId = configurationsRef.push().getKey();

                if (configurationId != null) {
                    Map<String, String> stringMap = new HashMap<>();
                    //saving itemId to the Database
                    for (Map.Entry<Integer, Integer> entry : cart_category_id.entrySet()) {
                        stringMap.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
                    }

                    configurationsRef.child(configurationId).setValue(stringMap)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(FullAutoConfiguration.this, "Configuration saved successfully!", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(FullAutoConfiguration.this, "Error saving configuration: " + task.getException(), Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        });
    }

    //callback which returns the list
    interface CategoryNamesCallback {
        void onCategoryNamesLoaded(List<String> categoryNames);
    }

    //simple function for getting all CategoryNames from database
    private void getCategoryNamesFromDatabase(FullAutoConfiguration.CategoryNamesCallback callback) {
        //open Category "table" in DB
        DatabaseReference detailsReference = database.getReference("Category");
        List<String> CategoryNames = new ArrayList<>();

        detailsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        String categoryName = issue.child("Name").getValue(String.class);
                        CategoryNames.add(categoryName);
                    }
                    //callback returns the list of Category Names
                    callback.onCategoryNamesLoaded(CategoryNames);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }


    //callback which returns the list of Titles and Prices for Items
    interface DetailTitlesCallback {
        void onDetailTitlesLoaded(List<String> detailTitles, List<Integer> detailPrices);
    }


    //simple function which getting the titles and prices for details
    private void getDetailTitlesFromDatabase(Collection<Integer> itemIds, FullAutoConfiguration.DetailTitlesCallback callback) {
        //going to Details "table" in database
        DatabaseReference detailsReference = database.getReference("Details");
        List<String> detailTitles = new ArrayList<>();
        List<Integer> detaisPrices = new ArrayList<>();
        boolean containsEmpty = itemIds.contains(-1);

        detailsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (Integer itemId : itemIds) {
                    //if itemId is -1, set it's name as Empty and price as 0
                    if (itemId == -1) {
                        detailTitles.add("Empty");
                        detaisPrices.add(0);
                    } else {
                        for (DataSnapshot detailSnapshot : dataSnapshot.getChildren()) {
                            Integer detailId = detailSnapshot.child("Id").getValue(Integer.class);
                            String detailTitle = detailSnapshot.child("Title").getValue(String.class);
                            Integer price = detailSnapshot.child("Price").getValue(Integer.class);
                            if (itemId.equals(detailId)) {
                                //adding all values to the list
                                detailTitles.add(detailTitle);
                                detaisPrices.add(price);
                            }
                        }
                    }
                }
                //return lists of Titles and Prices
                callback.onDetailTitlesLoaded(detailTitles, detaisPrices);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    ///initializing Configuration from database and putting to ListView
    private void initList() {
        ConfiguratorCart.updateCategoryToItemIdMap(itemIds);
        //getting the names of categories from DB
        getCategoryNamesFromDatabase(new CategoryNamesCallback() {
            @Override
            public void onCategoryNamesLoaded(List<String> categoryNames) {
                List<String> categoryNameList = categoryNames;
                //getting the Names and Prices from DB
                getDetailTitlesFromDatabase(itemIds, new DetailTitlesCallback() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDetailTitlesLoaded(List<String> detailTitles, List<Integer> detailPrices) {
                        List<String> detailTitleList = detailTitles;
                        List<Map<String, String>> ItemsList = new ArrayList<>();

                        for (int i = 0; i < categoryNameList.size(); i++) {
                            String categoryName = categoryNameList.get(i);
                            String detailTitle = detailTitleList.get(i);

                            Map<String, String> itemMap = new HashMap<>();
                            itemMap.put("Category", categoryName);
                            itemMap.put("DetailTitle", detailTitle);
                            ItemsList.add(itemMap);

                            System.out.println(ItemsList);
                        }

                        int totalPrice = 0;
                        for (int price : detailPrices) {
                            totalPrice += price;
                        }
                        //adding all items to the list_item ListView
                        SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), ItemsList, R.layout.list_item,
                                new String[]{"Category", "DetailTitle"}, new int[]{R.id.text_1, R.id.text_2});
                        binding.confAutoView.setAdapter(adapter);
                        binding.TotalTxt.setText("£" + totalPrice);
                        binding.budgetTxt.setText(chosenBudget);
                        binding.PurposeTxt.setText(chosenPurpose);
                    }

                });


            }
        });
    }

    //getting all values from previous Intent
    private void getIntentExtra() {
        chosenBudget = getIntent().getStringExtra("budget");
        chosenPurpose = getIntent().getStringExtra("purpose");
        itemIds = getIntent().getIntegerArrayListExtra("configuration");
    }
}