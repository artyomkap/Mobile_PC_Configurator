package com.example.pcconfiguratorassignment.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.pcconfiguratorassignment.Domain.Details;
import com.example.pcconfiguratorassignment.FullDetailActivity;
import com.example.pcconfiguratorassignment.R;

import java.util.ArrayList;

//This is an adapter for list of details from chosen category for the DetailsList.java file
public class DetailsListAdapter extends RecyclerView.Adapter<DetailsListAdapter.viewholder> {
    ArrayList<Details> items;
    Context context;

    // Constructor to initialize the adapter with a list of items
    public DetailsListAdapter(ArrayList<Details> items) {
        this.items = items;
    }

    @NonNull
    @Override
    //getting the XML file and use at as viewholder
    public DetailsListAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view
        context=parent.getContext();
        View inflate= LayoutInflater.from(context).inflate(R.layout.viewholder_details_list, parent, false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsListAdapter.viewholder holder, int position) {
        // Get element from your database at this position and replace the contents of the view with that element
        Details currentItem = items.get(position);
        holder.titleTxt.setText(items.get(position).getTitle());
        holder.priceTxt.setText("£" + items.get(position).getPrice());
        holder.extraInfoTxt.setText(items.get(position).getExtraInfo());
        // Load image using Glide library
        if (currentItem.getImagePath() != null) {
            Glide.with(context)
                    .load(currentItem.getImagePath())
                    .transform(new FitCenter(), new RoundedCorners(30))
                    .into(holder.img);
        } else {
            holder.img.setImageDrawable(null);
        }

        // Set click listener for the item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creating an intent to start the FullDetailActivity and pass the selected item
                Intent intent=new Intent(context, FullDetailActivity.class);
                intent.putExtra("object", items.get(position));
                context.startActivity(intent);
            }
        });
    }

    //Getting the the size of items
    @Override
    public int getItemCount() {
        return items.size();
    }


    // ViewHolder class for caching the views in a row
    public class viewholder extends RecyclerView.ViewHolder{
        TextView titleTxt, DescriptionTxt, priceTxt, extraInfoTxt;
        ImageView img;
        public viewholder(@NonNull View itemView) {
            super(itemView);

            titleTxt=itemView.findViewById(R.id.titleTxt);
            priceTxt=itemView.findViewById(R.id.priceTxt);
            extraInfoTxt=itemView.findViewById(R.id.extraInfoTxt);
            img=itemView.findViewById(R.id.image);
        }
    }
}
