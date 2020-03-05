package com.example.spacebottomview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.text.Html;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spacebottomview.R;
import com.example.spacebottomview.pojo_classes.Brand;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OnlineDetailAdapter extends RecyclerView.Adapter<OnlineDetailAdapter.ViewHolder> {
    Context context;
    ArrayList<Brand> onlinebrand_details=null;
    TextView prod_url;

    public OnlineDetailAdapter(Context context, ArrayList<Brand> onlinebrand_details) {
        this.context = context;
        this.onlinebrand_details=onlinebrand_details;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.onlinedetailadapter, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {
     //   holder.store_name.setText("Store Name :"+onlinebrand_details.get(i).website_name);
        String url=onlinebrand_details.get(i).prod_url;
        holder.brandata_prices.setText("Store Rate : Rs."+onlinebrand_details.get(i).online_price_rate);
        holder.mrp_rate.setText("Original Rate : Rs."+onlinebrand_details.get(i).product_rate);
        holder.delivery_date.setText("Delivery Date :"+onlinebrand_details.get(i).delivery_date);
        holder.delivery_cost.setText("Delivery Cost :"+onlinebrand_details.get(i).delivery_cost);
        holder.prod_url.setText(Html.fromHtml(onlinebrand_details.get(i).prod_url));



        Picasso.with(context)
                .load(onlinebrand_details.get(i).logo)
                .into(holder.mob_img);
    }
    @Override
    public int getItemCount() {
        return onlinebrand_details.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
         ImageView mob_img;
         TextView brandata_prices,mrp_rate,delivery_date,delivery_cost,prod_url;
         public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mob_img=itemView.findViewById(R.id.mob_img);
            brandata_prices=itemView.findViewById(R.id.brandata_prices);
            mrp_rate=itemView.findViewById(R.id.mrp_rate);
            delivery_date=itemView.findViewById(R.id.delivery_date);
            delivery_cost=itemView.findViewById(R.id.delivery_cost);
            prod_url=itemView.findViewById(R.id.prod_url);
         }
    }
}