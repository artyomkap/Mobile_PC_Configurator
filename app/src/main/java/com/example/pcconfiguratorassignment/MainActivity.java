package com.example.pcconfiguratorassignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    Button logout_button;
    TextView textView;
    FirebaseUser user;
    Button Catalog_button;

    Button Configurator_button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initializing values from XML file
        auth = FirebaseAuth.getInstance();
        logout_button = findViewById(R.id.logout);
        Catalog_button = findViewById(R.id.catalog_btn);
        Configurator_button = findViewById(R.id.configurator_btn);
        textView = findViewById(R.id.user_details);
        user = auth.getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        } else {
          textView.setText(user.getEmail());
        }

        //sends user to the Catalog Activity by clicking the button
        Catalog_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Catalog.class);
                startActivity(intent);
                finish();
            }
        });

        //sends user to the Configurator Menu Activity by clicking the button
        Configurator_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ConfiguratorMenu.class);
                startActivity(intent);
                finish();
            }
        });

        //sends user to the Login Activity by clicking the button
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}