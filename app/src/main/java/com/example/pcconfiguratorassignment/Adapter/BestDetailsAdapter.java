package com.example.pcconfiguratorassignment.Adapter;

import android.annotation.SuppressLint;
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
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.pcconfiguratorassignment.Domain.Details;
import com.example.pcconfiguratorassignment.FullDetailActivity;
import com.example.pcconfiguratorassignment.R;

import java.util.ArrayList;


//This is the class for Best Details Adapter which is used as a Detail Container on the Catalog.java page
public class BestDetailsAdapter extends RecyclerView.Adapter<BestDetailsAdapter.viewholder> {
    ArrayList<Details> items;
    Context context;

    //Initializing list of items
    public BestDetailsAdapter(ArrayList<Details> items) {
        this.items = items;
    }

    @NonNull
    @Override
    //// Create new views, new view holder with XML file
    public BestDetailsAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context= parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_best_details, parent, false);
        return new viewholder(inflate);
    }

    @SuppressLint("SetTextI18n")
    @Override

    //setting the XML Elements some text from Items List
    public void onBindViewHolder(@NonNull BestDetailsAdapter.viewholder holder, int position) {
         holder.titleTxt.setText(items.get(position).getTitle());
         holder.priceTxt.setText("£" + items.get(position).getPrice());
         holder.extraInfo.setText(items.get(position).getExtraInfo());

         //getting the image from items and setting it to the XML element using GLide library
         Glide.with(context)
                 .load(items.get(position).getImagePath())
                 .transform(new FitCenter(), new RoundedCorners(30))
                 .into(holder.pic);

         //Setting the clicker, if user click on this adapter, it will open a full page of this item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, FullDetailActivity.class);
                intent.putExtra("object", items.get(position));
                context.startActivity(intent);
            }
        });
    }

    //Getting the number of items in the list
    @Override
    public int getItemCount() {
        return items.size();
    }

    //getting the XML elements by its ids
    public class viewholder extends RecyclerView.ViewHolder{
        TextView titleTxt, priceTxt, extraInfo;
        ImageView pic;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            titleTxt=itemView.findViewById(R.id.titleTxt);
            priceTxt=itemView.findViewById(R.id.priceTxt);
            extraInfo=itemView.findViewById(R.id.ExtraInfoTxt);
            pic=itemView.findViewById(R.id.pic);
        }
    }
}
