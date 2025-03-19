package com.example.pcconfiguratorassignment.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pcconfiguratorassignment.R;

import java.util.List;

// Adapter class for the RecyclerView of the Loaded configuration on the ConfiguratorMenu.class page
public class LoadedConfigurationAdapter extends RecyclerView.Adapter<LoadedConfigurationAdapter.ConfigurationViewHolder> {
    private final List<String> configurations; // List of configuration names
    private final OnLoadButtonClickListener loadButtonClickListener; // Listener for load button clicks


    // Constructor to initialize the adapter with configurations and click listener
    public LoadedConfigurationAdapter(List<String> configurations, OnLoadButtonClickListener loadButtonClickListener) {
        this.configurations = configurations;
        this.loadButtonClickListener = loadButtonClickListener;
    }

    @NonNull
    @Override
    // Create new views and getting the XML file
    public ConfigurationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_loaded_configuration_adapter, parent, false);
        return new ConfigurationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ConfigurationViewHolder holder, int position) {
        String configuration = configurations.get(position);
        holder.bind(configuration);
    }

    // Return the size of the configuration list
    @Override
    public int getItemCount() {
        return configurations.size();
    }


    public class ConfigurationViewHolder extends RecyclerView.ViewHolder {
        private TextView configurationNameTextView;
        private Button loadButton;

        // Constructor to initialize views
        public ConfigurationViewHolder(@NonNull View itemView) {
            super(itemView);
            // Find and assign views from the item layout
            configurationNameTextView = itemView.findViewById(R.id.configurationNameTxt);
            loadButton = itemView.findViewById(R.id.loadBtn);
            // Set click listener for the load button
            loadButton.setOnClickListener(v -> {
                if (loadButtonClickListener != null) {
                    loadButtonClickListener.onLoadButtonClick(getAdapterPosition());
                }
            });
        }

        // Method to bind configuration name to the TextView
        public void bind(String configurationName) {
            configurationNameTextView.setText(configurationName);
        }
    }

    public interface OnLoadButtonClickListener {
        void onLoadButtonClick(int position);
    }
}
