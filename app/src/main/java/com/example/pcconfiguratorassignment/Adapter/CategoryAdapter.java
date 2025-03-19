package com.example.pcconfiguratorassignment.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pcconfiguratorassignment.DetailsList;
import com.example.pcconfiguratorassignment.Domain.Category;
import com.example.pcconfiguratorassignment.R;

import java.util.ArrayList;


//This is the class for Categories Adapter which is used as a Categories Container on the Catalog.java page
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewholder> {
    private static final String TAG = "CategoryAdapter";
    ArrayList<Category> items;
    Context context;

    //Getting list of items
    public CategoryAdapter(ArrayList<Category> items) {
        this.items = items;
    }

    @NonNull
    @Override
    //creating a view holder and getting XML file
    public CategoryAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context= parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.vieholder_category, parent, false);
        return new viewholder(inflate);
    }

    @Override
    //binding the background for each category taken from Category List
    public void onBindViewHolder(@NonNull CategoryAdapter.viewholder holder, int position) {

        holder.titleTxt.setText(items.get(position).getName());

        switch (position) {
            case 0: {
                holder.pic.setBackgroundResource(R.drawable.cat_0_backgound);
                break;
            }
            case 1: {
                holder.pic.setBackgroundResource(R.drawable.cat_1_backgound);
                break;
            }
            case 2: {
                holder.pic.setBackgroundResource(R.drawable.cat_2_backgound);
                break;
            }
            case 3: {
                holder.pic.setBackgroundResource(R.drawable.cat_3_backgound);
                break;
            }
            case 4: {
                holder.pic.setBackgroundResource(R.drawable.cat_4_backgound);
                break;
            }
            case 5: {
                holder.pic.setBackgroundResource(R.drawable.cat_5_backgound);
                break;
            }
        }
        int drawableResourceId=context.getResources().getIdentifier(items.get(position).getImagePath(),
         "drawable",holder.itemView.getContext().getPackageName());
        //Setting up the image using Glide library
         Glide.with(context)
                 .load(drawableResourceId)
                 .into(holder.pic);

        //Creating a click listener, so if user clicks on the category it will open the page with items from chosen category
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = holder.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    Intent intent = new Intent(context, DetailsList.class);
                    intent.putExtra("CategoryId", holder.getAdapterPosition());
                    intent.putExtra("CategoryName", items.get(clickedPosition).getName());
                    context.startActivity(intent);
                } else {
                    Log.e(TAG, "Invalid position: " + clickedPosition);
                }
            }
        });
    }

    //Getting the number of items from the list
    @Override
    public int getItemCount() {
        return items.size();
    }

    //getting the Xml elements
    public class viewholder extends RecyclerView.ViewHolder{
        TextView titleTxt;
        ImageView pic;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            titleTxt=itemView.findViewById(R.id.catNameTxt);
            pic=itemView.findViewById(R.id.imageCategory);
        }
    }
}
