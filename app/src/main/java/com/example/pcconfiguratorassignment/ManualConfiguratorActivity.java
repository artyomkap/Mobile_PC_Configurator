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
import com.example.pcconfiguratorassignment.databinding.ActivityManualConfiguratorBinding;
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

public class ManualConfiguratorActivity extends AppCompatActivity {
    private ActivityManualConfiguratorBinding binding;

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityManualConfiguratorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database= FirebaseDatabase.getInstance();
        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }

        initItems();
        setVariable();


    }

    interface DetailTitlesCallback {
        void onDetailTitlesLoaded(List<String> detailTitles, List<Integer> detailPrices);
    }

    private void getDetailTitlesFromDatabase(Collection<Integer> itemIds, DetailTitlesCallback callback) {
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
                            if (itemId.equals(detailId)) {
                                detailTitles.add(detailTitle);
                                detaisPrices.add(price);
                            }
                        }
                    }
                }
                callback.onDetailTitlesLoaded(detailTitles, detaisPrices);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    interface CategoryNamesCallback {
        void onCategoryNamesLoaded(List<String> categoryNames);
    }

    private void getCategoryNamesFromDatabase(CategoryNamesCallback callback) {
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
                    callback.onCategoryNamesLoaded(CategoryNames);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }



    private void setVariable() {
        //send user to the previous Activity to the COnfigurationMenu
        binding.backButtonDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ConfiguratorMenu.class);
                startActivity(intent);
                finish();
            }
        });

        //resets all values on the configurator
        binding.resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfiguratorCart.resetCategoryToItemIdMap();
                Intent intent = new Intent(getApplicationContext(), ManualConfiguratorActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(ManualConfiguratorActivity.this, "Configuration reset!", Toast.LENGTH_LONG).show();
            }
        });

        //saves all values in the configurator to the Database
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<Integer, Integer> cart_category_id = ConfiguratorCart.getCategoryToItemIdMap();
                String user_id = user.getUid();
                DatabaseReference configurationsRef = FirebaseDatabase.getInstance().getReference().child("configurations").child(user_id);
                String configurationId = configurationsRef.push().getKey();

                if (configurationId != null) {
                    Map<String, String> stringMap = new HashMap<>();
                    for (Map.Entry<Integer, Integer> entry : cart_category_id.entrySet()) {
                        stringMap.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
                    }

                    configurationsRef.child(configurationId).setValue(stringMap)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ManualConfiguratorActivity.this, "Configuration saved successfully!", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(ManualConfiguratorActivity.this, "Error saving configuration: " + task.getException(), Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        });

    }

    //initializing all items
    private void initItems() {
        Map<Integer, Integer> cart_category_id = ConfiguratorCart.getCategoryToItemIdMap();
        binding.ConfigProgressBar.setVisibility(View.VISIBLE);
        //getting all Category Names from Database
        getCategoryNamesFromDatabase(new CategoryNamesCallback() {
            @Override
            public void onCategoryNamesLoaded(List<String> categoryNames) {
                List<String> categoryNameList = categoryNames;
                System.out.println("CategoryNameList"+ categoryNameList);
                //getting all Details Names and Prices From database
                getDetailTitlesFromDatabase(cart_category_id.values(), new DetailTitlesCallback() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDetailTitlesLoaded(List<String> detailTitles, List<Integer> detailPrices) {
                        List<String> detailTitleList = detailTitles;
                        System.out.println("detailTitleList" + detailTitleList);
                        System.out.println("detailPrices" + detailPrices);
                        //putting all values to the Map List
                        List<Map<String, String>> itemList = new ArrayList<>();

                        for (int i = 0; i < categoryNameList.size(); i++) {
                            String categoryName = categoryNameList.get(i);
                            String detailTitle = detailTitleList.get(i);
                            //putting all values to another Map
                            Map<String, String> itemMap = new HashMap<>();
                            itemMap.put("Category", categoryName);
                            itemMap.put("DetailTitle", detailTitle);
                            itemList.add(itemMap);
                        }

                        //counting Total price from detail Prices
                        int totalPrice = 0;
                        for (int price : detailPrices) {
                            totalPrice += price;
                        }
                        //initializing Adapter with all Names and Total Price
                        SimpleAdapter adapter = new SimpleAdapter(ManualConfiguratorActivity.this, itemList, R.layout.list_item,
                                new String[]{"Category", "DetailTitle"}, new int[]{R.id.text_1, R.id.text_2});
                        binding.confManualView.setAdapter(adapter);
                        binding.TotalTxt.setText("£" + totalPrice);
                        binding.ConfigProgressBar.setVisibility(View.GONE);
                    }
                });

            }
        });


    }

}