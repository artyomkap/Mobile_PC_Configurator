package com.example.pcconfiguratorassignment;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.pcconfiguratorassignment.Domain.ConfiguratorCart;
import com.example.pcconfiguratorassignment.Domain.Details;
import com.example.pcconfiguratorassignment.databinding.ActivityFullDetailBinding;

import java.util.Map;

public class FullDetailActivity extends AppCompatActivity {
    ActivityFullDetailBinding binding;
    private Details object;
    private int num=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initializing binding and getting XML elements
        binding=ActivityFullDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //initializing data from intent
        getIntentExtra();
        //initializing buttons and actions
        setVariable();
    }

    private void setVariable() {
        //returns user to the previous page
        binding.backButton.setOnClickListener(v -> finish());

        //getting image from object from previous intent
        Glide.with(FullDetailActivity.this)
                .load(object.getImagePath())
                .into(binding.itemImg);
        binding.priceTxt.setText("£ " + object.getPrice());
        binding.titleTxt.setText(object.getTitle());
        binding.ExtraInfoTxt.setText(object.getExtraInfo());
        binding.DescriptionTxt.setText(object.getDescription());


        //Button which adds this Details to the Configuration

        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int category_id = object.getCategoryId();
                int item_id = object.getId();
                Map<Integer, Integer> cart_category_id = ConfiguratorCart.getCategoryToItemIdMap();

                if (cart_category_id.containsKey(category_id)) {
                    cart_category_id.put(category_id, item_id);
                } else {
                    cart_category_id.put(category_id, item_id);
                }

                ConfiguratorCart.setCategoryToItemIdMap(cart_category_id);
                String toast_txt = "Detail " + object.getTitle() + " was added to configurator";
                Toast.makeText(FullDetailActivity.this, toast_txt, Toast.LENGTH_LONG).show();
                System.out.println("CART: " + cart_category_id);
            }

        });

    }

    //getting the list of values from previous Intent
    private void getIntentExtra() {
        object= (Details) getIntent().getSerializableExtra("object");
    }
}